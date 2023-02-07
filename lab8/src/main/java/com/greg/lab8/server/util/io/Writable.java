package com.greg.lab8.server.util.io;

public interface Writable {
    boolean write(String output);
    boolean error(String errMessage);
}
