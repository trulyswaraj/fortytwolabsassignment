package com.fortytwolabs;

class Level1Supporter implements SupportHandler{

    private SupportHandler nextHandler;

    @Override
    public void handleRequest(Request request) {
        if(request.getPriority() == Priority.BASIC){
            System.out.println("Level 1 Support Handled the request.");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(SupportHandler supportHandler) {
        this.nextHandler=nextHandler;
    }
}

class Level2Supporter implements SupportHandler{
    private SupportHandler nextHandler;


    @Override
    public void handleRequest(Request request) {
        if(request.getPriority() == Priority.INTERMEDIATE){
            System.out.println("Level 2 Support handled the request.");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(SupportHandler supportHandler) {
        this.nextHandler=nextHandler;
    }
}

class Level3Supporter implements SupportHandler{

    @Override
    public void handleRequest(Request request) {
        if(request.getPriority() == Priority.CRITICAL){
            System.out.println("Level 3 Support handled the request.");
        } else{
            System.out.println("Request Cannot be handled.");
        }
    }

    @Override
    public void setNextHandler(SupportHandler supportHandler) {

    }
}