package com.hotel.mappers.book.request;

import com.hotel.service.common.Address;
import com.hotel.service.common.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ArrayOfResGuestsTypeResGuest;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GuestMapperTest {
    @InjectMocks
    GuestMapper guestMapper;

    @Test
    public void testMap() {
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

        ArrayOfResGuestsTypeResGuest guest = guestMapper.map(userInfo);
        assertThat(guest.getResGuest().size()).isEqualTo(1);
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().size()).isEqualTo(1);
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName().getGivenName().get(0)).isEqualTo("Test");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getTelephone().get(0).getPhoneNumber()).isEqualTo("1234");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue()).isEqualTo("test@gmail.com");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getAddress().get(0).getCityName()).isEqualTo("Paris");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getAddress().get(0).getCountryName().getCode()).isEqualTo("FR");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getAddress().get(0).getCountryName().getValue()).isEqualTo("France");


    }



}