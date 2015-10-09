package nl.ead.webservice.application;

import java.util.*;

// PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

public class PayPalService implements IPayPalService {
  private final String client_secret = "EF_MBpSMNE-O9YUnlTRTj7am-N66DyUhACLPpbXLu4mE9RqktfdJbRkvoG3rrzR4VkjTvqepNAK4gnDt";
  private final String client_id = "AaRCve_7U61qh8t0QnbTmxpqC8SdDIlasPpt1SbA2oxTmZ9xGTn7jcuLwrp9mmJFMghWyrXQt8j-96h6";
  private Map<String, String> sdkConfig;

  public PayPalService () {
    this.sdkConfig = new HashMap<String, String>();
    this.sdkConfig.put("mode", "sandbox");
  };

    private String getAccessToken() {
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

    public Payment getPayPalPayment(String paypalPaymentId) {
      Payment ppp = new Payment();
      return ppp;
    }

    public Payment sendPayPalPayment(int totalAmount) {
      String accessToken = this.getAccessToken();
      APIContext apiContext = this.setAPIContext(accessToken);
      Amount amount = this.setAmount(totalAmount);
      List<Transaction> transactions = this.setTransactionsList(amount);
      Payer payer = this.setPayer();
      RedirectUrls redirectUrls = this.setRedirectUrls();
      Payment payment = this.setPayment(payer, transactions, redirectUrls);
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
