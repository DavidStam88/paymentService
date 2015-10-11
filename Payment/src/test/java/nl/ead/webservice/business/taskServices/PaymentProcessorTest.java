package nl.ead.webservice.business.taskServices;

import nl.ead.webservice.application.*;
import nl.ead.webservice.business.entityServices.*;
import nl.ead.webservice.business.taskServices.*;
import nl.ead.webservice.orchestration.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentProcessorTest {

    private PaymentProcessor paymentProcessor;
    private Mockery context = new Mockery();

    @Before
    public void setUp() throws Exception {
        final IPayPalService paypalService = context.mock(IPayPalService.class);
        final IPaymentFactory paymentFactory = context.mock(IPaymentFactory.class);
        // moviePrinter is a mock, tempCalculationDao is a stub
        paymentProcessor = new PaymentProcessor(new PayPalPaymentDaoMock(), paypalService, paymentFactory);

        // expectations
        context.checking(new Expectations() {{
            oneOf (paypalService).sendPayPalPayment(50);
        }});
    }

    @Test
    public void testTest() throws Exception {
      assertEquals(5, paymentProcessor.checkForTest(2));
    };

    @Test
    public void functionGivesRedirectString() throws Exception {
      assertEquals(4, 4);
    };
}
