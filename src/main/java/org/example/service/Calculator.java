package org.example.service;

import org.example.dao.ClientAccountRepository;
import org.example.dao.ContributionRepository;
import org.example.dao.common.DbConnector;
import org.example.dao.common.IEntityRepository;
import org.example.entity.Client;
import org.example.entity.ClientAccount;
import org.example.entity.Contribution;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final ClientAccountRepository accountRepository;

    public Calculator(ClientAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public record ContribState(LocalDate date, Double amount) {    }

    public List<ContribState> calculateIncome(Integer accountId){
        List<ContribState> list = new ArrayList<>();
        ClientAccount account = accountRepository.loadById(accountId); // ссылка на счет
        Contribution contrib = account.getContribution(); // вернуть вклад
        Float investAmount = account.getInvestedAmount();
        LocalDate openingDate = account.getOpeningLocalDate();
        Integer months = contrib.getMonths();
        Double rate = contrib.getRate();

        Double summa = (double) investAmount;
        for (int i = 0; i < months + 1; i++) {

            list.add(new ContribState(openingDate, summa));
            openingDate = openingDate.plusMonths(1);
            summa = summa + summa * (rate / 100) / 12;
            summa = Math.round(summa * 100.0) / 100.0;

        }

        return list;
    }



}
