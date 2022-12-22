package com.hotel.adapter;

import com.hotel.service.common.Context;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.service.raterule.HotelRateRuleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HotelRateRuleAdapterTest {

    private String endPointUrl = "https://traveldoo.koedia.com";
    private String service = "hotel-reservation";

    @Mock
    OTAHotelBookingRuleRQ hotelResRQ;

    @InjectMocks
    HotelRateRuleAdapter client;

    @Test
    public void testRestClient() {
        Assertions.assertThrows(Exception.class, () -> {
            HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                    setRequestContext(Context.newBuilder().
                            setSupplierUrl(endPointUrl).build()).build();
            HotelRateRuleResponse responseEntity = client.restClient(hotelResRQ,request);
            assertThat(responseEntity).isNotNull();
        });
    }
}