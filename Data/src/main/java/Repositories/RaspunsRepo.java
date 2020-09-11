package Repositories;

import Domain.*;
import RepoInterface.IRaspunsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class RaspunsRepo implements IRaspunsRepository {
    private SessionFactory sessionFactory;

    public RaspunsRepo(){
        this.sessionFactory=hibernateUtils.getSessionFactory();
    }

    @Override
    public boolean checkExistenceTigari(BrandTigari brandTigari) {
        BrandTigari foundCigarette = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String tig = brandTigari.getNume().substring(0,1).toUpperCase() + brandTigari.getNume().substring(1).toLowerCase();
                foundCigarette = (BrandTigari)session.createQuery("from BrandTigari tara where tara.nume =  :nume")
                        .setString("nume", tig).setMaxResults(1).uniqueResult();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                }
        }
        if(foundCigarette != null) return true;
        return false;
    }

    @Override
    public boolean checkExistenceMasina(BrandMasina brandMasina) {
        BrandMasina foundCar = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String mas = brandMasina.getNume().substring(0,1).toUpperCase() + brandMasina.getNume().substring(1).toLowerCase();
                foundCar = (BrandMasina)session.createQuery("from BrandMasina oras where oras.nume =  :nume")
                        .setString("nume", mas).setMaxResults(1).uniqueResult();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
            }
        }
        if(foundCar != null) return true;
        return false;
    }

    @Override
    public boolean checkExistenceTelefon(BrandTelefon brandTelefon) {
        BrandTelefon foundPhone = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String tel = brandTelefon.getNume().substring(0,1).toUpperCase() + brandTelefon.getNume().substring(1).toLowerCase();
                foundPhone = (BrandTelefon)session.createQuery("from BrandTelefon mare where mare.nume =  :nume")
                        .setString("nume", tel).setMaxResults(1).uniqueResult();

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                System.out.println(e);
            }
        }
        if(foundPhone != null) return true;
        return false;
    }
}
