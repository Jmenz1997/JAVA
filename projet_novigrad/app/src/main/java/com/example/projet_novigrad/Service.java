package com.example.projet_novigrad;

import java.util.ArrayList;

public class Service {
    /**
     * Name of the service.
     */
    private String name;

    /**
     * Price of the service
     */
    private double price;//voir les documents a fournir

    /**
     * List of the required documents.
     */
    private ArrayList<String> documents;

    /**
     * List of the required information.
     */
    private ArrayList<String> information;

    //Constructor with all specified parameters
    public Service(String name, double price, ArrayList<String> documents, ArrayList<String> information){
        this.name = name;
        this.price = price;
        this.documents = documents;
        this.information = information;
    }

    //Constructor with price and name only
    public Service(String name, double price){
        this.name = name;
        this.price = price;
    }

    //Constructor with no specified parameters
    public Service(){}

    //Getter and Setter methods

    //Methods for name
    public String getServiceName(){
        return name;
    }

    public void setServiceName(String n){
        name = n;
    }

    //Methods for price
    public double getPrice() { return price; }

    public void setPrice(double p) { price = p; }

    //Methods for documents
    public ArrayList<String> getDocuments() { return documents; }

    public void setDocuments(ArrayList<String> d) { documents = d; }

    //Methods for information
    public ArrayList<String> getInformation() { return information; }

    public void setInformation(ArrayList<String> i) { information = i; }

}



