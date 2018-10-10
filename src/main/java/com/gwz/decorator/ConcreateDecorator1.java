package com.gwz.decorator;

public class ConcreateDecorator1 extends Decorator {
    public ConcreateDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    public void doAnotherThing(){
        System.out.println("功能B");
    }
}
