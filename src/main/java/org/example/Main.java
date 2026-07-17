package org.example;

import org.example.dao.ClientRepository;
import org.example.dao.common.DbConnector;
import org.example.entity.Client;
import org.example.ui.GenericTableFrame;

public class Main {
    public static void main(String[] args) {
        ClientRepository ClientRepository = new ClientRepository();
        GenericTableFrame<Client> frame = new GenericTableFrame<>(
                "Client",
                Client.class,
                ClientRepository
        );
        frame.setVisible(true);
    }
}