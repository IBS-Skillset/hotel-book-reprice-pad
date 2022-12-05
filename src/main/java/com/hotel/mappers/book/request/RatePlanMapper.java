package com.hotel.mappers.book.request;

import org.opentravel.ota._2003._05.ArrayOfRatePlanType;
import org.opentravel.ota._2003._05.RatePlanType;
import org.springframework.stereotype.Component;

@Component
public class RatePlanMapper {

    public ArrayOfRatePlanType map(String ratePlanId) {
        ArrayOfRatePlanType ratePlans = new ArrayOfRatePlanType();
        RatePlanType ratePlan = new RatePlanType();
        ratePlan.setRatePlanID(ratePlanId);
        ratePlans.getRatePlan().add(ratePlan);
        return ratePlans;
    }
}
