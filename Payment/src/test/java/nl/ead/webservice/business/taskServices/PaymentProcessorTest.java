package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.application.*;
import nl.ead.webservice.business.entityServices.*;
import nl.ead.webservice.business.taskServices.*;
import nl.ead.webservice.orchestration.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;

public class PaymentProcessorTest {

    private PaymentProcessor paymentProcessor;

    @Before
    public void setUp() throws Exception {
        paymentProcessor = new PaymentProcessor(new PayPalPaymentDaoMock(), new PayPalServiceMock(), new PaymentFactory());
    }

    @Test
    public void testTest() throws Exception {
      assertEquals(5, paymentProcessor.checkForTest(2));
    };

    @Test
    public void functionGivesRedirectString() throws Exception {
      assertEquals("http://www.nu.nl", paymentProcessor.sendPayment(5, "12", "USD"));
    };

    @Test
    public void functionGivesComfirmedPayment() throws Exception {
      assertThat(paymentProcessor.confirmPayment("5", "payerId"), instanceOf(PayPalPayment.class));
      assertEquals(true, paymentProcessor.confirmPayment("5", "payerId").getPaymentConfirmed());
    };
}
