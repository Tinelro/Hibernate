import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.addResource("employee.hbm.xml");

        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

       public static void close() {
        sessionFactory.close();
       }
}


