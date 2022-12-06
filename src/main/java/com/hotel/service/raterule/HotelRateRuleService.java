package com.hotel.service.raterule;

import com.hotel.adapter.HotelRateRuleAdapter;
import com.hotel.endpoint.DjocaEndPointFactory;
import com.hotel.mappers.common.POSMapper;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotel.mappers.rateRule.request.RuleMessageMapper;
import com.hotel.mappers.rateRule.response.HotelRateRuleResponseMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class HotelRateRuleService {

    @Autowired
    private POSMapper posMapper;
    @Autowired
    private RuleMessageMapper ruleMessageMapper;
    @Autowired
    private HotelRateRuleResponseMapper mapper;
    @Autowired
    private HotelRateRuleAdapter rateRuleAdaptor;

    public HotelRateRuleResponse getHotelRateRuleItemsFromSupplier(HotelRateRuleRequest request) throws Exception {
        OTAHotelBookingRuleRQ bookingRuleRQ = getOTAHotelBookingRuleRQ(request);
        OTAHotelBookingRuleRS hotelBookingRuleRS = new OTAHotelBookingRuleRS();
            String response = rateRuleAdaptor.restClient(bookingRuleRQ, request);
            try{
                if (Objects.nonNull(extracted(response))) {
                    hotelBookingRuleRS = (OTAHotelBookingRuleRS) extracted(response);
                }
                return mapper.map(hotelBookingRuleRS, patternMatcher(response));
            }
            catch (Exception e){
                log.info("Error while retrieving the HotelRateRule Response : " + e);
                throw e;
            }

    }

    public OTAHotelBookingRuleRQ getOTAHotelBookingRuleRQ(HotelRateRuleRequest request) {
        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        bookingRuleRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        bookingRuleRQ.setRuleMessage(ruleMessageMapper.mapRuleMessage(request));
        return bookingRuleRQ;
    }

    private static Object extracted(String response) throws JAXBException {
        JAXBContext context = DjocaEndPointFactory.context;
        return context.createUnmarshaller().unmarshal(new StringReader(response));
    }

    private String patternMatcher(String responseEntity) throws IOException {
        StringReader inputString = new StringReader(responseEntity);
        BufferedReader br = new BufferedReader(inputString);
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        Pattern pattern = Pattern.compile("(?<=<PenaltyDescription><Text xmlns=\"\" xmlns:ns2=\"http://www.opentravel.org/OTA/2003/05\">)(.*?)(?=</Text>)");
        Matcher matcher = pattern.matcher(sb);
        String group = null;
        while (matcher.find()) {
            group = matcher.group();
        }
        return group;
    }
}
