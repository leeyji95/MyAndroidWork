package com.lec.android.a007_activity;

import java.io.Serializable;

// Intent 에 담아 보내는 객체는 반드시 Serializable 되어 있어야 한다!!!
public class Person_intent implements Serializable {
    String name;
    int age;

    public Person_intent() {}

    public Person_intent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
}
