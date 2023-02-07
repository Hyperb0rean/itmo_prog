package com.greg.lab8.server.util.io;

public class UserOutput implements Writable{



    @Override
    public boolean write(String output) {
        System.out.println(output);
        return true;
    }

    @Override
    public boolean error(String errMessage) {
        System.err.println(errMessage);
        return true;
    }
}
