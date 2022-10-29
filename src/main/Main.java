package main;

import main.Server.KVServer;
import main.control.InterfaceManager.TaskManager;
import main.control.manager.Managers;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        TaskManager manager = Managers.getDefault();
    }
}
