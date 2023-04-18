package com.example.pumpwimo.models;

public class Parametr {

    private String parametrName; // название параметра
    private int parametrImage; // картинка

    public Parametr(String parametrName, int parametrImage) {
        this.parametrName = parametrName;
        this.parametrImage = parametrImage;
    }

    public void setParametrName(String parametrName) {
        this.parametrName = parametrName;
    }

    public void setParametrImage(int parametrImage) {
        this.parametrImage = parametrImage;
    }

    public String getParametrName() {
        return parametrName;
    }

    public int getParametrImage() {
        return parametrImage;
    }
}
