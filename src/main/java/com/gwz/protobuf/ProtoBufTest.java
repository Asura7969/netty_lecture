package com.gwz.protobuf;

public class ProtoBufTest {

    public static void main(String[] args) throws Exception{
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("Asura").setAge(23).setAddress("上海").build();
        byte[] bytes = student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(bytes);

        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());

    }
}
