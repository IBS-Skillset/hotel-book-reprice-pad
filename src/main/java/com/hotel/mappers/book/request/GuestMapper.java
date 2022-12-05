package com.hotel.mappers.book.request;

import com.hotel.service.common.UserInfo;
import org.opentravel.ota._2003._05.ArrayOfProfilesTypeProfileInfo;
import org.opentravel.ota._2003._05.ArrayOfResGuestsTypeResGuest;
import org.opentravel.ota._2003._05.ProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    @Autowired
    CustomerTypeMapper customerTypeMapper;

    public ArrayOfResGuestsTypeResGuest map(UserInfo userInfo) {
        ArrayOfResGuestsTypeResGuest arrayOfResGuestsTypeResGuest = new ArrayOfResGuestsTypeResGuest();
        ArrayOfResGuestsTypeResGuest.ResGuest resGuest = new ArrayOfResGuestsTypeResGuest.ResGuest();
        ArrayOfProfilesTypeProfileInfo profiles = new ArrayOfProfilesTypeProfileInfo()  ;
        ArrayOfProfilesTypeProfileInfo.ProfileInfo profileInfo = new ArrayOfProfilesTypeProfileInfo.ProfileInfo();
        ProfileType profile = new ProfileType();
        profile.setCustomer(customerTypeMapper.map(userInfo));
        profileInfo.setProfile(profile);
        profiles.getProfileInfo().add(profileInfo);
        resGuest.setProfiles(profiles);
        arrayOfResGuestsTypeResGuest.getResGuest().add(resGuest);
        return arrayOfResGuestsTypeResGuest;
    }
}
