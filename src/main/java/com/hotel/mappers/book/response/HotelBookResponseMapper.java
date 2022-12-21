package com.hotel.mappers.book.response;

import com.hotel.service.book.HotelBookResponse;
import com.hotel.service.common.ResponseStatus;
import com.hotel.util.APIConstants;
import org.opentravel.ota._2003._05.ArrayOfRoomStaysTypeRoomStay;
import org.opentravel.ota._2003._05.OTAHotelResRS;
import org.opentravel.ota._2003._05.RatePlanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class HotelBookResponseMapper {

    @Autowired
    private ErrorResponseMapper errorResponseMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RateMapper rateMapper;

    @Autowired
    private PnrInfoMapper pnrInfoMapper;

    public HotelBookResponse map(OTAHotelResRS response) {
        HotelBookResponse.Builder hotelBookResponseBuilder = HotelBookResponse.newBuilder();
        if (nonNull(response.getSuccess()) && nonNull(response.getHotelReservations())) {
            final ArrayOfRoomStaysTypeRoomStay.RoomStay roomStay = response.getHotelReservations().getHotelReservation().get(0)
                    .getRoomStays().getRoomStay().get(0);
            final RatePlanType ratePlanType = roomStay.getRatePlans().getRatePlan().get(0);
            ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(hotelBookResponseBuilder::setStartDate, roomStay.getTimeSpan().getStart());
            safeSetProtoField(hotelBookResponseBuilder::setEndDate, roomStay.getTimeSpan().getEnd());
            safeSetProtoField(hotelBookResponseBuilder::setRatePlanId, ratePlanType.getRatePlanID());
            safeSetProtoField(hotelBookResponseBuilder::setRateDescription, ratePlanType.getRatePlanDescription().getText());
            safeSetProtoField(hotelBookResponseBuilder::setHotelCode, roomStay.getBasicPropertyInfo().getHotelCode());
            safeSetProtoField(hotelBookResponseBuilder::setHotelName, roomStay.getBasicPropertyInfo().getHotelName());
            safeSetProtoField(hotelBookResponseBuilder::setHotelAddress, addressMapper.map(roomStay.getBasicPropertyInfo().getAddress()));
            safeSetProtoField(hotelBookResponseBuilder::setRoomRate,rateMapper.map(roomStay.getRoomRates().getRoomRate().get(0).getRates().getRate().get(0).getBase() , ratePlanType.getRatePlanDescription().getText()));
            safeSetProtoField(responseStatusBuilder::setStatus, APIConstants.SUCCESS);
            safeSetProtoField(hotelBookResponseBuilder::setResponseStatus, responseStatusBuilder);
            safeSetProtoField(hotelBookResponseBuilder::setPnrInfo, pnrInfoMapper.map(response.getHotelReservations().getHotelReservation().get(0).getTPAExtensions(),
                    response.getHotelReservations().getHotelReservation().get(0).getResGlobalInfo()));
            return hotelBookResponseBuilder.build();
        } else {
            return errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getValue(),
                    response.getErrors().getError().get(0).getCode());
        }


    }
}