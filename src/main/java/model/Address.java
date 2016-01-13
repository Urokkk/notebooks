package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Юрий on 10.01.2016.
 */
public class Address {
    public Address() {
    }

    public Address(Person person, String address) {
        this.person = person;
        this.address = address;
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Long id;
    private Person person;
    private String address;
}
