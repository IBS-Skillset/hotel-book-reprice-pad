package com.hotel.mappers.raterule.response;

import com.hotel.service.raterule.HotelRateRuleResponse;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.stereotype.Component;

import static com.hotel.util.APIConstants.FALSE;
import static com.hotel.util.APIConstants.TRUE;

@Component
public class HotelRateRuleResponseMapper {

    public HotelRateRuleResponse map(OTAHotelBookingRuleRS hotelBookingRuleRS, String penaltyDescriptionText) throws Exception {
        try {
            return HotelRateRuleResponse.newBuilder()
                    .setHotelCode(hotelBookingRuleRS.getRuleMessage().getHotelCode())
                    .setRatePlanId(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRatePlanID())
                    .setCurrencyCode(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRoomRates().getRoomRate().get(0).getCurrencyCode())
                    .setAmount(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRoomRates().getRoomRate().get(0).getAmount().toString())
                    .setPenaltyDescriptionText(penaltyDescriptionText)
                    .setCancelPolicyDeadLine(hotelBookingRuleRS.getRuleMessage().getTPAExtensions().getCancelPolicyDeadLine())
                    .setIsCancellable(hotelBookingRuleRS.getRuleMessage().getTPAExtensions().getNonRefundable().equals(FALSE))
                    .setIsBreakfastIncluded(hotelBookingRuleRS.getRuleMessage().getTPAExtensions().getBreakfastIncluded().equals(TRUE))
                    .build();
        } catch (Exception e) {
            throw new Exception(hotelBookingRuleRS.getErrors().getError().get(0).getValue(),
                    new Throwable(hotelBookingRuleRS.getErrors().getError().get(0).getCode()));
        }
    }
}
