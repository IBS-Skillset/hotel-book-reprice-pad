package com.hotel.mappers.book.request;

import com.hotel.service.book.HotelBookRequest;
import org.opentravel.ota._2003._05.ArrayOfRoomStaysTypeRoomStay;
import org.opentravel.ota._2003._05.ArrayOfRoomTypeType;
import org.opentravel.ota._2003._05.RoomTypeType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class RoomStaysMapper {

    @Autowired
    GuaranteeMapper guaranteeMapper;
    @Autowired
    GuestCountMapper guestCountMapper;
    @Autowired
    RatePlanMapper ratePlanMapper;

    public ArrayOfRoomStaysTypeRoomStay map(HotelBookRequest request) {
        ArrayOfRoomStaysTypeRoomStay arrayOfRoomStaysTypeRoomStay = new ArrayOfRoomStaysTypeRoomStay();
        ArrayOfRoomStaysTypeRoomStay.RoomStay roomStay = new ArrayOfRoomStaysTypeRoomStay.RoomStay();
        ArrayOfRoomTypeType roomType = new ArrayOfRoomTypeType();
        RoomTypeType roomTypeType = new RoomTypeType();
        roomTypeType.setNumberOfUnits(BigInteger.valueOf(request.getRoomCount()));
        roomType.getRoomType().add(roomTypeType);
        roomStay.setRoomTypes(roomType);
        roomStay.setRatePlans(ratePlanMapper.map(request.getRatePlanId()));
        roomStay.setGuestCounts(guestCountMapper.map(request.getGuestCount()));
        DateTimeSpanType timeSpan = new DateTimeSpanType();
        timeSpan.setStart(request.getStartDate());
        timeSpan.setEnd(request.getEndDate());
        roomStay.setTimeSpan(timeSpan);
        roomStay.getGuarantee().add(guaranteeMapper.map(request.getPaymentInfo()));
        BasicPropertyInfoType basicPropertyInfo = new BasicPropertyInfoType();
        basicPropertyInfo.setHotelCode(request.getHotelCode());
        roomStay.setBasicPropertyInfo(basicPropertyInfo);
        arrayOfRoomStaysTypeRoomStay.getRoomStay().add(roomStay);
        return arrayOfRoomStaysTypeRoomStay;

    }
}
