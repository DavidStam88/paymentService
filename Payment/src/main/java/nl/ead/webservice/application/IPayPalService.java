package nl.ead.webservice.application;

import java.util.*;

// PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

public interface IPayPalService {
    Payment sendPayPalPayment(int totalAmount);
    Payment getPayPalPayment(String paypalPaymentId);
}
