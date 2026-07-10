package org.example.dao;

import org.example.dao.common.DbConnector;
import org.example.dao.common.IEntityRepository;
import org.example.entity.Contribution;
import org.example.entity.Creditor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContributionRepository implements IEntityRepository<Contribution> {
    @Override
    public List<Contribution> loadData() {
        List<Contribution> result = new ArrayList<>();
        try (Statement stmt = DbConnector.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM contribution order by contribution_code");
            while (rs.next()) {
                Integer contributionCode = rs.getInt("contribution_code");
                String contributionName = rs.getString("contribution_name");
                Integer months = rs.getInt("months");
                Double rate = rs.getDouble("rate");

                result.add(new Contribution(contributionCode, contributionName, months, rate));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        return result;
    }

    @Override
    public void saveEntity(Contribution e) {
        String strStmt = """
                insert into Contribution (contribution_name, months, rate)
                values (?, ?, ?)
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setString(1, e.getName());
            stmt.setInt(2, e.getMonths());
            stmt.setDouble(3, e.getRate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateEntity(Contribution e) {
        String strStmt = """
                update Contribution
                set contribution_name = ?, months = ?, rate = ?
                where contribution_code = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setString(1, e.getName());
            stmt.setInt(2, e.getMonths());
            stmt.setDouble(3,e.getRate());
            stmt.setInt(4, e.getPk());

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteEntity(Object pk) {
        String strStmt = """
                delete from Contribution where contribution_code = ?
                """;
        try (PreparedStatement stmt = DbConnector.getConnection().prepareStatement(strStmt)) {
            stmt.setInt(1, (Integer)pk);

            return stmt.executeUpdate() != 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public Object[] toRow(Contribution e) {
        return new Object[] {e.getPk(), e.getName(), e.getMonths(), e.getRate()};
    }

}
