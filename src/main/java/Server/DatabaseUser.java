package Server;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseUser {
    void addUser(String imie, String nazwisko, String email, String numerTelefonu
            , java.sql.Date dataUrodzenia, String haslo, java.sql.Date dataDolaczenia); //adds user to database
    void removeUser(int id); //removes user from database
    void removeEveryUser(); //removes every user
    boolean loginUser(String email, String password);
    boolean registerCheckMail(String email);
    String selectName(int id);
    List<String> selectProfileInfo(int id);
    void updateUser(int id,  Map<String, String> fieldsToUpdate);

}
