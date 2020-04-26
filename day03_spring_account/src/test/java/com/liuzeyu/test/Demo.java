package com.liuzeyu.test;

public class Demo {

    int value;

    public Demo(){
        value = 8;
    }
    public void out(){
        System.out.println("Demo 的value: " +value);
    }
}

class Child extends Demo{
    public Child(){

        super.out();
        value = 10;
        System.out.println(super.value);
    }

    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.value);
    }
}
