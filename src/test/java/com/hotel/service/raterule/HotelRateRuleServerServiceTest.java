package com.hotel.service.raterule;

import com.hotel.adapter.HotelRateRuleAdapter;
import com.hotel.mappers.rateRule.request.RateRuleRequestMapper;
import com.hotel.service.common.Context;
import com.hotel.service.common.ResponseStatus;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class HotelRateRuleServerServiceTest {

    @Mock
    private HotelRateRuleAdapter rateRuleAdaptor;

    @Mock
    private RateRuleRequestMapper rateRuleRequestMapper;
    @InjectMocks
    private HotelRateRuleServerService hotelRateRuleServerService;

    private String endPointUrl = "https://traveldoo.koedia.com/hotel-booking-rule";

    @Test
    public void testGetHotelRateRule() throws Exception {
        HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                setRequestContext(Context.newBuilder().setSupplierUrl(endPointUrl)).build();
        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        HotelRateRuleResponse response = HotelRateRuleResponse.newBuilder().setResponseStatus(ResponseStatus.newBuilder().setStatus(1).build()).build();
        StreamRecorder<HotelRateRuleResponse> responseStreamRecorder = StreamRecorder.create();
        when(rateRuleRequestMapper.getOTAHotelBookingRuleRQ(request)).thenReturn(bookingRuleRQ);
        when(rateRuleAdaptor.restClient(bookingRuleRQ,request)).thenReturn(response);
        hotelRateRuleServerService.getHotelRateRule(request, responseStreamRecorder);
        List<HotelRateRuleResponse> responseList = responseStreamRecorder.getValues();
        HotelRateRuleResponse rateRuleResponse = responseList.get(0);
        assertThat(responseList).isNotNull();
        assertThat(rateRuleResponse).isNotNull();
        verify(rateRuleAdaptor, atLeast(1)).restClient(bookingRuleRQ,request);
    }
    @Test
    public void testGetHotelRateRuleThrowsException() throws Exception {
        HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                setRequestContext(Context.newBuilder().setSupplierUrl(endPointUrl)).build();
        OTAHotelBookingRuleRQ bookingRuleRQ = new OTAHotelBookingRuleRQ();
        StreamRecorder<HotelRateRuleResponse> responseStreamRecorder = StreamRecorder.create();
        when(rateRuleRequestMapper.getOTAHotelBookingRuleRQ(request)).thenReturn(bookingRuleRQ);
        when(rateRuleAdaptor.restClient(bookingRuleRQ,request)).thenThrow(new Exception());
        hotelRateRuleServerService.getHotelRateRule(request, responseStreamRecorder);
        List<HotelRateRuleResponse> responseList = responseStreamRecorder.getValues();
        assertThat(responseList).isEmpty();
        verify(rateRuleAdaptor, atLeast(1)).restClient(bookingRuleRQ,request);
    }
}