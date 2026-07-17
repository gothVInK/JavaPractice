package org.example.entity;

import org.example.entity.common.Column;
import org.example.entity.common.IEntity;
import org.example.entity.common.NonEditColumn;
import org.example.entity.common.SequenceColumn;

import java.time.LocalDate;
import java.util.Map;
import java.sql.Date;

public class ClientAccount implements IEntity {
    @Column(title = "Account Number")
    @SequenceColumn
    @NonEditColumn
    private Integer account_number;

    @Column(title = "Client Code") private Integer client_code;
    @Column(title = "Contribution Code") private Integer contribution_code;
    @Column(title = "Opening Date") private LocalDate opening_date;
    @Column(title = "Closing Date") private LocalDate closing_date;
    @Column(title = "Invested Amount") private Float invested_amount;

    Contribution contribution;
    Client client;


    public ClientAccount(Integer accountNumber, Integer clientCode, Integer contributionCode, LocalDate openingDate, LocalDate closingDate, Float investedAmount) {
        this.account_number = accountNumber;
        this.client_code = clientCode;
        this.contribution_code = contributionCode;
        this.opening_date = openingDate;
        this.closing_date = closingDate;
        this.invested_amount = investedAmount;
    }

    public ClientAccount(Integer accountNumber, LocalDate openingDate, LocalDate closingDate, Float investedAmount, Contribution contribution, Client client) {
        this(accountNumber, client.getPk(), contribution.getPk(), openingDate, closingDate, investedAmount);
        this.contribution = contribution;
        this.client = client;
    }

    public ClientAccount(Map<String, String> columns) {
        this(
                columns.get("account_number") != null ? Integer.valueOf(columns.get("account_number")) : null,
                Integer.valueOf(columns.get("client_code")),
                Integer.valueOf(columns.get("contribution_code")),
                columns.get("opening_date") != null ? LocalDate.parse(columns.get("opening_date")) : null,
                columns.get("closing_date") != null ? LocalDate.parse(columns.get("closing_date")) : null,
                Float.valueOf(columns.get("invested_amount"))
        );
    }

    @Override
    public Integer getPk() {
        return account_number;
    }
    public Integer getClientCode() {return client_code;}
    public Integer getContributionCode() {
        return contribution_code;
    }
    public LocalDate getOpeningLocalDate() {
        return opening_date;
    }
    public Date getOpeningDate(){
        return opening_date != null ? Date.valueOf(opening_date) : null;
    }
    public LocalDate getClosingLocalDate() {
        return closing_date;
    }
    public Date getClosingDate (){
        return closing_date != null ? Date.valueOf(closing_date) : null;
    }
    public Float getInvestedAmount() {return invested_amount;}

    public Contribution getContribution(){
        return contribution;
    }
    public Client getClient(){
        return client;
    }
}
