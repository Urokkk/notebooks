package services;

import model.Book;
import model.Person;

import java.util.List;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 18
 */
public interface StorageService
{
    void add(String personName, String phone, String address);

    void delete(String id);

    List<Person> list();

    void update(String id,String person,String phone,String address);

    Book defaultBook();

    void close();
}
