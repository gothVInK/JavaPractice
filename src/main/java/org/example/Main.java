package org.example;

import org.example.dao.ContributionRepository;
import org.example.dao.CreditorRepostitory;
import org.example.dao.common.DbConnector;
import org.example.entity.Contribution;
import org.example.entity.Creditor;
import org.example.ui.GenericTableFrame;

public class Main {
    ///
    public static void main(String[] args) {
        DbConnector connector = new DbConnector();

        ContributionRepository ContributionRepository = new ContributionRepository();
        GenericTableFrame<Contribution> frame = new GenericTableFrame<>(
                "Contribution",
                Contribution.class,
                ContributionRepository
        );
        frame.setVisible(true);
    }
}