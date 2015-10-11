package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.application.IPayPalPaymentDao;
import nl.ead.webservice.application.IPayPalService;
import nl.ead.webservice.business.entityServices.PayPalPayment;
import nl.ead.webservice.business.entityServices.IPaymentFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author David Stam & Maikel Hennekes
 */

// PayPal libraries
import com.paypal.api.payments.*;

public class PaymentProcessor implements IPaymentProcessor {
  private final IPayPalPaymentDao paypalPaymentDao;
  private final IPayPalService paypalService;
  private final IPaymentFactory paymentFactory;

  @Autowired
  public PaymentProcessor(IPayPalPaymentDao paypalPaymentDao, IPayPalService paypalService, IPaymentFactory paymentFactory) {
    this.paypalPaymentDao = paypalPaymentDao;
    this.paypalService = paypalService;
    this.paymentFactory = paymentFactory;
  }

  public String sendPayment(int userId, String amount, String currency) {
    Payment payment = this.paypalService.sendPayPalPayment(amount, currency);
    boolean paymentConfirmed = false;
    String paypalId = payment.getId();
    PayPalPayment ppp = paymentFactory.createPayPalPayment(userId, amount, currency, paymentConfirmed, paypalId);
    this.paypalPaymentDao.save(ppp);

    return payment.getLinks().get(1).getHref();
  }

  public int checkForTest (int x) {
    return x + 3;
  };

  public PayPalPayment confirmPayment(String paymentId, String payerId) {
    Payment payment = this.paypalService.getPayPalPayment(paymentId, payerId);
    PayPalPayment ppp = this.paypalPaymentDao.find(paymentId);

    if (payment.getState() == "approved") {
      ppp = this.paypalPaymentDao.update(paymentId);
    }
    return ppp;
  }

  public void cancelPayment(String paymentId) {
    this.paypalPaymentDao.remove(paymentId);
  }
}
