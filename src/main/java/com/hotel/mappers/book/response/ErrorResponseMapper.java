package com.hotel.mappers.book.response;

import com.hotel.service.book.HotelBookResponse;
import com.hotel.service.common.ResponseStatus;
import com.hotel.util.APIConstants;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class ErrorResponseMapper {

    public HotelBookResponse mapErrorResponse (String message, String code) {
        HotelBookResponse.Builder hotelBookResponseBuilder = HotelBookResponse.newBuilder();
        ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
        safeSetProtoField(reponseStatusBuilder::setStatus, APIConstants.FAILURE);
        safeSetProtoField(reponseStatusBuilder::setErrorCode, code);
        safeSetProtoField(reponseStatusBuilder::setErrorMessage, message);
        safeSetProtoField(hotelBookResponseBuilder::setResponseStatus, reponseStatusBuilder);
        return hotelBookResponseBuilder.build();

    }
}
