package com.hotel.mappers.book.request;

import com.hotel.mappers.common.POSMapper;
import com.hotel.service.book.HotelBookRequest;
import com.hotel.service.common.Context;
import com.hotel.service.common.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ArrayOfResGuestsTypeResGuest;
import org.opentravel.ota._2003._05.ArrayOfRoomStaysTypeRoomStay;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.OTAHotelResRQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HotelReservationsMapperTest {

    @InjectMocks
    HotelReservationsMapper hotelReservationsMapper;
    @Mock
    GuestMapper guestMapper;
    @Mock
    POSMapper posMapper;
    @Mock
    RoomStaysMapper roomStaysMapper;

    @Test
    public void testMap() {
        HotelBookRequest request = HotelBookRequest.newBuilder()
                .setRequestContext(Context.newBuilder().build())
                .setUserInfo(UserInfo.newBuilder().build())
                .setLanguageCode("FR")
                .build();
        when(roomStaysMapper.map(request)).thenReturn(new ArrayOfRoomStaysTypeRoomStay());
        when(guestMapper.map(request.getUserInfo())).thenReturn(new ArrayOfResGuestsTypeResGuest());
        when(posMapper.mapPOS(request.getRequestContext(),request.getLanguageCode())).thenReturn(new ArrayOfSourceType());
        OTAHotelResRQ hotelResRQ = hotelReservationsMapper.map(request);
        assertThat(hotelResRQ).isNotNull();
        assertThat(hotelResRQ.getHotelReservations().getHotelReservation().size()).isEqualTo(1);
        assertThat(hotelResRQ.getHotelReservations().getHotelReservation().get(0).getResGuests()).isNotNull();
        assertThat(hotelResRQ.getHotelReservations().getHotelReservation().get(0).getRoomStays()).isNotNull();
        assertThat(hotelResRQ.getPOS()).isNotNull();
        verify(roomStaysMapper,atLeast(1)).map(request);
        verify(guestMapper,atLeast(1)).map(any());
        verify(posMapper,atLeast(1)).mapPOS(any(),anyString());

    }

}