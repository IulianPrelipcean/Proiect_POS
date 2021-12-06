package com.example.bookorder.Repository;


public class CustomClientImpl implements CustomClient {
    private String collectionName;

    @Override
    public void setCollectionName(String clientId){
        collectionName = "client_" + clientId;
    }

    @Override
    public String getCollectionName(){
        return collectionName;
    }

}
