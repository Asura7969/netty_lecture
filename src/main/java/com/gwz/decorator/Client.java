package com.gwz.decorator;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreateDecorator2(new ConcreateDecorator1(new ConcreteComponent()));
        component.doSomething();
    }
}
