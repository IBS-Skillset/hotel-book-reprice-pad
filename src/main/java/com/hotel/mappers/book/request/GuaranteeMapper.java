package com.hotel.mappers.book.request;

import com.hotel.service.book.PaymentInfo;
import org.opentravel.ota._2003._05.ArrayOfGuaranteeTypeGuaranteeAccepted;
import org.opentravel.ota._2003._05.GuaranteeType;
import org.opentravel.ota._2003._05.PaymentCardType;
import org.springframework.stereotype.Component;

@Component
public class GuaranteeMapper {

    public GuaranteeType map(PaymentInfo paymentInfo) {
        GuaranteeType guarantee = new GuaranteeType();
        ArrayOfGuaranteeTypeGuaranteeAccepted guaranteAccepted = new ArrayOfGuaranteeTypeGuaranteeAccepted();
        ArrayOfGuaranteeTypeGuaranteeAccepted.GuaranteeAccepted guaranteeAccepted = new ArrayOfGuaranteeTypeGuaranteeAccepted.GuaranteeAccepted();
        guaranteeAccepted.setGuaranteeTypeCode(paymentInfo.getPaymentType());
        PaymentCardType paymentCard = new PaymentCardType();
        paymentCard.setCardCode(paymentInfo.getCardType());
        paymentCard.setCardNumber(paymentInfo.getCardNumber());
        paymentCard.setSeriesCode(paymentInfo.getCvv());
        paymentCard.setExpireDate(paymentInfo.getCardExpireDate());
        paymentCard.setCardHolderName(paymentInfo.getCardHolderName());
        guaranteeAccepted.setPaymentCard(paymentCard);
        guaranteAccepted.getGuaranteeAccepted().add(guaranteeAccepted);
        guarantee.setGuaranteesAccepted(guaranteAccepted);
        return guarantee;
    }
}
