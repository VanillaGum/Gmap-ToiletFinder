import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDatabaseClass {
    String dburl = "jdbc:mysql://localhost:3306/toilet_finder";
    private Connection conn;
    public TestDatabaseClass() {
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
            addToiletSuggestion.setBoolean(5,m.isGenderM());
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
                PreparedStatement getApprovedToilet = conn.prepareStatement("SELECT * FROM toilet_request WHERE id=?;");
                getApprovedToilet.setInt(1,approvedToiletId);
                ResultSet approvedToilet = getApprovedToilet.executeQuery();

                PreparedStatement createToilet = conn.prepareStatement("INSERT INTO toilet (latitude,longitude) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement createToiletInfo = conn.prepareStatement("INSERT INTO toilet_info (toilet_id,rating, amt_of_rating, genderM) VALUES (?,?,?,?)");

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
                            getToiletId = createToilet.getGeneratedKeys();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        if (getToiletId.next()) {
                            createToiletInfo.setInt(1, getToiletId.getInt(1));
                        }
                        createToiletInfo.setInt(2, approvedToilet.getInt("rating"));
                        createToiletInfo.setInt(3, approvedToilet.getInt("amt_of_rating"));
                        createToiletInfo.setBoolean(4, approvedToilet.getBoolean("genderM"));
                        createToiletInfo.executeUpdate();
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
    public List<Marker> DisplayMarker() {
        try {
            List<Marker> mList= new ArrayList<>();
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet");
            PreparedStatement getToiletInfo = conn.prepareStatement( "SELECT * FROM toilet_info WHERE toilet_id= ?");
            ResultSet rs = getToilets.executeQuery();
            while(rs.next()) {
                getToiletInfo.setInt( 1 , rs.getInt("id"));
                ResultSet rs2 = getToiletInfo.executeQuery();

                String name = rs.getString("name");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("Longitude");
                Marker m = new Marker(new LatLng(latitude,longitude),name);
                mList.add(m);
            }
            return mList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    }
