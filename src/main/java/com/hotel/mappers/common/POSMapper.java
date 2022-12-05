package com.hotel.mappers.common;

import com.hotel.service.common.Context;
import com.hotel.util.APIConstants;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.springframework.stereotype.Component;

@Component
public class POSMapper {

    public ArrayOfSourceType mapPOS(Context request, String languageCode){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requesterID= new SourceType.RequestorID();

        requesterID.setID(request.getSupplierRequestorId());
        requesterID.setMessagePassword(request.getSupplierCredential());
        requesterID.setLanguageCode(languageCode);
        requesterID.setType(APIConstants.TYPE);

        sourceType.setRequestorID(requesterID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }
}
