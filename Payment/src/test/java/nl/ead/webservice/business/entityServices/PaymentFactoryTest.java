package nl.ead.webservice.business.entityServices;
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

public class PaymentFactoryTest {
    private IPaymentFactory paymentFactory;

    @Before
    public void setUp() throws Exception {
        paymentFactory = new PaymentFactory();
    }

    @Test
    public void testPaymentFactory() throws Exception {
        assertThat(paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId"), instanceOf(PayPalPayment.class));
        assertEquals(1, paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId").getUserId());
        assertEquals("50", paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId").getAmount());
        assertEquals(false, paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId").getPaymentConfirmed());
        assertEquals("paypalId", paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId").getPaypalId());
        assertEquals("USD", paymentFactory.createPayPalPayment(1, "50", "USD", false, "paypalId").getCurrency());
    };
}
