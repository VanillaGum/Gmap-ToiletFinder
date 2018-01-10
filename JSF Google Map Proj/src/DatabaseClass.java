import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseClass {
    String dburl = "jdbc:mysql://localhost:3306/toilet_finder";
    private Connection conn;
    public DatabaseClass() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dburl, "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void suggestToiletLoc(MarkerData m) {
        try {
            PreparedStatement addToiletSuggestion = conn.prepareStatement("INSERT INTO toilet_request" +
                    "(latitude,longitude,approval,rating,genderM) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            addToiletSuggestion.setDouble(1, m.getLatlng().getLat());
            addToiletSuggestion.setDouble(2, m.getLatlng().getLng());

            UserController uc = new UserController();
            int userLevel = uc.getUserLevel();
            switch(userLevel) {
                case 0:
                    addToiletSuggestion.setInt(3,1);
                case 1:
                    addToiletSuggestion.setInt(3,2);
                case 2:
                    addToiletSuggestion.setInt(3,6);
            }

            if (m.getRating() != -1) {
                addToiletSuggestion.setInt(4, m.getRating());
            }else {
                addToiletSuggestion.setInt(4, 0);
            }
            addToiletSuggestion.setInt(5,m.getGenderM());
            int checkExecuted = addToiletSuggestion.executeUpdate();
            if (checkExecuted != 0) {
                ResultSet toiletRequestInfo = addToiletSuggestion.getGeneratedKeys();
                approvalCheck(toiletRequestInfo);
            }
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }
    public void approvalCheck(ResultSet toiletRequestId) {
        try {
            while (toiletRequestId.next()) {
                PreparedStatement getToiletApproval = conn.prepareStatement("SELECT approval FROM toilet_request WHERE id = ?;");
                getToiletApproval.setInt(1,toiletRequestId.getInt(1));
                ResultSet toiletApproval= getToiletApproval.executeQuery();
                while (toiletApproval.next()) {
                    int approval = toiletApproval.getInt("approval");
                    if (approval >= 6) {
                        createToilet(toiletRequestId.getInt(1));
                    }
                }
            }
        }catch(SQLException se) {
                se.printStackTrace();
            }
    }
    public void upvoteToilet(int toiletId, int approvalAmt) {
        try {
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_request " +
                    "Set approval = approval + ?");
            upvoteToilet.setInt(1,approvalAmt);
            upvoteToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void flagToilet(int toiletId, int removalValue) {
        try {
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_request " +
                    "Set removal_flag = removal_flag + ?");
            upvoteToilet.setInt(1,removalValue);
            upvoteToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createToilet(int approvedToiletId) {
        //If Approval Of Toilet High Enough
        //Call This Method
        try {
                //Get Info Of Approved Toilet
                PreparedStatement getApprovedToilet = conn.prepareStatement("SELECT * FROM toilet_request WHERE id=?;");
                getApprovedToilet.setInt(1,approvedToiletId);
                ResultSet approvedToilet = getApprovedToilet.executeQuery();

                //Write
                PreparedStatement createToilet = conn.prepareStatement("INSERT INTO toilet (latitude,longitude) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement createToiletInfo = conn.prepareStatement("INSERT INTO toilet_info (toilet_id,rating, amt_of_rating, genderM) VALUES (?,?,?,?)");

                //Delete Toilet That Has Been Approved
                PreparedStatement deleteToiletApproved = conn.prepareStatement("DELETE FROM toilet_request WHERE id=?;");

                while(approvedToilet.next()) {

                    createToilet.setDouble(1, approvedToilet.getDouble("latitude"));
                    createToilet.setDouble(2, approvedToilet.getDouble("longitude"));
                    int affectedRows = createToilet.executeUpdate();
                    ResultSet getToiletId = null;
                    int toiletId;
                    if (affectedRows == 0) {
                        //Failed Insert
                    }else {
                        try {
                            //Get Id Of Toilet to create Toilet Info Table On
                            getToiletId = createToilet.getGeneratedKeys();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        if (getToiletId.next()) {
                            //Creating toilet Successful
                            createToiletInfo.setInt(1, getToiletId.getInt(1));
                            createToiletInfo.setInt(2, approvedToilet.getInt("rating"));
                            createToiletInfo.setInt(3, approvedToilet.getInt("amt_of_rating"));
                            createToiletInfo.setBoolean(4, approvedToilet.getBoolean("genderM"));
                            createToiletInfo.executeUpdate();

                            //Delete Approved Toilet
                            deleteToiletApproved.setInt(1,approvedToiletId);
                            deleteToiletApproved.executeUpdate();
                        }
                    }
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void InsertValue(double lat, double lng){
        try {
            PreparedStatement ps = conn.prepareStatement("Insert INTO toilet " +
                    "(name,latitude,longitude) VALUES (?,?,?)");
            ps.setString(1,"Male Test Toilet");
            ps.setDouble(2,lat);
            ps.setDouble(3,lng);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<List> DisplayMarker() {
        try {
            List<List> toiletMarkerList = new ArrayList<>();
            List<MarkerData> mList= new ArrayList<>();
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet t INNER JOIN toilet_info ti ON ti.toilet_id = t.id;");
            PreparedStatement getToiletSuggested = conn.prepareStatement("SELECT * FROm toilet_request;");
            ResultSet toilets = getToilets.executeQuery();
            while(toilets.next()) {
                //Toilet Table
                int toiletId = toilets.getInt(1);
                String name = toilets.getString("name");
                double latitude = toilets.getDouble("latitude");
                double longitude = toilets.getDouble("Longitude");

                //ToiletInfo Table
                int toiletInfoId = toilets.getInt(5);
                int rating = toilets.getInt("rating");
                int amt_of_rating = toilets.getInt("amt_of_rating");
                int genderM = toilets.getInt("genderM");

                MarkerData m = new MarkerData(new LatLng(latitude,longitude),rating,amt_of_rating, name, genderM, toiletId, toiletInfoId);
                mList.add(m);
            }
            toiletMarkerList.add(mList);
            return toiletMarkerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    }
