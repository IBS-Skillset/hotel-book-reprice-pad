package com.hotel.mappers.request.raterule;

import com.hotel.service.raterule.HotelRateRuleRequest;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class RuleMessageMapper {

    public OTAHotelBookingRuleRQ.RuleMessage mapRuleMessage(HotelRateRuleRequest request){
        OTAHotelBookingRuleRQ.RuleMessage ruleMessage = new OTAHotelBookingRuleRQ.RuleMessage();
        ruleMessage.setHotelCode(request.getHotelCode());
        OTAHotelBookingRuleRQ.RuleMessage.StatusApplication  statusApplication = new OTAHotelBookingRuleRQ.RuleMessage.StatusApplication();
        ruleMessage.setStatusApplication(statusApplication);
        statusApplication.setStart(request.getStartDate());
        statusApplication.setEnd(request.getEndDate());
        statusApplication.setRatePlanID(request.getRatePlanId());
        statusApplication.setNumberOfUnits(BigInteger.valueOf(request.getRoomCount()));
        return ruleMessage;
    }
}
