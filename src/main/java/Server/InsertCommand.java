package Server;

import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InsertCommand implements Command{
    public static  void execute(ObjectInputStream input, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String field = (String) input.readObject();
        Object object = input.readObject();
        boolean response;
        PasswordEncoder passwordEncoder = PasswordEncoderFactory.createPasswordEncoder();



        switch (field) {
            case "USER" -> {
                if (Verification.verify((UserData) object)) {
                    String encodePassword = passwordEncoder.encodePassword(((UserData) object).getPassword());
                    ((UserData) object).setPassword(encodePassword);
                    response = InsertHandler.insertUser((UserData) object);
                } else {
                    response = false;
                }
            }
            case "CAR" -> response = InsertHandler.insertVehicle((VehicleData) object);
            case "LICENSE" -> response = InsertHandler.insertLicense((LicenseData) object);
            case "DRIVER" -> response = InsertHandler.insertDriver((DriverData) object);
            default -> throw new IllegalStateException("Unexpected value: " + field);
        }
        out.writeBoolean(response);
        out.flush();
    }
}

