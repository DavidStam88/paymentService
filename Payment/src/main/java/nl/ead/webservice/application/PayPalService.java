package nl.ead.webservice.application;

import java.util.*;

// Nodige PayPal libraries
import com.paypal.api.payments.*;
import com.paypal.base.rest.*;

/**
* @author David Stam & Maikel Hennekes
* @used_source https://devtools-paypal.com/guide/pay_paypal
*/

public class PayPalService implements IPayPalService {
  // Het nodige client_id en de secret voor het verkrijgen van een access token voor de PayPal API.
  // Normaal gesproken zou je deze uit een extern bestand of databse willen halen.
  private final String client_secret = "";
  private final String client_id = "";
  private Map<String, String> sdkConfig;

  public PayPalService () {
    this.sdkConfig = new HashMap<String, String>();
    // De mode staat op "Sandbox": Een testomgeving van PayPal
    this.sdkConfig.put("mode", "sandbox");
  };

  // Vraag een access token voor de PayPal API
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
      //throw new PayPalRESTException("Shizzle! Kan geen access token krijgen!");
    };

    return accessToken;
  }

  // De betaling is gekeurd door PayPal, vraag de betalingsdata op via de PayPal API
  public Payment getPayPalPayment(String paypalPaymentId, String payerId) {
    // Normaal gesproken wil je de vergreken acces_token in een databse opslaan en deze gebruiken totdat deze verloopt
    // om vervolgens een nieuwe aan te vragen.
    String accessToken = this.getAccessToken();
    APIContext apiContext = this.setAPIContext(accessToken);

    Payment payment = new Payment();
    payment.setId(paypalPaymentId);
    PaymentExecution paymentExecute = new PaymentExecution();
    paymentExecute.setPayerId(payerId);

    Payment validatedPayment = null;

    try {
      validatedPayment = payment.execute(apiContext, paymentExecute);
    } catch (PayPalRESTException e) {
      System.out.println(e.getLocalizedMessage());
      //throw new PayPalRESTException("Shizzle! Kan geen conformatie krijgen!");
    }

    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.println(validatedPayment);

    return validatedPayment;
  }

  // Zend een nieuwe PayPalPayment naar de PayPal API voor conformatie.
  public Payment sendPayPalPayment(String totalAmount, String currency) {
    String accessToken = this.getAccessToken();
    APIContext apiContext = this.setAPIContext(accessToken);
    Amount amount = this.setAmount(totalAmount, currency);
    List<Transaction> transactions = this.setTransactionsList(amount);
    Payer payer = this.setPayer();
    RedirectUrls redirectUrls = this.setRedirectUrls();
    Payment payment = this.setPayment(payer, transactions, redirectUrls);
    Payment createdPayment = null;

    try {
      createdPayment = payment.create(apiContext);
    } catch (PayPalRESTException e) {
      System.out.println(e.getLocalizedMessage());
      //throw new PayPalRESTException("Shizzle! Kan geen payment sturen!");
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

  private Amount setAmount(String totalAmount, String currency) {
    Amount amount = new Amount();
    amount.setCurrency(currency);
    amount.setTotal(totalAmount);

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

  // De gebruikte redirect links zijn standaard "dummy-links" van PayPal aangezien we deze met het gebruik van localhost niet af kunnen vangen.
  private RedirectUrls setRedirectUrls() {
    RedirectUrls redirectUrls = new RedirectUrls();
    redirectUrls.setCancelUrl("https://devtools-paypal.com/guide/pay_paypal/java?cancel=true");
    redirectUrls.setReturnUrl("https://devtools-paypal.com/guide/pay_paypal/java?success=true");

    return redirectUrls;
  }
}
