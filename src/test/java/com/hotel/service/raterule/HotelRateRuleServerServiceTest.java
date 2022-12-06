package com.hotel.service.raterule;

import com.hotel.service.common.Context;
import com.hotel.service.common.ResponseStatus;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelRateRuleServerServiceTest {

    @Mock
    private HotelRateRuleService hotelRateRuleService;

    @InjectMocks
    private HotelRateRuleServerService hotelRateRuleServerService;

    private String endPointUrl = "https://traveldoo.koedia.com/hotel-booking-rule";

    @Test
    public void testGetHotelRateRule() throws Exception {
        HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                setRequestContext(Context.newBuilder().setSupplierUrl(endPointUrl)).build();
        HotelRateRuleResponse response = HotelRateRuleResponse.newBuilder().setResponseStatus(ResponseStatus.newBuilder().setStatus(1).build()).build();
        StreamRecorder<HotelRateRuleResponse> responseStreamRecorder = StreamRecorder.create();
        when(hotelRateRuleService.getHotelRateRuleItemsFromSupplier(request)).thenReturn(response);
        hotelRateRuleServerService.getHotelRateRule(request, responseStreamRecorder);
        List<HotelRateRuleResponse> responseList = responseStreamRecorder.getValues();
        HotelRateRuleResponse rateRuleResponse = responseList.get(0);
        assertThat(responseList).isNotNull();
        assertThat(rateRuleResponse).isNotNull();
        verify(hotelRateRuleService, atLeast(1)).getHotelRateRuleItemsFromSupplier(request);
    }
    @Test
    public void testGetHotelRateRuleThrowsException() throws Exception {
        HotelRateRuleRequest request = HotelRateRuleRequest.newBuilder().
                setRequestContext(Context.newBuilder().setSupplierUrl(endPointUrl)).build();
        StreamRecorder<HotelRateRuleResponse> responseStreamRecorder = StreamRecorder.create();
        when(hotelRateRuleService.getHotelRateRuleItemsFromSupplier(request)).thenThrow(new Exception());
        hotelRateRuleServerService.getHotelRateRule(request, responseStreamRecorder);
        List<HotelRateRuleResponse> responseList = responseStreamRecorder.getValues();
        assertThat(responseList).isEmpty();
        verify(hotelRateRuleService, atLeast(1)).getHotelRateRuleItemsFromSupplier(request);
    }
}