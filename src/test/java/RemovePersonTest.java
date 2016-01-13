import model.Book;
import org.junit.Test;
import services.JDBCStorageService;
import java.util.List;
import model.Person;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Юрий on 13.01.2016.
 */
public class RemovePersonTest {
    @Test
    public void equalsTest()
    {
        Book book=JDBCStorageService.TransactionScript.getInstance().defaultBook();

        JDBCStorageService.TransactionScript.getInstance().addPerson("Ivan", "123456", "Tver, Lenina st.", book);

        List<Person> persons = JDBCStorageService.TransactionScript.getInstance().listPersons();

        Long id1 = persons.stream()
                .filter(p -> p.getName().equals("Ivan") && p.getPhones().stream().anyMatch(phone -> phone.getPhone().equals("123456")) && p.getAddresses().stream().anyMatch(adr -> adr.getAddress().equals("Tver, Lenina st.")))
                .findFirst()
                .map(p -> p.getId()).orElse(-1L);

        JDBCStorageService.TransactionScript.getInstance().deletePerson(id1.toString(), book);

        persons = JDBCStorageService.TransactionScript.getInstance().listPersons();

        Long id2 = persons.stream()
                .filter(p -> p.getName().equals("Ivan") && p.getPhones().stream().anyMatch(phone -> phone.getPhone().equals("123456")) && p.getAddresses().stream().anyMatch(adr -> adr.getAddress().equals("Tver, Lenina st.")))
                .findFirst()
                .map(p -> p.getId()).orElse(-1L);

        assertNotEquals("remove person service method", id1, id2);
    }
}

