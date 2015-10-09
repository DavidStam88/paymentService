package nl.ead.webservice.business.entityServices;
public class PaymentFactory implements IPaymentFactory {

    private PaymentFactory(){
    }

    public PayPalPayment createPayPalPayment(int userId, int amount, boolean paymentConfirmed, String paypalId) {
        PayPalPayment ppp = new PayPalPayment();
        ppp.setUserId(userId);
        ppp.setAmount(amount);
        ppp.setPaymentConfirmed(paymentConfirmed);
        ppp.setPaypalId(paypalId);
        return ppp;
    }
}