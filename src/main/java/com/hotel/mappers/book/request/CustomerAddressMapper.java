package com.hotel.mappers.book.request;

import com.hotel.service.common.Address;
import org.opentravel.ota._2003._05.CountryNameType;
import org.opentravel.ota._2003._05.CustomerType;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper {

    public CustomerType.Address mapAddress(Address address) {
        CustomerType.Address custAddress = new CustomerType.Address();
        custAddress.getAddressLine().add(address.getStreetAddress());
        custAddress.setCityName(address.getCityName());
        custAddress.setPostalCode(address.getZipCode());
        CountryNameType countryName = new CountryNameType();
        countryName.setValue(address.getCountryName());
        countryName.setCode(address.getCountryCode());
        custAddress.setCountryName(countryName);
        return custAddress;

    }
}
