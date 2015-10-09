package nl.ead.webservice.application;

import java.util.*;

public interface IPayPalService {
    Payment getPayPalPayment(int totalAmount);
    Payment gPayPalPayment(String paypalPaymentId);
}
