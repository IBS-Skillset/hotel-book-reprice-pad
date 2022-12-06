package com.hotel.mappers.rateRule.response;

import com.hotel.service.raterule.HotelRateRuleResponse;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.stereotype.Component;

@Component
public class HotelRateRuleResponseMapper {

    public HotelRateRuleResponse map(OTAHotelBookingRuleRS hotelBookingRuleRS,String penaltyDescriptionText) throws  Exception {
        try{
            return HotelRateRuleResponse.newBuilder()
                    .setHotelCode(hotelBookingRuleRS.getRuleMessage().getHotelCode())
                    .setRatePlanId(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRatePlanID())
                    .setCurrencyCode(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRoomRates().getRoomRate().get(0).getCurrencyCode())
                    .setAmount(hotelBookingRuleRS.getRuleMessage().getStatusApplication().getRoomRates().getRoomRate().get(0).getAmount().toString())
                    .setPenaltyDescriptionText(penaltyDescriptionText)
                    .setCancelPolicyDeadLine(hotelBookingRuleRS.getRuleMessage().getTPAExtensions().getCancelPolicyDeadLine())
                    .build();
        }
        catch (Exception e){
            throw new Exception(hotelBookingRuleRS.getErrors().getError().get(0).getValue(),
                    new Throwable(hotelBookingRuleRS.getErrors().getError().get(0).getCode()));
        }
    }
}
