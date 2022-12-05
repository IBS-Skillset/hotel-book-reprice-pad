package com.hotel.endpoint;

import org.opentravel.ota._2003._05.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class DjocaEndPointFactory {

    public static final JAXBContext context;

    static {
        try {
            context = JAXBContext.newInstance(
                    OTAHotelBookingRuleRQ.class,
                    OTAHotelBookingRuleRS.class,
                    OTAHotelResRQ.class,
                    OTAHotelResRS.class
            );
        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot initialize DJOCA services");
        }
    }
}
