package com.hotel.mappers.rateRule.request;

import com.hotel.mappers.common.POSMapper;
import com.hotel.service.raterule.HotelRateRuleRequest;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateRuleRequestMapper {

    @Autowired
    private POSMapper posMapper;
    @Autowired
    private RuleMessageMapper ruleMessageMapper;

    public OTAHotelBookingRuleRQ getOTAHotelBookingRuleRQ(HotelRateRuleRequest request) {
        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        bookingRuleRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        bookingRuleRQ.setRuleMessage(ruleMessageMapper.mapRuleMessage(request));
        return bookingRuleRQ;
    }
}
