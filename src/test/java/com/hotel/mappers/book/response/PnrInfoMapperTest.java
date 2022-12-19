package com.hotel.mappers.book.response;

import com.hotel.service.common.PnrInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfHotelReservationIDsTypeHotelReservationID;
import org.opentravel.ota._2003._05.ResGlobalInfoType;
import org.opentravel.ota._2003._05.StatusMode;
import org.opentravel.ota._2003._05.TPAExtensionsType;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class PnrInfoMapperTest {

    @InjectMocks
    PnrInfoMapper pnrInfoMapper;

    @Test
    public void map() {

        TPAExtensionsType tpaExtensionsType = getTpaExtensionsType();
        ResGlobalInfoType globalInfoType = getGlobalInfo();
        PnrInfo pnrInfo = pnrInfoMapper.map(tpaExtensionsType, globalInfoType);
        assertThat(pnrInfo).isNotNull();
        assertThat(pnrInfo.getBookingCode()).isEqualTo("1234");
        assertThat(pnrInfo.getBookingDescription()).isEqualTo("Confirmed");
        assertThat(pnrInfo.getBookingState()).isEqualTo("Done");
    }

    private ResGlobalInfoType getGlobalInfo() {
        ResGlobalInfoType globalInfoType = new ResGlobalInfoType();
        ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID hotelReservationID = new ArrayOfHotelReservationIDsTypeHotelReservationID.HotelReservationID();
        hotelReservationID.setResIDSourceContext("INT");
        hotelReservationID.setResIDValue("1234");
        ArrayOfHotelReservationIDsTypeHotelReservationID hotelReservationIDsTypeHotelReservationID = new ArrayOfHotelReservationIDsTypeHotelReservationID();
        hotelReservationIDsTypeHotelReservationID.getHotelReservationID().add(hotelReservationID);
        globalInfoType.setHotelReservationIDs(hotelReservationIDsTypeHotelReservationID);
        return globalInfoType;
    }

    private TPAExtensionsType getTpaExtensionsType() {
        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        StatusMode statusMode = new StatusMode();
        statusMode.setDescription("Confirmed");
        statusMode.setState("Done");
        tpaExtensionsType.setStatus(statusMode);
        return tpaExtensionsType;
    }
}