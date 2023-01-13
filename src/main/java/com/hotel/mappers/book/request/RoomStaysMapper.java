package com.hotel.mappers.book.request;

import com.hotel.service.book.HotelBookRequest;
import lombok.AllArgsConstructor;
import org.opentravel.ota._2003._05.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@AllArgsConstructor
public class RoomStaysMapper {

    private GuaranteeMapper guaranteeMapper;
    private GuestCountMapper guestCountMapper;
    private RatePlanMapper ratePlanMapper;

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
