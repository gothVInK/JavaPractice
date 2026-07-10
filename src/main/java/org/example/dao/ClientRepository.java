package org.example.dao;

import org.example.dao.common.DbConnector;
import org.example.dao.common.IEntityRepository;
import org.example.entity.Client;
import org.example.entity.Contribution;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements IEntityRepository<Client> {
    @Override
    public List<Client> loadData() {
        List<Client> result = new ArrayList<>();
        try (Statement stmt = DbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM client order by client_code");
            while (rs.next()) {
                Integer clientCode = rs.getInt("client_code");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String passportNumber = rs.getString("passport_number");
                String clientAdress = rs.getString("client_adress");
                String phoneNumber = rs.getString("phone_number");

                result.add(new Client(clientCode, firstName, middleName, lastName, passportNumber, clientAdress, phoneNumber));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        return result;
    }

    @Override
    public void saveEntity(Client e) {
        String strStmt = """
                insert into client (first_name, middle_name, last_name, passport_number, client_adress, phone_number)
                values (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getMiddleName());
            stmt.setString(3, e.getLastName());
            stmt.setString(4, e.getPassportNumber());
            stmt.setString(5, e.getClientAdress());
            stmt.setString(6, e.getPhoneNumber());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateEntity(Client e) {
        String strStmt = """
                update client
                set first_name = ?, middle_name = ?, last_name = ?, passport_number = ?, client_adress = ?, phone_number = ?
                where client_code = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setString(1, e.getFirstName());
            stmt.setString(2, e.getMiddleName());
            stmt.setString(3,e.getLastName());
            stmt.setString(4,e.getPassportNumber());
            stmt.setString(5,e.getClientAdress());
            stmt.setString(6,e.getPhoneNumber());
            stmt.setInt(7, e.getPk());

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteEntity(Object pk) {
        String strStmt = """
                delete from client where client_code = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, (Integer)pk);

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public Object[] toRow(Client e) {
        return new Object[] {e.getPk(), e.getFirstName(), e.getMiddleName(), e.getLastName(), e.getPassportNumber(), e.getClientAdress(), e.getPhoneNumber()};
    }
}
