package com.fortytwolabs.Handlers;

public class AbstractLogger implements Handler{

    protected Handler nextHandler;

    @Override
    public void setNextHandler(Handler nextHandler) {

    }

    @Override
    public void logMessage(String level, String message) {

    }
}
