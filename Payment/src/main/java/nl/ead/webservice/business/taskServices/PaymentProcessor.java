package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.application.PayPalPaymentDao;
import nl.ead.webservice.application.IPayPalPaymentDao;
import nl.ead.webservice.application.PayPalService;
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
  private final PayPalService paypalService;
  private final IPaymentFactory paymentFactory;

  @Autowired
  public PaymentProcessor(IPayPalPaymentDao paypalPaymentDao, PayPalService paypalService, IPaymentFactory paymentFactory) {
    this.paypalPaymentDao = paypalPaymentDao;
    this.paypalService = paypalService;
    this.paymentFactory = paymentFactory;
  }

  public String sendPayment(int userId, int amount) {
    Payment payment = this.paypalService.getPayPalPayment(amount);
    //PayPalPayment ppp = new PayPalPayment();
    boolean paymentConfirmed = false;
    String paypalId = payment.getId();
    PayPalPayment ppp = paymentFactory.createPayPalPayment(userId, amount, paymentConfirmed, paypalId);
    //ppp.setUserId(userId);
    //ppp.setAmount(amount);
    //ppp.setPaymentConfirmed(false);
    //ppp.setPaypalId(payment.getId());
    this.paypalPaymentDao.save(ppp);

    return payment.getLinks().get(1).getHref();
  }

  public PayPalPayment confirmPayment(String paymentId) {
    PayPalPayment ppp = this.paypalPaymentDao.update(paymentId);
    return ppp;
  }

  public void cancelPayment(String paymentId) {
    this.paypalPaymentDao.remove(paymentId);
  }
}
