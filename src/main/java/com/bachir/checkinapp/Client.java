/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bachir.checkinapp;
/**
 *
 * @author user
 */
public class Client {
    private String passport_nb;
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String tel;
    private String email;
    private Seat r;
    
    public Client(int ID, String firstName, String lastName, String password, String tel, String email) {
        this.id = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.tel = tel;
        this.email = email;
    }

    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
}
