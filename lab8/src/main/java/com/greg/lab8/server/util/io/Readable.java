package com.greg.lab8.server.util.io;

import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.util.data.User;

import java.net.SocketAddress;
import java.util.Scanner;

public interface Readable {
    Organization readOrganisation();
    String read();
    Scanner getScanner();
    SocketAddress getCurrentClient();
    User readUser();
}
