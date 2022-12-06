package com.hotel.mappers.book.response;

import com.hotel.service.common.PnrInfo;
import com.hotel.util.APIConstants;
import org.opentravel.ota._2003._05.ResGlobalInfoType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class PnrInfoMapper {
    public PnrInfo map(TPAExtensionsType tpaExtensionsType, ResGlobalInfoType globalInfoType) {
        PnrInfo.Builder pnrInfo = PnrInfo.newBuilder();
        globalInfoType.getHotelReservationIDs().getHotelReservationID().forEach(resId -> {
            if (resId.getResIDSourceContext().equals(APIConstants.BOOKING_CODE)) {
                safeSetProtoField(pnrInfo::setBookingCode, resId.getResIDValue());
            } else if (resId.getResIDSourceContext().equals(APIConstants.CONFIRMATION_CODE)) {
                safeSetProtoField(pnrInfo::setConfirmationNumber, resId.getResIDValue());
            }
        });
        safeSetProtoField(pnrInfo::setBookingDescription, tpaExtensionsType.getStatus().getDescription());
        safeSetProtoField(pnrInfo::setBookingState, tpaExtensionsType.getStatus().getState());
        return pnrInfo.build();
    }
}
