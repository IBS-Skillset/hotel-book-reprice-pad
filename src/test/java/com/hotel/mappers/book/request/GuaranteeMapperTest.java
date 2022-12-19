package com.hotel.mappers.book.request;

import com.hotel.service.book.PaymentInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.GuaranteeType;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GuaranteeMapperTest {

    @InjectMocks
    GuaranteeMapper guaranteeMapper;

    @Test
    public void map() {

        PaymentInfo paymentInfo = PaymentInfo.newBuilder()
                .setPaymentType("Card")
                .setCardType("Visa")
                .setCardNumber("1234")
                .setCvv("123")
                .setCardExpireDate("2023-11-18")
                .setCardHolderName("Test")
                .build();

        GuaranteeType guarantee = guaranteeMapper.map(paymentInfo);
        assertThat(guarantee).isNotNull();
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getGuaranteeTypeCode()).isEqualTo("Card");
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getPaymentCard().getCardCode()).isEqualTo("Visa");
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getPaymentCard().getCardNumber()).isEqualTo("1234");
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getPaymentCard().getSeriesCode()).isEqualTo("123");
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getPaymentCard().getExpireDate()).isEqualTo("2023-11-18");
        assertThat(guarantee.getGuaranteesAccepted().getGuaranteeAccepted().get(0).getPaymentCard().getCardHolderName()).isEqualTo("Test");


    }
}
