import model.Person;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;

/**
 * Created by user on 27.05.14.
 */
public class PersonTest {

    @Test
    public void equalsTest()
    {
        Person p1 = new Person("Ivanov", new HashSet(), new HashSet());
        Person p2 = new Person("Ivanov", new HashSet(), new HashSet());

        assertEquals("Equals Persons", p1, p2);
    }

}
