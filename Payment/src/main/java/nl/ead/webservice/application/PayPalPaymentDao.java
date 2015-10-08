package nl.ead.webservice.application;

import javax.persistence.*;
import nl.ead.webservice.business.entityServices.PayPalPayment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PayPalPaymentDao implements IPayPalPaymentDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void save(PayPalPayment paypalPayment) {
        em.persist(paypalPayment);
    }

    @Transactional
    @Override
    public PayPalPayment update(String paypalId) {
      Query q1 = em.createQuery("SELECT id FROM PayPalPayment where paypalId = '" + paypalId + "'");
      long id = (long)q1.getSingleResult();
      PayPalPayment ppp = em.find(PayPalPayment.class, id);
      ppp.setPaymentConfirmed(true);
      return ppp;
    };

    @Transactional
    @Override
    public void remove(String paypalId) {
      Query q1 = em.createQuery("SELECT id FROM PayPalPayment where paypalId = '" + paypalId + "'");
      long id = (long)q1.getSingleResult();
      PayPalPayment ppp = em.find(PayPalPayment.class, id);
      em.remove(ppp);
    };
}
