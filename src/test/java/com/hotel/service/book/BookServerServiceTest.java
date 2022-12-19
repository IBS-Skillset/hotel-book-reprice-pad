package com.hotel.service.book;

import com.hotel.adapter.DjocaClient;
import com.hotel.mappers.book.request.HotelReservationsMapper;
import com.hotel.mappers.book.response.HotelBookResponseMapper;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelResRQ;
import org.opentravel.ota._2003._05.OTAHotelResRS;

import javax.xml.bind.JAXBException;

import static com.hotel.util.APIConstants.BOOK_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class BookServerServiceTest {

    @Mock
    HotelReservationsMapper hotelReservationsMapper;
    @Mock
    HotelBookResponseMapper hotelBookResponseMapper;
    @Mock
    private DjocaClient djocaClient;
    @InjectMocks
    private BookServerService bookServerService;

    @Test
    public void getHotelBook() throws JAXBException {
        HotelBookRequest request = getRequest();
        OTAHotelResRS otaHotelResRS = new OTAHotelResRS();
        OTAHotelResRQ otaHotelResRQ = new OTAHotelResRQ();
        HotelBookResponse.Builder hotelBookResponse = HotelBookResponse.newBuilder();
        StreamRecorder<HotelBookResponse> responseStreamRecorder = StreamRecorder.create();
        when(hotelReservationsMapper.map(request)).thenReturn(otaHotelResRQ);
        when(djocaClient.restClient(otaHotelResRQ, request.getRequestContext().getSupplierUrl(), BOOK_SERVICE)).thenReturn(otaHotelResRS);
        when(hotelBookResponseMapper.map(otaHotelResRS)).thenReturn(hotelBookResponse.build());
        bookServerService.getHotelBook(request, responseStreamRecorder);
        assertThat(responseStreamRecorder).isNotNull();
        verify(hotelReservationsMapper, atLeast(1)).map(request);
        verify(djocaClient, atLeast(1)).restClient(otaHotelResRQ, request.getRequestContext().getSupplierUrl(), BOOK_SERVICE);
        verify(hotelBookResponseMapper, atLeast(1)).map(otaHotelResRS);
    }

    private HotelBookRequest getRequest() {
        HotelBookRequest request = HotelBookRequest.newBuilder().build();
        return request;
    }
}