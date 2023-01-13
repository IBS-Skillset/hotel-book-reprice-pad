package com.hotel.mappers.book.request;

import com.hotel.service.common.Address;
import com.hotel.service.common.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.CountryNameType;
import org.opentravel.ota._2003._05.CustomerType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTypeMapperTest {

    @InjectMocks
    CustomerTypeMapper customerTypeMapper;

    @Mock
    CustomerAddressMapper customerAddressMapper;

    @Test
    public void map() {

        UserInfo userInfo = UserInfo.newBuilder()
                .setNamePrefix("Mr")
                .setFirstName("Test")
                .setLastName("User")
                .setPhoneNumber("1234")
                .setEmail("test@gmail.com")
                .setAddress(Address.newBuilder().setStreetAddress("Street1")
                        .setCityName("Paris")
                        .setCountryCode("FR")
                        .setCountryName("France")
                        .setZipCode("8789")
                        .build()).build();

        CustomerType.Address custAddress = new CustomerType.Address();
        custAddress.setCityName(userInfo.getAddress().getCityName());
        CountryNameType countryName = new CountryNameType();
        countryName.setValue(userInfo.getAddress().getCountryName());
        countryName.setCode(userInfo.getAddress().getCountryCode());
        custAddress.setCountryName(countryName);
        when(customerAddressMapper.mapAddress(userInfo.getAddress())).thenReturn(custAddress);
        CustomerType customer = customerTypeMapper.map(userInfo);
        assertThat(customer).isNotNull();
        assertThat(customer.getPersonName().getNamePrefix().get(0)).isEqualTo("Mr");
        assertThat(customer.getPersonName().getGivenName().get(0)).isEqualTo("Test");
        assertThat(customer.getTelephone().get(0).getPhoneNumber()).isEqualTo("1234");
        assertThat(customer.getEmail().get(0).getValue()).isEqualTo("test@gmail.com");
        assertThat(customer.getAddress().get(0).getCityName()).isEqualTo("Paris");
        assertThat(customer.getAddress().get(0).getCountryName().getCode()).isEqualTo("FR");
        assertThat(customer.getAddress().get(0).getCountryName().getValue()).isEqualTo("France");
        verify(customerAddressMapper, atLeast(1)).mapAddress(userInfo.getAddress());
    }
}
