package nl.ead.webservice.application;

import java.util.*;

// PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

/**
 * @author David Stam & Maikel Hennekes
 */

public class PayPalServiceMock implements IPayPalService {

    private String getAccessToken() {
      return "903485934289t547945";
    }

    public Payment getPayPalPayment(String paypalPaymentId, String payerId) {
      Payment ppp = new Payment();
      return ppp;
    }

    public Payment sendPayPalPayment(String totalAmount, String currency) {
      Payment payment = new Payment();
      payment.setId("LeukePaymentId");
      Links link = new Links();
      link.setHref("http://www.nu.nl");
      List<Links> links = new ArrayList<Links>();
      links.add(link);
      links.add(link);
      links.add(link);
      payment.setLinks(links);

      return payment;
    }
}
