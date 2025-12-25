package com.sm.fundamentals.whatisbean;

/**
 * Regular Java Object - NOT managed by Spring
 * Spring IOC is not responsible for creating and managing its lifecycle
 */
public class RegularJavaObject {
    private String name;
    public RegularJavaObject(){
        System.out.println("Regular Java Object Created");
    }
    public RegularJavaObject(String name)
    {
       this.name = name;
       System.out.println("Regular Obeject Created with Name");
    }
    public void doSomething()
    {
       System.out.println("Doing Something "+name);
    }

    public static void main(String[] args) {
        // I am creatimng it
        RegularJavaObject obj1 = new RegularJavaObject("Object 1");
        RegularJavaObject obj2 = new RegularJavaObject("Object 2");

        // I am  managing it
        obj1.doSomething();
        obj2.doSomething();

        // I am  destroying it (or let GC handle it)
        obj1 = null;
        obj2 = null;
    }
}
