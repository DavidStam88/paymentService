package nl.ead.webservice.business.entityServices;

/**
* @author David Stam & Maikel Hennekes
*/

public interface IPaymentFactory{
  public PayPalPayment createPayPalPayment(int userId, String amount, String currency, boolean paymentConfirmed, String paypalId);
}
