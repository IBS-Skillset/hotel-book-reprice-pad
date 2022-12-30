package com.hotel.mappers.book.response;

import com.hotel.service.book.HotelBookResponse;
import com.hotel.service.common.ResponseStatus;
import com.hotel.util.APIConstants;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class ErrorResponseMapper {

    public HotelBookResponse mapErrorResponse(ErrorsType errorsType) {
        HotelBookResponse.Builder hotelBookResponseBuilder = HotelBookResponse.newBuilder();
        if (nonNull(errorsType) && !CollectionUtils.isEmpty((errorsType.getError()))) {
            ErrorType type = errorsType.getError().get(0);
            ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(reponseStatusBuilder::setStatus, APIConstants.FAILURE);
            safeSetProtoField(reponseStatusBuilder::setErrorCode, type.getCode());
            safeSetProtoField(reponseStatusBuilder::setErrorMessage, type.getValue());
            safeSetProtoField(hotelBookResponseBuilder::setResponseStatus, reponseStatusBuilder);
        }
        return hotelBookResponseBuilder.build();
    }
}
