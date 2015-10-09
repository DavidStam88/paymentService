package nl.ead.webservice.application;

import java.util.*;

// PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

public class PayPalService {
  private final String client_secret = "";
  private final String client_id = "";
  private Map<String, String> sdkConfig;

  public PayPalService () {
    this.sdkConfig = new HashMap<String, String>();
    this.sdkConfig.put("mode", "sandbox");
  };

    private String getAccessToken() {
      // Instantiate PayPal credentials
      //Map<String, String> sdkConfig = new HashMap<String, String>();
      //this.sdkConfig.put("mode", "sandbox");

      // Get access_token from PayPal
      String accessToken = null;

      try {
        accessToken = new OAuthTokenCredential(
          this.client_id,
          this.client_secret,
          this.sdkConfig
        ).getAccessToken();
      } catch (PayPalRESTException e) {
        System.out.println(e.getLocalizedMessage());
      };

      return accessToken;
    }

    public PayPalPayment getPayPalPayment(String paypalPaymentId) {
      PayPalPayment ppp = new PayPalPayment();

      return ppp;
    }

    public Payment getPayPalPayment(int totalAmount) {
      // Instantiate PayPal credentials
      this.sdkConfig.put("mode", "sandbox");

      String accessToken = this.getAccessToken();
      // Make payment request to PayPal with given credentials
      APIContext apiContext = this.setAPIContext(accessToken);
      //APIContext apiContext = new APIContext(accessToken);
      //apiContext.setConfigurationMap(this.sdkConfig);

      Amount amount = this.setAmount(totalAmount);
      //Amount amount = new Amount();
      //amount.setCurrency("USD");
      //amount.setTotal(totalAmount + "");

      List<Transaction> transactions = this.setTransactionsList(amount);
      //Transaction transaction = new Transaction();
      //transaction.setDescription("creating a payment");
      //transaction.setAmount(amount);

      //List<Transaction> transactions = new ArrayList<Transaction>();
      //transactions.add(transaction);

      Payer payer = this.setPayer();
      //Payer payer = new Payer();
      //payer.setPaymentMethod("paypal");

      RedirectUrls redirectUrls = this.setRedirectUrls();
      //RedirectUrls redirectUrls = new RedirectUrls();
      //redirectUrls.setCancelUrl("https://devtools-paypal.com/guide/pay_paypal/java?cancel=true");
      //redirectUrls.setReturnUrl("https://devtools-paypal.com/guide/pay_paypal/java?success=true");

      Payment payment = this.setPayment(payer, transactions, redirectUrls);

      //Payment payment = new Payment();
      //payment.setIntent("sale");
      //payment.setPayer(payer);
      //payment.setTransactions(transactions);
      //payment.setRedirectUrls(redirectUrls);

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

    private APIContext setAPIContext(String accessToken) {
      APIContext apiContext = new APIContext(accessToken);
      apiContext.setConfigurationMap(this.sdkConfig);

      return apiContext;
    }

    private Amount setAmount(int totalAmount) {
      Amount amount = new Amount();
      amount.setCurrency("USD");
      amount.setTotal(totalAmount + "");

      return amount;
    }

    private List<Transaction> setTransactionsList(Amount amount) {
      Transaction transaction = new Transaction();
      transaction.setDescription("creating a payment");
      transaction.setAmount(amount);
      List<Transaction> transactions = new ArrayList<Transaction>();
      transactions.add(transaction);

      return transactions;
    }

    private Payer setPayer() {
      Payer payer = new Payer();
      payer.setPaymentMethod("paypal");

      return payer;
    }

    private Payment setPayment(Payer payer, List<Transaction> transactions, RedirectUrls redirectUrls) {
      Payment payment = new Payment();
      payment.setIntent("sale");
      payment.setPayer(payer);
      payment.setTransactions(transactions);
      payment.setRedirectUrls(redirectUrls);

      return payment;
    }

    private RedirectUrls setRedirectUrls() {
      RedirectUrls redirectUrls = new RedirectUrls();
      redirectUrls.setCancelUrl("https://devtools-paypal.com/guide/pay_paypal/java?cancel=true");
      redirectUrls.setReturnUrl("https://devtools-paypal.com/guide/pay_paypal/java?success=true");

      return redirectUrls;
    }
}
