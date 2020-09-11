package Repositories;

import Domain.User;
import RepoInterface.IUserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepo implements IUserRepository {
    private SessionFactory sessionFactory;

    public UserRepo() {
        this.sessionFactory = hibernateUtils.getSessionFactory();
    }

    @Override
    public User findUser(User user) {
        User foundUser = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();//todo:aici may be wrong
                TypedQuery<User> q = session.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username and u.password = :password", User.class);
                q.setParameter("username", user.getUsername());
                q.setParameter("password",user.getPassword());
                foundUser = q.getSingleResult();
                //foundUser = (User)session.createQuery("from User user where user.username = :username and user.password = :password ",User.class).setParameter("username",user.getUsername()).
            //setParameter("password",user.getPassword()).setMaxResults(1).uniqueResult();
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                System.out.println(e);
            }
        }
        System.out.println("Repo found -- " + foundUser.getUsername() + "--" + foundUser.getPassword());
        return foundUser;
    }

    @Override
    public User findByUsername(User user) {
        User foundUser = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();//todo:aici may be wrong
                TypedQuery<User> q = session.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class);
                q.setParameter("username", user.getUsername());
                foundUser = q.getSingleResult();
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return foundUser;
    }

    @Override
    public void saveUser(User user) throws Exception {
        if(findByUsername(user) != null)
            throw new Exception("Username already exists!");
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();//todo:aici may be wrong
                session.save(user);
                tx.commit();
            } catch (RuntimeException e) {
                System.out.println(e);
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
