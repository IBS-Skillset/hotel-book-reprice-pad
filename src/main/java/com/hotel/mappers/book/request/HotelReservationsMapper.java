package com.hotel.mappers.book.request;

import com.hotel.mappers.common.POSMapper;
import com.hotel.service.book.HotelBookRequest;
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.OTAHotelResRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelReservationsMapper {

    @Autowired
    RoomStaysMapper roomStaysMapper;

    @Autowired
    GuestMapper guestMapper;

    @Autowired
    POSMapper posMapper;

    public OTAHotelResRQ map(HotelBookRequest request) {
        OTAHotelResRQ hotelResRQ = new OTAHotelResRQ();
        HotelReservationsType hotelReservationsType = new HotelReservationsType();
        HotelReservationType hotelReservationType = new HotelReservationType();
        hotelReservationType.setRoomStays(roomStaysMapper.map(request));
        hotelReservationType.setResGuests(guestMapper.map(request.getUserInfo()));
        hotelReservationsType.getHotelReservation().add(hotelReservationType);
        hotelResRQ.setHotelReservations(hotelReservationsType);
        hotelResRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        return hotelResRQ;
    }

}
