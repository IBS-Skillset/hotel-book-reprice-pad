package com.hotel.mappers.book.request;

import com.hotel.service.common.UserInfo;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.EmailType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerTypeMapper {
    @Autowired
    CustomerAddressMapper customerAddressMapper;

    public CustomerType map(UserInfo userInfo) {
        CustomerType customer = new CustomerType();
        PersonNameType personName = new PersonNameType();
        personName.getNamePrefix().add(userInfo.getNamePrefix());
        personName.getGivenName().add(userInfo.getFirstName());
        personName.setSurname(userInfo.getLastName());
        customer.setPersonName(personName);

        CustomerType.Telephone telephone = new CustomerType.Telephone();
        telephone.setPhoneNumber(userInfo.getPhoneNumber());
        customer.getTelephone().add(telephone);
        EmailType emailType = new EmailType();
        emailType.setValue(userInfo.getEmail());
        customer.getEmail().add(emailType);
        customer.getAddress().add(customerAddressMapper.mapAddress(userInfo.getAddress()));
        return customer;
    }
}
