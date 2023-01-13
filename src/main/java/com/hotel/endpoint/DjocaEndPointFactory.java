package com.hotel.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRS;
import org.opentravel.ota._2003._05.OTAHotelResRQ;
import org.opentravel.ota._2003._05.OTAHotelResRS;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Slf4j
public class DjocaEndPointFactory {

    static {
        try {
            context = JAXBContext.newInstance(
                    OTAHotelBookingRuleRQ.class,
                    OTAHotelBookingRuleRS.class,
                    OTAHotelResRQ.class,
                    OTAHotelResRS.class
            );
        } catch (JAXBException e) {
            log.info("Illegal State Exception thrown");
            throw new IllegalStateException("Cannot initialize DJOCA services");
        }
    }

    public static final JAXBContext context;

    private DjocaEndPointFactory() {

    }
}
