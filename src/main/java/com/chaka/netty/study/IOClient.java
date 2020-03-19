package com.chaka.netty.study;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class IOClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1",8080);
        while (true){
            socket.getOutputStream().write((new Date() + ": hello world!").getBytes());
            TimeUnit.SECONDS.sleep(2);
        }

    }
}
