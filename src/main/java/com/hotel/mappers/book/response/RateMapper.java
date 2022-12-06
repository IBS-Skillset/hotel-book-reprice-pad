package com.hotel.mappers.book.response;

import com.hotel.service.book.RoomRate;
import org.opentravel.ota._2003._05.TotalType;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class RateMapper {
    public RoomRate map(TotalType rateResponse) {
        RoomRate.Builder roomRateBuilder = RoomRate.newBuilder();
        safeSetProtoField(roomRateBuilder::setCurrencyCode, rateResponse.getCurrencyCode());
        safeSetProtoField(roomRateBuilder::setTotalAmount, rateResponse.getAmountAfterTax().floatValue());
        return roomRateBuilder.build();
    }
}
