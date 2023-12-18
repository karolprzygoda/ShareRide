package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQL {

    private final Connection connection;

    public PostgreSQL() {
        this.connection = PostgreSQLInitialization.getInstance().connection;
    }


    public List<String> select(int id, String table, List<String> keys) {
        List<String> resultList = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT ");

        // Tworzenie części SELECT z określonymi kolumnami
        for (int i = 0; i < keys.size(); i++) {
            query.append(keys.get(i));
            if (i != keys.size() - 1) {
                query.append(", ");
            }
        }

        query.append(" FROM ").append(table).append(" WHERE id = ?;");
        String sql = query.toString();
        System.out.println(sql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    for (String key : keys) {
                        resultList.add(resultSet.getString(key));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
        }

        return resultList;
    }




}
