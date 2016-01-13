package services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import configs.DBConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
public class DBModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        /**
         * {@link StorageService} имеет три реализации, а именно
         * {@link InMemoryStorage} - хранилище в памяти
         * {@link JDBCStorageService} - хранилище в БД с взаимодействием посредством jdbc
         * {@link HibernateStorageService} - хранилище в БД с взаимодействием посредством hibernate
         * Т.о. любой из вышеперечисленных классов может быть указан для инжектинга
         * guice в зависимые объекты.
         *
         * В {@link configs.DBConnection} можно найти настройки соединения с БД для каждого из
         * сервисов, зависящих от БД.
         */
        bind(StorageService.class).to(JDBCStorageService.class);
    }

    @Provides
    @Singleton
    public EntityManagerFactory getFactory()
    {
        Map<String, String> props = new HashMap<>();

        // настройки соединения
        props.put("hibernate.connection.url",           DBConnection.HIBERNATE.url());
        props.put("hibernate.connection.username",      DBConnection.HIBERNATE.username());
        props.put("hibernate.connection.password",      DBConnection.HIBERNATE.password());

        // класс драйвера jdbc для используемой базы данных
        props.put("hibernate.connection.driver_class",  "org.postgresql.Driver");
        // диалект hibernate для используемой БД
        props.put("hibernate.dialect",                  "org.hibernate.dialect.PostgreSQL9Dialect");


        /**
         *
         * максимальное количество соединений, хранимых в пуле соединений с БД. Рекомендуется всегда заменять на c3po
         * hibernate.c3p0.min_size=5
         * hibernate.c3p0.max_size=20
         * hibernate.c3p0.timeout=1800
         * hibernate.c3p0.max_statements=50
         *
         * @see https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html_single/#d5e150
         *
         * для детального объяснения
         */
        props.put("hibernate.connection.pool_size",     "1");


        /**
         * определяет стратегию синхронизации схемы БД с описанием
         * паппинага классов. Может принимать значения
         *
         * validate, update, create, create-drop
         *
         * @see https://docs.jboss.org/hibernate/orm/4.3/devguide/en-US/html_single/#d5e5095
         * for more details
         */
        props.put("hibernate.hbm2ddl.auto",             "update");

        return Persistence.createEntityManagerFactory("db-manager", props);

    }

    @Provides
    public EntityManager getManager(EntityManagerFactory managerFactory)
    {
        return managerFactory.createEntityManager();
    }

}
