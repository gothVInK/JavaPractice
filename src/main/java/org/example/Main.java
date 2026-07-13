package org.example;

import org.example.dao.ClientAccountRepository;
import org.example.dao.ClientRepository;
import org.example.dao.ContributionRepository;
import org.example.dao.CreditorRepostitory;
import org.example.dao.common.DbConnector;
import org.example.entity.Client;
import org.example.entity.ClientAccount;
import org.example.entity.Contribution;
import org.example.entity.Creditor;
import org.example.ui.GenericTableFrame;

public class Main {
    ///
    public static void main(String[] args) {
        DbConnector connector = new DbConnector();

        ClientRepository ClientRepository = new ClientRepository();
        GenericTableFrame<Client> frame = new GenericTableFrame<>(
                "Client",
                Client.class,
                ClientRepository
        );
        frame.setVisible(true);
    }
}

// dispose закрывается