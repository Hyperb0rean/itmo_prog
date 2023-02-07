package com.greg.lab8.client.util;

import com.greg.lab8.common.util.data.User;

public class UserHolder {
    private  static User user = null;

    private UserHolder(){

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserHolder.user = user;
    }
}
