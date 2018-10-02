package com.gwz.netty.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonServce;

public class ThriftClient {

    public static void main(String[] args) throws Exception {
        TTransport transport = new TFramedTransport(new TSocket("localhost",8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonServce.Client client = new PersonServce.Client(protocol);

        try {

            transport.open();

            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("----------------");

            Person person1 = new Person();
            person.setUsername("李四");
            person.setAge(11);
            person.setMarried(true);

            client.savePerson(person1);
        }catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(),ex);
        }finally {
            transport.close();
        }
    }
}
