package com.hotel.adapter;

import com.hotel.endpoint.DjocaEndPointFactory;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.util.APIConstants;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
@Slf4j
public class HotelRateRuleAdapter {

    public String restClient(OTAHotelBookingRuleRQ bookingRuleRQ, HotelRateRuleRequest request) throws Exception {
        StringWriter requestWriter = new StringWriter();
        JAXBContext context = DjocaEndPointFactory.context;
        Marshaller marshaller = context.createMarshaller();
        try {
            marshaller.marshal(bookingRuleRQ, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(request.getRequestContext().getSupplierUrl() + APIConstants.SERVICE, requestWriter.toString(), String.class);
            return responseEntity.getBody();
        } catch (JAXBException b) {
            log.info("JAXBException caught : " + b);
            throw b;
        } catch (Exception e) {
            log.info("Exception occurred in request-response to Djoca : " + e);
            throw e;
        }

    }
}
