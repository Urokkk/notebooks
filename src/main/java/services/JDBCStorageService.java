package services;

import com.google.inject.Singleton;
import configs.DBConnection;
import model.Address;
import model.Book;
import model.Person;
import model.Phone;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
@Singleton
public class JDBCStorageService implements StorageService
{
    @Override
    public void add(String personName, String phone, String address)
    {

        TransactionScript.getInstance().addPerson(personName, phone, address, defaultBook());
    }

    @Override
    public void delete(String id) {
        TransactionScript.getInstance().deletePerson(id, defaultBook());
    }

    @Override
    public List<Person> list()
    {
        return TransactionScript.getInstance().listPersons();
    }

    @Override
    public void update(String id, String person, String phone, String address) {
        TransactionScript.getInstance().updatePerson(id,person,phone,address, defaultBook());
    }

    @Override
    public Book defaultBook()
    {
        return TransactionScript.getInstance().defaultBook();
    }

    @Override
    public void close()
    {
        try
        {
            TransactionScript.getInstance().close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static final class TransactionScript
    {
        private static final TransactionScript instance = new TransactionScript();

        public static TransactionScript getInstance() {
            return instance;
        }

        public TransactionScript()
        {
            String url      = DBConnection.JDBC.url();
            String login    = DBConnection.JDBC.username();
            String password = DBConnection.JDBC.password();

            try
            {
                connection = DriverManager.getConnection(url, login,
                        password);
            } catch (Exception e)
            {
                e.printStackTrace();
            };
        }

        public List<Person> listPersons()
        {
            List<Person> result = new ArrayList<>(10);

            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "select name, phone, address, p.id from book b \n" +
                        "inner join person p on b.id = p.book_id \n" +
                        "inner join phone ph on p.id = ph.person_id\n" +
                        "inner join address ad on p.id = ad.person_id\n");

                ResultSet r_set = statement.executeQuery();

                /*PreparedStatement staitment = connection.prepareStatement("Select address.address, address.person_id, person.name, person.id, phone.phone, phone.person_id" +
                        " from person, address, phone" +
                        " where address.person_id = phone.person_id = person.id and" +
                        "address.address =? and person.name =? and phone.phone =?");*/

                while (r_set.next())
                {
                    Person p = new Person(r_set.getString("name"));
                    Phone ph = new Phone(p, r_set.getString("phone"));
                    Address ad = new Address(p, r_set.getString("address"));

                    p.setId(r_set.getLong("id"));
                    p.getPhones().add(ph);
                    p.getAddresses().add(ad);
                    result.add(p);
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        public void addPerson(String person, String phone, String address, Book book)
        {
            try
            {
                if (book.getId() == null)
                {
                    PreparedStatement addBook = connection.prepareStatement("insert into book (id) values (DEFAULT)", Statement.RETURN_GENERATED_KEYS);
                    addBook.execute();
                    ResultSet generated_book_id = addBook.getGeneratedKeys();

                    if (generated_book_id.next())
                        book.setId(generated_book_id.getLong("id"));
                }

                PreparedStatement addPerson = connection.prepareStatement("insert into person (book_id, name) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addPhone  = connection.prepareStatement("insert into phone (person_id, phone) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement addAddress = connection.prepareStatement("insert into address (person_id, address) values (?, ?)", Statement.RETURN_GENERATED_KEYS);

                addPerson.setLong(1, book.getId());
                addPerson.setString (2, person);
                addPerson.execute();

                ResultSet auto_pk = addPerson.getGeneratedKeys();
                while (auto_pk.next())
                {
                    int id = auto_pk.getInt("id");
                    addPhone.setInt(1, id);
                    addPhone.setString(2, phone);
                    addPhone.execute();

                    addAddress.setInt(1, id);
                    addAddress.setString(2, address);
                    addAddress.execute();
                }

            }
            catch (Exception e)
            {
                System.out.println("ERROR: Person can not be added");
                e.printStackTrace();
            }
        }

        public void deletePerson(String ID, Book book)
        {
            try
            {
                Long id = Long.parseLong(ID);
                if (book.getId() == null)
                {
                    throw  new Exception("ERROR: Table book does not exist");
                }

                PreparedStatement staitment = connection.prepareStatement("Select name from person where book_id=? and id=?");
                staitment.setLong (1, book.getId());
                staitment.setLong (2, id);

                ResultSet queryResult = staitment.executeQuery();

                while (queryResult.next())
                {
                    PreparedStatement deletedPerson = connection.prepareStatement("delete from person where book_id=? and id=?");
                    PreparedStatement deletedPhone = connection.prepareStatement("delete from phone where person_id =?");
                    PreparedStatement deletedAdress = connection.prepareStatement("delete from address where person_id =?");

                    deletedPhone.setLong(1, id);
                    deletedPhone.executeUpdate();

                    deletedAdress.setLong(1, id);
                    deletedAdress.executeUpdate();

                    deletedPerson.setLong(1, book.getId());
                    deletedPerson.setLong(2, id);
                    deletedPerson.executeUpdate();
                }

            }
            catch (Exception e)
            {
                System.out.println("ERROR: Record can not deleted");
                e.printStackTrace();
            }
        }

        public void updatePerson(String ID, String person, String phone, String address, Book book)
        {
            try
            {
                Long id = Long.parseLong(ID);
                if (book.getId() == null)
                {
                    throw  new Exception("ERROR: Table book does not exist");
                }

                PreparedStatement staitment = connection.prepareStatement("Select name from person where book_id=? and id=?");
                staitment.setLong (1, book.getId());
                staitment.setLong (2, id);

                ResultSet queryResult = staitment.executeQuery();

                while (queryResult.next())
                {
                    PreparedStatement updatePerson = connection.prepareStatement("update person set name = ? where book_id = ? and id=?");
                    PreparedStatement updatePhone = connection.prepareStatement("update phone set phone = ? where person_id=?");
                    PreparedStatement updateAdress = connection.prepareStatement("update address set address = ? where person_id=?");

                    updatePhone.setString(1, phone);
                    updatePhone.setLong(2, id);
                    updatePhone.executeUpdate();

                    updateAdress.setString(1, address);
                    updateAdress.setLong(2, id);
                    updateAdress.executeUpdate();

                    updatePerson.setString(1, person);
                    updatePerson.setLong(2, book.getId());
                    updatePerson.setLong(3, id);
                    updatePerson.executeUpdate();
                }

            }
            catch (Exception e)
            {
                System.out.println("ERROR: Record can not updated");
                e.printStackTrace();
            }
        }

        public Book defaultBook()
        {
            // создаем новый экземпляр, который в дальнейшем и сохраним,
            // если не найдем для него записи в БД
            Book book = new Book();

            try
            {
                Statement statement = connection.createStatement();
                // выбираем из таблицы book единственную запись
                ResultSet books = statement.executeQuery("select id from book limit 1");
                // если хоть одна зепись в таблице нашлась, инициализируем наш объект полученными значениями
                if (books.next())
                {
                    book.setId(books.getLong("id"));
                }

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            // возвращаем проинициализированный или пустой объект книги
            return book;
        }

        public void close() throws SQLException
        {
            if (connection != null && !connection.isClosed())
                connection.close();
        }

        private Connection connection;
    }
}
