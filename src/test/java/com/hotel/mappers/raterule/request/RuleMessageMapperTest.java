package com.hotel.mappers.raterule.request;

import com.hotel.mappers.rateRule.request.RuleMessageMapper;
import com.hotel.service.raterule.HotelRateRuleRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class RuleMessageMapperTest {

    @InjectMocks
    RuleMessageMapper ruleMessageMapper;

    @Test
    public void testMapRuleMessage() {
        HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                setHotelCode("abc").
                setRatePlanId("1234").build();
        OTAHotelBookingRuleRQ.RuleMessage ruleMessage = ruleMessageMapper.mapRuleMessage(request);
        assertThat(ruleMessage).isNotNull();
        assertThat(ruleMessage.getHotelCode()).isEqualTo("abc");
        assertThat(ruleMessage.getStatusApplication().getRatePlanID()).isEqualTo("1234");
    }
}