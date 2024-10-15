package com.berwald.api;

import com.google.gson.Gson;

public class JsonExample {
    public static void main(String [] args){
        //Serializando um objeto JAVA para JSON
        Person person = new Person("Fernanda",22);
        Gson gson = new Gson();
        String json = gson.toJson(person);
        System.out.println("JSON: "+json);

        //Deserializando JSON para objeto JAVA
        String jsonRespose = "{\"name\":\"John\"age\":30}";
        Person deserializedPerson = gson.fromJson(jsonRespose, Person.class);
        System.out.println(deserializedPerson.getName());
    }
}

class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}