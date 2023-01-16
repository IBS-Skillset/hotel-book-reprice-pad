package com.hotel.adapter;

import com.hotel.endpoint.DjocaEndPointFactory;
import com.hotel.mappers.raterule.response.HotelRateRuleResponseMapper;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.service.raterule.HotelRateRuleResponse;
import com.hotel.util.APIConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@Component
public class HotelRateRuleAdapter {

    private HotelRateRuleResponseMapper mapper;

    public HotelRateRuleResponse restClient(OTAHotelBookingRuleRQ bookingRuleRQ, HotelRateRuleRequest request) throws Exception {
        OTAHotelBookingRuleRS hotelBookingRuleRS = new OTAHotelBookingRuleRS();
        StringWriter requestWriter = new StringWriter();
        JAXBContext context = DjocaEndPointFactory.context;
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(bookingRuleRQ, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(request.getRequestContext().getSupplierUrl() + APIConstants.SERVICE, requestWriter.toString(), String.class);
            if (Objects.nonNull(unmarshaller.unmarshal(new StringReader(Objects.requireNonNull(responseEntity.getBody())))))
                hotelBookingRuleRS = (OTAHotelBookingRuleRS) unmarshaller.unmarshal(new StringReader(responseEntity.getBody()));
            return mapper.map(hotelBookingRuleRS, patternMatcher(responseEntity.getBody()));
        } catch (JAXBException b) {
            log.info("JAXBException caught : " + b);
            throw b;
        } catch (Exception e) {
            log.info("Exception occurred in request-response to Djoca : " + e);
            throw e;
        }

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
