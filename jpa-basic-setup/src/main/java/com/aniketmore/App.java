package com.aniketmore;

import javax.persistence.*;
import com.aniketmore.Message;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnitName");
            EntityManager em =  emf.createEntityManager();
            
            Message msg = new Message();
            msg.setText("hello from jpa");
            
            em.getTransaction().begin();
            em.persist(msg);
            em.getTransaction().commit();
            
            em.close();
            emf.close();
            System.out.println("created successfull: "+msg.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
