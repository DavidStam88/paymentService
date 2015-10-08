package nl.ead.webservice.application;

import java.util.*;

// PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

public class PayPalService {
  private final String client_secret = "";
  private final String client_id = "";

    private String getAccessToken() {
      // Instantiate PayPal credentials
      Map<String, String> sdkConfig = new HashMap<String, String>();
      sdkConfig.put("mode", "sandbox");

      // Get access_token from PayPal
      String accessToken = null;

      try {
        accessToken = new OAuthTokenCredential(
          this.client_id,
          this.client_secret,
          sdkConfig
        ).getAccessToken();
      } catch (PayPalRESTException e) {
        System.out.println(e.getLocalizedMessage());
      };

      return accessToken;
    }

    public Payment getPayPalPayment(int totalAmount) {
      // Instantiate PayPal credentials
      Map<String, String> sdkConfig = new HashMap<String, String>();
      sdkConfig.put("mode", "sandbox");

      String accessToken = this.getAccessToken();
      // Make payment request to PayPal with given credentials
      APIContext apiContext = new APIContext(accessToken);
      apiContext.setConfigurationMap(sdkConfig);

      Amount amount = new Amount();
      amount.setCurrency("USD");
      amount.setTotal(totalAmount + "");

      Transaction transaction = new Transaction();
      transaction.setDescription("creating a payment");
      transaction.setAmount(amount);

      List<Transaction> transactions = new ArrayList<Transaction>();
      transactions.add(transaction);

      Payer payer = new Payer();
      payer.setPaymentMethod("paypal");

      Payment payment = new Payment();
      payment.setIntent("sale");
      payment.setPayer(payer);
      payment.setTransactions(transactions);
      RedirectUrls redirectUrls = new RedirectUrls();
      redirectUrls.setCancelUrl("https://devtools-paypal.com/guide/pay_paypal/java?cancel=true");
      redirectUrls.setReturnUrl("https://devtools-paypal.com/guide/pay_paypal/java?success=true");
      payment.setRedirectUrls(redirectUrls);

      Payment createdPayment = null;

      try {
        createdPayment = payment.create(apiContext);
      } catch (PayPalRESTException e) {
        System.out.println(e.getLocalizedMessage());
      }

      System.out.println("------------------------------------------------------------------------------------");
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println("------------------------------------------------------------------------------------");
      System.out.println(createdPayment);

      return createdPayment;
    }
}
