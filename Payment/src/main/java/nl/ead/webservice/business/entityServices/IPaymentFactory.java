package nl.ead.webservice.business.entityServices;

/**
 * @author David Stam & Maikel Hennekes
 */

public interface IPaymentFactory{
  public PayPalPayment createPayPalPayment(int userId, int amount,  boolean paymentConfirmed, String paypalId);
}
