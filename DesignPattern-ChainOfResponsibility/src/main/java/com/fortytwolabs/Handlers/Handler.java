package com.fortytwolabs.Handlers;

public interface Handler {

    void setNextHandler(Handler nextHandler);
    void logMessage(String level, String message);

}
