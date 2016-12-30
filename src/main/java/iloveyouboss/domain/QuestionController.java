package iloveyouboss.domain;


import iloveyouboss.BooleanQuestion;
import iloveyouboss.Question;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Clock;
import java.util.function.Consumer;

public class QuestionController {

    private Clock clock = Clock.systemUTC();

    private static EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("postgres-ds");
    }

    public Question find(Integer id) {
        return null;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public int addBooleanQuestion(String text) {
        return persist(new BooleanQuestion(text));
    }

    private int persist(Persistable object) {
        object.setCreateTimeStamp(clock.instant());
        executeInTransaction((em) -> em.persist(object));
        return object.getId();
    }

    private void executeInTransaction(Consumer<EntityManager> func) {
        EntityManager em = em();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            func.accept(em);
            transaction.commit();
        } catch (Throwable t) {
            t.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    private EntityManager em() {
        return getEntityManagerFactory().createEntityManager();
    }

}
