package com.fortytwolabs;

public class Main {

    public static void main(String[] args){
        SupportHandler level1Handler = new Level1Supporter();
        SupportHandler level2Handler = new Level2Supporter();
        SupportHandler level3Handler = new Level3Supporter();

        level1Handler.setNextHandler(level2Handler);
        level2Handler.setNextHandler(level3Handler);

        Request request1 = new Request(Priority.BASIC);
        Request request2 = new Request(Priority.INTERMEDIATE);
        Request request3 = new Request(Priority.CRITICAL);

        level1Handler.handleRequest(request1);
        level2Handler.handleRequest(request2);
        level3Handler.handleRequest(request3);

    }

}
