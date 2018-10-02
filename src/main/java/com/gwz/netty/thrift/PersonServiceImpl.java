package com.gwz.netty.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonServce;

public class PersonServiceImpl implements PersonServce.Iface{
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param:" + username);

        Person person = new Person();
        person.setUsername(username).setAge(23).setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got Client Param:");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
