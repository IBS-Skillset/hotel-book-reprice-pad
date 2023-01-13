package com.hotel.mappers.book.request;

import com.hotel.service.common.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.CustomerType;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerAddressMapperTest {

   @InjectMocks
   CustomerAddressMapper customerAddressMapper;

   @Test
   void mapAddress() {

      Address address = Address.newBuilder()
              .setStreetAddress("Hotel Paris, Street1, France")
              .setCityName("Paris")
              .setZipCode("75102")
              .setCountryName("France")
              .setCountryCode("FR")
              .setPhoneNumber("9937166771")
              .build();

        CustomerType.Address  custAddress = customerAddressMapper.mapAddress(address);
        assertThat(custAddress).isNotNull();
        assertThat(address.getStreetAddress()).isEqualTo("Hotel Paris, Street1, France");
        assertThat(address.getCityName()).isEqualTo("Paris");
        assertThat(address.getZipCode()).isEqualTo("75102");
        assertThat(address.getCountryName()).isEqualTo("France");
        assertThat(address.getPhoneNumber()).isEqualTo("9937166771");

    }
}