package Server;

import Data.LicenseData;
import Data.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgreSQL {

    private static final Connection connection = PostgreSQLInitialization.getInstance().connection;


    public static boolean update(int id, String table, Map<String, Object> fieldsToUpdate) {

        StringBuilder query = new StringBuilder("UPDATE ");

        query.append(table).append(" SET ");

        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null ) {
                query.append(key).append(" = ?, ");
            }
        }

        if (!fieldsToUpdate.isEmpty()) {
            query.setLength(query.length() - 2); // Usunięcie ostatniej przecinki i spacji
            query.append(" WHERE id_uzytkownika = ?;");
        }

        String sql = query.toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;

            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {

                if(entry.getKey().startsWith("data_"))
                {
                    Date value = Date.valueOf(entry.getValue().toString());
                    preparedStatement.setDate(i,value);
                }
                else
                {
                    String value = entry.getValue().toString();
                    try {
                        Integer number = Integer.parseInt(value);
                        preparedStatement.setObject(i,number);
                    }
                    catch (Exception e)
                    {
                        preparedStatement.setObject(i,value);
                    }
                }
                i++;
            }
            preparedStatement.setInt(i, id);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd: " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(String field,int id) {

        StringBuilder query = new StringBuilder("DELETE FROM ");

        query.append(field).append(" WHERE id_uzytkownika= ").append(id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd SQL: " + e.getMessage());
            return false;
        }
    }

    public static int login(String email, String password) {
        String sql = "SELECT id_uzytkownika, haslo FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("haslo");

                if(Password.matchPassword(password, storedPassword))
                {
                    return resultSet.getInt("id_uzytkownika");
                }
                else
                {
                    return -1;
                }
            }
            else {
                System.out.println("Błąd logowania. Nieprawidłowe dane.");
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błędne dane logowania: " + e.getMessage());
            return -1;
        }
    }
}
