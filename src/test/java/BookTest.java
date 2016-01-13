import model.Address;
import org.junit.Assert;
import model.Book;
import model.Person;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 27.05.14.
 */
public class BookTest {

    @Test
    public void fillBook()
    {

        Set<Person> persons = new HashSet<Person>();
        persons.add(new Person("Ivan Ivanovich", new HashSet(), new HashSet()));
        persons.add(new Person("Petr", new HashSet(), new HashSet()));
        persons.add(new Person("Sidor", new HashSet(), new HashSet()));
        Book book = new Book(persons);

        Assert.assertNotNull(book.getPersons());
        Assert.assertFalse(book.getPersons().isEmpty());
    }

}
