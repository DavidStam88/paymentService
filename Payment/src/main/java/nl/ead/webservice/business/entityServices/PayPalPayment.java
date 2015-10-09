package nl.ead.webservice.business.entityServices;

import javax.persistence.*;

/**
 * @author David Stam & Maikel Hennekes
 */

@Entity
public class PayPalPayment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int userId;
    private int amount;
    private String paypalId;
    private boolean paymentConfirmed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getPaymentConfirmed() {
        return paymentConfirmed;
    }

    public void setPaymentConfirmed(boolean paymentConfirmed) {
        this.paymentConfirmed = paymentConfirmed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId;
    }
}
