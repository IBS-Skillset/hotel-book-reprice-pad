package com.hotel.mappers.common;

import com.hotel.util.APIConstants;
import com.hotel.service.raterule.HotelRateRuleRequest;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.springframework.stereotype.Component;

@Component
public class POSMapper {

    public ArrayOfSourceType mapPOS(HotelRateRuleRequest request){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requesterID= new SourceType.RequestorID();

        requesterID.setID(request.getRequestContext().getSupplierRequestorId());
        requesterID.setMessagePassword(request.getRequestContext().getSupplierCredential());
        requesterID.setLanguageCode(request.getLanguageCode());
        requesterID.setType(APIConstants.TYPE);

        sourceType.setRequestorID(requesterID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }
}
