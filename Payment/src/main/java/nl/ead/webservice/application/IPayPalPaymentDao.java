package nl.ead.webservice.application;

import nl.ead.webservice.business.entityServices.PayPalPayment;

/**
 * @author David Stam & Maikel Hennekes
 */

public interface IPayPalPaymentDao {
    void save(PayPalPayment paypalPayment);
    PayPalPayment update(String paypalId);
    void remove(String paypalId);
}
