package com.hotel.adapter;

import com.hotel.exception.HotelBookException;
import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.common.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelResRQ;
import org.opentravel.ota._2003._05.OTAHotelResRS;
import org.springframework.web.client.RestClientException;

import javax.xml.bind.JAXBException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class DjocaClientTest {

    private String endPointUrl = "https://traveldoo.koedia.com";
    private String service = "hotel-reservation";

    @Mock
    OTAHotelResRQ hotelResRQ;

    @InjectMocks
    DjocaClient client;

    @Test
    public void restClient() {
        try {

            Object responseEntity = client.restClient(hotelResRQ, endPointUrl, service);
            OTAHotelResRS response = (OTAHotelResRS) responseEntity;
            assertThat(responseEntity).isNotNull();
            assertThat(response.getErrors()).isNotNull();
            assertThat(response.getErrors().getError().get(0).getValue()).isEqualTo("Invalid Profile");

        } catch (JAXBException | RestClientException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = HotelBookException.class)
    public void testException() {
        try {
            HotelAvailabilityRequest request = HotelAvailabilityRequest.newBuilder().
                    setRequestContext(Context.newBuilder().
                            setSupplierUrl(endPointUrl).build()).build();
            Object responseEntity = client.restClient(request, endPointUrl, service);
            assertThat(responseEntity).isNull();

        } catch (JAXBException | RestClientException e) {
            e.printStackTrace();
        }
    }
}