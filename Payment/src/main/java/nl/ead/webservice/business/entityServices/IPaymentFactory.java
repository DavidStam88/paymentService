package nl.ead.webservice.business.entityServices;

public interface IPaymentFactory{
  public PayPalPayment createPayPalPayment(int userId, int amount,  boolean paymentConfirmed, String paypalId);
}
