package com.hotel.mappers.book.request;

import com.hotel.service.common.UserInfo;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ArrayOfResGuestsTypeResGuest;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.EmailType;
import org.opentravel.ota._2003._05.PersonNameType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GuestMapperTest {

    @InjectMocks
    GuestMapper guestMapper;

    @Mock
    CustomerTypeMapper customerTypeMapper;


    @Test
    public void testMap() {
        UserInfo userInfo = UserInfo.newBuilder()
                .setNamePrefix("Mr")
                .setFirstName("Test")
                .setLastName("User")
                .setPhoneNumber("1234")
                .setEmail("test@gmail.com")
                .build();

        CustomerType customer = new CustomerType();
        PersonNameType personName = new PersonNameType();
        personName.getGivenName().add(userInfo.getFirstName());
        customer.setPersonName(personName);
        CustomerType.Telephone telephone = new CustomerType.Telephone();
        telephone.setPhoneNumber(userInfo.getPhoneNumber());
        customer.getTelephone().add(telephone);
        EmailType emailType = new EmailType();
        emailType.setValue(userInfo.getEmail());
        customer.getEmail().add(emailType);
        when(customerTypeMapper.map(userInfo)).thenReturn(customer);
        ArrayOfResGuestsTypeResGuest guest = guestMapper.map(userInfo);
        assertThat(guest.getResGuest()).hasSize(1);
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo()).hasSize(1);
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getPersonName().getGivenName().get(0)).isEqualTo("Test");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getTelephone().get(0).getPhoneNumber()).isEqualTo("1234");
        assertThat(guest.getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer().getEmail().get(0).getValue()).isEqualTo("test@gmail.com");
        verify(customerTypeMapper, atLeast(1)).map(userInfo);

    }

}