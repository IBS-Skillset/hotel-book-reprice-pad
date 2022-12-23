package com.hotel.adapter;

import com.hotel.mappers.rateRule.response.HotelRateRuleResponseMapper;
import com.hotel.service.common.Context;
import com.hotel.service.raterule.HotelRateRuleRequest;
import com.hotel.service.raterule.HotelRateRuleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelRateRuleAdapterTest {

    private String endPointUrl = "https://traveldoo.koedia.com";
    private String service = "hotel-reservation";

    @Mock
    OTAHotelBookingRuleRQ hotelResRQ;

    @Mock
    private HotelRateRuleResponseMapper mapper;

    @InjectMocks
    HotelRateRuleAdapter client;
    @Test
    public void testRestClient() throws Exception {
            HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                    setRequestContext(Context.newBuilder().
                            setSupplierUrl(endPointUrl).build()).build();
            HotelRateRuleResponse rateRuleResponse = HotelRateRuleResponse.newBuilder().build();
            when(mapper.map(any(),any())).thenReturn(rateRuleResponse);
            HotelRateRuleResponse responseEntity = client.restClient(hotelResRQ,request);
            assertThat(responseEntity).isNotNull();
    }
}