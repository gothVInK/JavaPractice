package org.example.dao;

import org.example.dao.common.DbConnector;
import org.example.dao.common.IEntityRepository;
import org.example.entity.Client;
import org.example.entity.ClientAccount;
import org.example.entity.Contribution;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientAccountRepository implements IEntityRepository<ClientAccount> {
    public ClientAccount loadById(Integer id) {
        //PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)
        //return stmt.executeUpdate() != 0;
        String strStmt = """
            select 
                *
            from 
                client_account 
                left join Contribution 
                    on client_account.contribution_code = Contribution.contribution_code
                left join Client 
                    on client_account.client_code = Client.client_code 
            where 
                client_account.account_number = ?
        """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer accountNumber = rs.getInt("account_number");
                Integer clientCode = rs.getInt("client_code");
                Integer contributionCode = rs.getInt("contribution_code");
                Date openingDate = rs.getDate("opening_date");
                LocalDate openingLocaleDate = openingDate != null ? openingDate.toLocalDate() : null;
                Date closingDate = rs.getDate("closing_date");
                LocalDate closingLocalDate = closingDate != null ? closingDate.toLocalDate() : null;
                Float investedAmount = rs.getFloat("invested_amount");

                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String passportNumber = rs.getString("passport_number");
                String clientAdress = rs.getString("client_adress");
                String phoneNumber = rs.getString("phone_number");

                String contributionName = rs.getString("contribution_name");
                Integer months = rs.getInt("months");
                Double rate = rs.getDouble("rate");

                Client client = new Client (clientCode, firstName, middleName, lastName, passportNumber, clientAdress, phoneNumber);
                Contribution contrib = new Contribution (contributionCode, contributionName, months, rate);

                return new ClientAccount(accountNumber, openingLocaleDate, closingLocalDate, investedAmount, contrib, client);
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

    }

    @Override
    public List<ClientAccount> loadData() {
        List<ClientAccount> result = new ArrayList<>();
        try (Statement stmt = DbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM client_account order by account_number");
            while (rs.next()) {
                Integer accountNumber = rs.getInt("account_number");
                Integer clientCode = rs.getInt("client_code");
                Integer contributionCode = rs.getInt("contribution_code");
                Date openingDate = rs.getDate("opening_date");
                LocalDate openingLocaleDate = openingDate != null ? openingDate.toLocalDate() : null;

                Date closingDate = rs.getDate("closing_date");
                LocalDate closingLocalDate = closingDate != null ? closingDate.toLocalDate() : null;

                Float investedAmount = rs.getFloat("invested_amount");

                result.add(new ClientAccount(accountNumber, clientCode, contributionCode, openingLocaleDate, closingLocalDate, investedAmount));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        return result;
    }

    @Override
    public void saveEntity(ClientAccount e) {
        String strStmt = """
                insert into client_account (client_code, contribution_code, opening_date, closing_date, invested_amount)
                values (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, e.getClientCode());
            stmt.setInt(2, e.getContributionCode());
            stmt.setDate(3, e.getOpeningDate());
            stmt.setDate(4, e.getClosingDate());
            stmt.setFloat(5, e.getInvestedAmount());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateEntity(ClientAccount e) {
        String strStmt = """
                update client_account
                set client_code = ?, contribution_code = ?, opening_date = ?, closing_date = ?, invested_amount = ?
                where account_number = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, e.getClientCode());
            stmt.setInt(2, e.getContributionCode());
            stmt.setDate(3, e.getOpeningDate());
            stmt.setDate(4, e.getClosingDate());
            stmt.setFloat(5,e.getInvestedAmount());
            stmt.setInt(6, e.getPk());

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteEntity(Object pk) {
        String strStmt = """
                delete from client_account where account_number = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, (Integer)pk);

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteEntityByName(String name) {
        return false;
    }
}
