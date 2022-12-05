package com.hotel.mappers.book.request;

import com.hotel.util.APIConstants;
import org.opentravel.ota._2003._05.GuestCountType;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class GuestCountMapper {

    public GuestCountType map(int count) {
        GuestCountType guestCounts = new GuestCountType();
        GuestCountType.GuestCount guestCount = new GuestCountType.GuestCount();
        guestCount.setCount(BigInteger.valueOf(count));
        guestCount.setAgeQualifyingCode(APIConstants.AGE_QUALIFIER);
        guestCounts.getGuestCount().add(guestCount);
        return guestCounts;
    }
}
