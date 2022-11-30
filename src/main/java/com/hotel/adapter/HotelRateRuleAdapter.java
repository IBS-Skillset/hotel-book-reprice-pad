package com.hotel.adapter;

import com.hotel.exception.HotelException;
import com.hotel.endpoint.DjocaEndPointFactory;
import com.hotel.util.APIConstants;
import com.hotel.service.raterule.HotelRateRuleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class HotelRateRuleAdapter {

    public Object restClient(Object bookingRuleRQ, HotelRateRuleRequest request) throws Exception {
        StringWriter requestWriter = new StringWriter();
        JAXBContext context = DjocaEndPointFactory.context;
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(bookingRuleRQ, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestContext().getSupplierUrl());
            uriBuilder.path(APIConstants.SERVICE);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(),
                    requestWriter.toString(), String.class);
            System.out.println(responseEntity.getBody());
            System.out.println("response : " +unmarshaller.unmarshal(new StringReader(responseEntity.getBody())).toString());
            return unmarshaller.unmarshal(new StringReader(responseEntity.getBody()));

        } catch (JAXBException b) {
            throw new HotelException(b.getMessage(), APIConstants.SUPPLIER_SERVER_ERROR);
        } catch (Exception e) {
            throw new HotelException(e.getMessage(), APIConstants.SUPPLIER_SERVER_ERROR);
        }
    }

}
