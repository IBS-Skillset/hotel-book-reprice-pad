package com.hotel.adapter;

import com.hotel.service.common.Context;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.service.raterule.HotelRateRuleResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;

import javax.xml.bind.JAXBException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HotelRateRuleAdapterTest {

    private String endPointUrl = "https://traveldoo.koedia.com";
    private String service = "hotel-reservation";

    @Mock
    OTAHotelBookingRuleRQ hotelResRQ;

    @InjectMocks
    HotelRateRuleAdapter client;

    @Test
    public void testRestClient() {
        try {
            HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                    setRequestContext(Context.newBuilder().
                            setSupplierUrl(endPointUrl).build()).build();
            HotelRateRuleResponse responseEntity = client.restClient(hotelResRQ,request);
            assertThat(responseEntity).isNotNull();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}