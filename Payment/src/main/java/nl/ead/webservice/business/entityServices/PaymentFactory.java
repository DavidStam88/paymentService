package nl.ead.webservice.business.entityServices;

/**
 * @author David Stam & Maikel Hennekes
 */

public class PaymentFactory implements IPaymentFactory {

    public PayPalPayment createPayPalPayment(int userId, String amount, String currency, boolean paymentConfirmed, String paypalId) {
        PayPalPayment ppp = new PayPalPayment();
        ppp.setUserId(userId);
        ppp.setAmount(amount);
        ppp.setPaymentConfirmed(paymentConfirmed);
        ppp.setPaypalId(paypalId);
        ppp.setCurrency(currency);
        return ppp;
    }
}
