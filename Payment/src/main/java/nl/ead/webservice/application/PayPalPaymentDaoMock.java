package nl.ead.webservice.application;

import nl.ead.webservice.business.entityServices.PayPalPayment;

/**
 * @David Stam & Maikel Hennekes
*/

public class PayPalPaymentDaoMock implements IPayPalPaymentDao {
    public void save(PayPalPayment paypalPayment) {
        System.out.println("Doe niets");
    }

    public PayPalPayment find(String paypalId) {
      PayPalPayment ppp = new PayPalPayment();
      return ppp;
    };

    public PayPalPayment update(String paypalId) {
      PayPalPayment ppp = new PayPalPayment();
      return ppp;
    };

    public void remove(String paypalId) {
        System.out.println("Doe niets");
    };
}
