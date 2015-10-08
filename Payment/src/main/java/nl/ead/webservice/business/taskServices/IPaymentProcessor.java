package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.business.entityServices.PayPalPayment;

public interface IPaymentProcessor {
    String sendPayment(int userId, int amount);
    PayPalPayment confirmPayment(String paymentId);
    void cancelPayment(String paymentId);
}
