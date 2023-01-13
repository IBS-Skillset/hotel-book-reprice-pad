package com.hotel.mappers.raterule.response;

import com.hotel.service.raterule.HotelRateRuleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfOTAHotelBookingRuleRSRuleMessageStatusApplicationRoomRate;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.ErrorsType;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HotelRateRuleResponseMapperTest {

    @InjectMocks
    private HotelRateRuleResponseMapper mapper;

    @Test
    void testMap() throws Exception {
        ArrayOfOTAHotelBookingRuleRSRuleMessageStatusApplicationRoomRate.RoomRate roomRate = new ArrayOfOTAHotelBookingRuleRSRuleMessageStatusApplicationRoomRate.RoomRate();
        roomRate.setCurrencyCode("123");
        roomRate.setAmount(BigDecimal.valueOf(345));
        ArrayOfOTAHotelBookingRuleRSRuleMessageStatusApplicationRoomRate roomRates = new ArrayOfOTAHotelBookingRuleRSRuleMessageStatusApplicationRoomRate();
        roomRates.getRoomRate().add(roomRate);
        OTAHotelBookingRuleRS.RuleMessage.StatusApplication statusApplication = new OTAHotelBookingRuleRS.RuleMessage.StatusApplication();
        statusApplication.setRatePlanID("12ab");
        statusApplication.setRoomRates(roomRates);
        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        tpaExtensionsType.setCancelPolicyDeadLine("20/12/12");
        OTAHotelBookingRuleRS.RuleMessage ruleMessage = new OTAHotelBookingRuleRS.RuleMessage();
        ruleMessage.setHotelCode("abc");
        tpaExtensionsType.setNonRefundable("false");
        tpaExtensionsType.setBreakfastIncluded("true");
        ruleMessage.setStatusApplication(statusApplication);
        ruleMessage.setTPAExtensions(tpaExtensionsType);
        OTAHotelBookingRuleRS hotelBookingRuleRS = new OTAHotelBookingRuleRS();
        hotelBookingRuleRS.setRuleMessage(ruleMessage);
        HotelRateRuleResponse rateRuleResponse = mapper.map(hotelBookingRuleRS, "abc");
        assertThat(rateRuleResponse).isNotNull();
        assertThat(rateRuleResponse.getIsBreakfastIncluded()).isTrue();
        assertThat(rateRuleResponse.getIsCancellable()).isTrue();
    }

    @Test
    void testMapThrowsException() {
        Assertions.assertThrows(Exception.class, () -> {
            ErrorType errorType = new ErrorType();
            errorType.setCode("123");
            errorType.setValue("exception");
            ErrorsType errorsType = new ErrorsType();
            errorsType.getError().add(errorType);
            OTAHotelBookingRuleRS hotelBookingRuleRS = new OTAHotelBookingRuleRS();
            hotelBookingRuleRS.setErrors(errorsType);
            mapper.map(hotelBookingRuleRS, "anyString");
        });

    }
}