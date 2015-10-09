package nl.ead.webservice.orchestration;

import java.util.*;
import nl.ead.webservice.*;
import nl.ead.webservice.application.IPayPalPaymentDao;
import nl.ead.webservice.application.PayPalPaymentDao;
import nl.ead.webservice.business.entityServices.PayPalPayment;
import nl.ead.webservice.business.taskServices.IPaymentProcessor;
import nl.ead.webservice.business.taskServices.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PaymentEndpoint {
    private final IPaymentProcessor paymentProcessor;

    @Autowired
    public PaymentEndpoint(IPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @PayloadRoot(localPart = "SendPaymentRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public SendPaymentResponse sendPayment(@RequestPayload SendPaymentRequest req) {
        int userId = req.getInput().getUserId();
        int amount = req.getInput().getAmount();
        String redirectURL = this.paymentProcessor.sendPayment(userId, amount);

        SendPaymentOutput sendPaymentOutput = new SendPaymentOutput();
        sendPaymentOutput.setRedirectURL(redirectURL);
        SendPaymentResponse resp = new SendPaymentResponse();
        resp.setOutput(sendPaymentOutput);

        return resp;
    }

    @PayloadRoot(localPart = "ConfirmPaymentRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public ConfirmPaymentResponse confirmPayment(@RequestPayload ConfirmPaymentRequest req) {
        String paymentId = req.getInput().getPaymentId();
        PayPalPayment ppp = this.paymentProcessor.confirmPayment(paymentId);

        ConfirmPaymentOutput cpo = new ConfirmPaymentOutput();
        cpo.setConfirmed(ppp.getPaymentConfirmed());
        ConfirmPaymentResponse resp = new ConfirmPaymentResponse();
        resp.setOutput(cpo);

        return resp;
    };

    @PayloadRoot(localPart = "CancelPaymentRequest", namespace = "http://www.han.nl/schemas/messages")
    @ResponsePayload
    public CancelPaymentResponse cancelPayment(@RequestPayload CancelPaymentRequest req) {
      String paymentId = req.getInput().getPaymentId();
      this.paymentProcessor.cancelPayment(paymentId);

      CancelPaymentOutput cpo = new CancelPaymentOutput();
      cpo.setCanceled(true);
      CancelPaymentResponse resp = new CancelPaymentResponse();
      resp.setOutput(cpo);

      return resp;
    };
}
