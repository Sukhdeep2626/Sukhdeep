package com.example.sukhdeep;

public class FamilyDoctor {
    private int id;
    private String ClientName;
    private int age;
    private String address;

    public FamilyDoctor(int id, String ClientName,int age, String address){
        this.id = id;
        this.ClientName = ClientName;
        this.age = age;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}