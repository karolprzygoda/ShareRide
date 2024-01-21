package DataManagers;

import Data.PassengersData;
import Data.RatingData;
import Server.PostgreSQLInitialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RatingDataManager {

    private static final Logger logger = LogManager.getLogger(RatingDataManager.class);

    private static final PostgreSQLInitialization databaseConnection = PostgreSQLInitialization.getInstance();

    public static boolean insertRatings(List<RatingData> ratingDataList) {


        Connection connection = databaseConnection.startConnection();

        int i=0;

        String query = "INSERT INTO ratings (user_id, announcements_id, rater_id, rating) VALUES (?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (RatingData ratingData : ratingDataList) {


                        preparedStatement.setInt(1, ratingData.getUser_id());
                        preparedStatement.setInt(2, ratingData.getAnnouncement_id());
                        preparedStatement.setInt(3, ratingData.getRater_id());
                        preparedStatement.setInt(4, ratingData.getRating());

                        if (ratingData.getUser_id() != ratingData.getRater_id()) {
                            preparedStatement.executeUpdate();
                            logger.info("User: " + ratingData.getUser_id() + " got new rating from user: " + ratingData.getRater_id());
                            i++;
                        }




                }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Server caught an error: " + e.getCause());
            return false;
        }finally {
            databaseConnection.closeConnection();
        }
                if(i == 0)
                {
                    return false;
                }
                else {
                    return true;
                }

    }

}
