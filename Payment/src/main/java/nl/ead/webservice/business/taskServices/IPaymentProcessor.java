package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.business.entityServices.PayPalPayment;

/**
* @author David Stam & Maikel Hennekes
*/

public interface IPaymentProcessor {
  String sendPayment(int userId, String amount, String currency);
  PayPalPayment confirmPayment(String paymentId, String payerId);
  String cancelPayment(String paymentId);
}
