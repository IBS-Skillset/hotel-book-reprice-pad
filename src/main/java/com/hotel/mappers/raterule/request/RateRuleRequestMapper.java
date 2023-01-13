package com.hotel.mappers.raterule.request;

import com.hotel.mappers.common.POSMapper;
import com.hotel.service.raterule.HotelRateRuleRequest;
import lombok.AllArgsConstructor;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RateRuleRequestMapper {
    private POSMapper posMapper;
    private RuleMessageMapper ruleMessageMapper;

    public OTAHotelBookingRuleRQ getOTAHotelBookingRuleRQ(HotelRateRuleRequest request) {
        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        bookingRuleRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        bookingRuleRQ.setRuleMessage(ruleMessageMapper.mapRuleMessage(request));
        return bookingRuleRQ;
    }
}
