package com.hotel.mappers.response;

import com.hotel.service.raterule.HotelRateRuleResponse;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.stereotype.Component;

@Component
public class HotelRateRuleResponseMapper {

    public HotelRateRuleResponse map(OTAHotelBookingRuleRS hotelBookingRuleRS) {
        return HotelRateRuleResponse.newBuilder()
                .setHotelCode(hotelBookingRuleRS.getRuleMessage().getHotelCode())
                .setRatePlanId(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRatePlanID())
                .setCurrencyCode(hotelBookingRuleRS.getRuleMessage().getCurrencyCode())
                .setPenaltyDescriptionText(hotelBookingRuleRS.getRuleMessage().getBookingRules().getBookingRule().get(0).getCancelPenalties().getCancelPenalty().get(0).getPenaltyDescription().get(0).getText())
                .setCancelPolicyDeadLine(hotelBookingRuleRS.getRuleMessage().getTPAExtensions().getCancelPolicyDeadLine())
                .build();

    }
}
