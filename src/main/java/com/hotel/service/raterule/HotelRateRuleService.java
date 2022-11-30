package com.hotel.service.raterule;

import com.hotel.exception.HotelException;
import com.hotel.mappers.common.POSMapper;
import com.hotel.mappers.request.raterule.RuleMessageMapper;
import com.hotel.mappers.response.HotelRateRuleResponseMapper;
import com.hotel.adaptor.HotelRateRuleAdapter;
import com.hotel.util.APIConstants;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.service.raterule.HotelRateRuleResponse;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HotelRateRuleService {

    @Autowired
    private POSMapper posMapper;

    @Autowired
    private RuleMessageMapper ruleMessageMapper;

    @Autowired
    private HotelRateRuleAdapter hotelRateRuleAdapter;

    @Autowired
    private HotelRateRuleResponseMapper mapper;

    public HotelRateRuleResponse getHotelRateRuleItemsFromSupplier(HotelRateRuleRequest request) {

        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        bookingRuleRQ.setPOS(posMapper.mapPOS(request));
        bookingRuleRQ.setRuleMessage(ruleMessageMapper.mapRuleMessage(request));
        OTAHotelBookingRuleRS hotelBookingRuleRS = new OTAHotelBookingRuleRS();
        try {
            Object response = hotelRateRuleAdapter.restClient(bookingRuleRQ, request);
            if (Objects.nonNull(response)) {
                hotelBookingRuleRS = (OTAHotelBookingRuleRS) response;
            }
            System.out.println("rate response : " +mapper.map(hotelBookingRuleRS));
            return mapper.map(hotelBookingRuleRS);
        } catch (Exception e) {
            throw new HotelException(e.getMessage(), APIConstants.SUPPLIER_SERVER_ERROR);
        }
    }
}
