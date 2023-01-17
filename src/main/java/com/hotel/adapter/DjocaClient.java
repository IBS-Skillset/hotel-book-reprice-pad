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
import java.util.Objects;

@Component
@Slf4j
public class DjocaClient {

    private final ErrorMappings errorMappings;

    public DjocaClient(ErrorMappings errorMappings) {
        this.errorMappings = errorMappings;
    }

    public Object restClient(Object request, String supplierUrl, String service) throws JAXBException {
        log.info("Request for DJocaClient " + request.toString());
        JAXBContext context = DjocaEndPointFactory.context;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(request, requestWriter);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(supplierUrl).path(service);
            ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(uriBuilder.build().toUriString(), requestWriter.toString(), String.class);
            log.info(requestWriter.toString());
            log.info(responseEntity.getBody());
            return unmarshaller.unmarshal(new StringReader(Objects.requireNonNull(responseEntity.getBody())));
        } catch (JAXBException b) {
            log.info("JAXBException caught " + b);
            throw new HotelBookException(b.getMessage() , errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode());
        } catch (Exception e) {
            log.info(errorMappings.getErrorMapping().get(APIConstants.DJOCA).getErrorMessage() + e);
            throw new HotelBookException(e.getMessage() , errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode());
        }
    }

}

