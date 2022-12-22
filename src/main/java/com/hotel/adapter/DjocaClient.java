package com.hotel.adapter;

import com.hotel.endpoint.DjocaEndPointFactory;
import com.hotel.exception.HotelBookException;
import com.hotel.util.APIConstants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DjocaClient {

    public Object restClient(Object request, String supplierUrl, String service) throws JAXBException {
        JAXBContext context = DjocaEndPointFactory.context;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(request, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(supplierUrl);
            uriBuilder.path(service);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(), requestWriter.toString(), String.class);
            log.info(requestWriter.toString());
            log.info(responseEntity.getBody());
            return unmarshaller.unmarshal(new StringReader(responseEntity.getBody()));
        } catch (JAXBException b) {
            log.info("JAXBException caught " + b);
            throw new HotelBookException(b.getMessage() , APIConstants.SUPPLIER_SERVER_ERROR);
        } catch (Exception e) {
            log.info("Exception occured in request-response to Djoca " + e);
            throw new HotelBookException(e.getMessage() , APIConstants.SUPPLIER_SERVER_ERROR);
        }
    }

}

