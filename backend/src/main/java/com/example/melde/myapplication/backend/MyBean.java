package com.example.melde.myapplication.backend;

import com.example.Jockes;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private String myData;
Jockes jocke;

    public  MyBean(){
        jocke=new Jockes();
    }
    public String getData() {
        return jocke.getJoke();
    }

}