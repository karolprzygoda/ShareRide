package SharerideClient.Controllers;

import ChainOfResponsibility.Request;
import Data.DriverData;
import Data.LicenseData;
import Data.UserData;
import Data.VehicleData;
import SharerideClient.Alerts;

import java.io.*;

import static SharerideClient.Views.FormsContainer.*;

/**
 * Kontroler zarządzający połączeniem z serwerem
 * @author Karol Przygoda
 */
public class ServerController {


    /**
     * @author Karol Przygoda
     */

    static protected int sendLoginRequest(UserData userData) {
        try{

            Request request = new Request(Request.RequestType.LOGIN);
            request.setDataToManage(userData);
            out.writeObject(request);
            out.flush();
            out.reset();//wazne zapobiega cachowaniu obiektow

            boolean response = input.readBoolean();

            if(response)
            {
                return 1;
            }
            else
            {
                return 0;
            }


        }catch (IOException  | RuntimeException  e )
        {
            e.printStackTrace();
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }

    static protected <T> T sendSelectRequest(T dataToManage) {

        try{

            if(dataToManage instanceof UserData || dataToManage instanceof LicenseData || dataToManage instanceof VehicleData || dataToManage instanceof DriverData ) {

                Request request = new Request(Request.RequestType.SELECT);
                request.setDataToManage(dataToManage);
                out.writeObject(request);
                out.flush();
                out.reset();

                return (T) input.readObject();
            }
            else {
                throw new IllegalArgumentException("Nieprawidłowy typ danych");
            }


        }catch (IOException | ClassNotFoundException e )
        {
            e.printStackTrace();
            Alerts.failureAlert("Serwer napotkał problem");
            return null;
        }
    }

    static protected <T> int sendInsertRequest(T dataToManage) {

        try{

            if(dataToManage instanceof UserData || dataToManage instanceof LicenseData || dataToManage instanceof VehicleData || dataToManage instanceof DriverData) {

                Request request = new Request(Request.RequestType.INSERT);
                request.setDataToManage(dataToManage);
                out.writeObject(request);
                out.flush();
                out.reset();

                boolean response = input.readBoolean();

                if (response) {
                    return 1;
                } else {
                    return 0;
                }
            }else
            {
                throw new IllegalArgumentException("Nieprawidłowy typ danych");
            }

        }catch (IOException  | RuntimeException e)
        {
            e.printStackTrace();
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }

    static protected <T> int sendUpdateRequest(T dataToManage) {
        try{

            if(dataToManage instanceof UserData || dataToManage instanceof LicenseData || dataToManage instanceof VehicleData || dataToManage instanceof DriverData) {

                Request request = new Request(Request.RequestType.UPDATE);
                request.setDataToManage(dataToManage);
                out.writeObject(request);
                out.flush();
                out.reset();

                boolean response = input.readBoolean();
                if (response) {
                    return 1;
                } else {
                    return 0;
                }
            }
            else
            {
                throw new IllegalArgumentException("Nieprawidłowy typ danych");
            }

        }catch (IOException | RuntimeException e)
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }

    static protected <T> int  sendDeleteRequest(T dataToManage) {
        try {

            if(dataToManage instanceof UserData || dataToManage instanceof LicenseData || dataToManage instanceof VehicleData || dataToManage instanceof DriverData) {

                Request request = new Request(Request.RequestType.DELETE);
                request.setDataToManage(dataToManage);
                out.writeObject(request);
                out.flush();
                out.reset();

                boolean response = input.readBoolean();
                if (response) {
                    return 1;
                } else {
                    return 0;
                }
            }else
            {
                throw new IllegalArgumentException("Nieprawidłowy typ danych");
            }
        }catch (IOException | RuntimeException e)
        {
            Alerts.failureAlert("Serwer napotkał problem");
            return -1;
        }
    }
}