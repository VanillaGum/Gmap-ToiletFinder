import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* TO DO LIST
   Add Method To Accept Grouping Of Toilet/ (Male And Female Toilets Suggestions)
   Add New Class That Extends MarkerData, MarkerDataGroup, MarkerDataGroupRequest
 */


public class DatabaseClass {
    String dburl = "jdbc:mysql://localhost:3306/map_proj";
    private Connection conn;
    int userLevel;
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
        UserController uc = UserController.getInstance();
        userLevel = uc.getUserLevel();
    }



//    Toilet Section
    public int suggestToiletLoc(MarkerData m) {
        try {
            System.out.println("Adding Toilet");
            PreparedStatement addToiletSuggestion = conn.prepareStatement("INSERT INTO toilet_request" +
                    "(latitude,longitude) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement addToiletSuggestionInfo = conn.prepareStatement("INSERT INTO toilet_request_info" +
                    "(toilet_request_id,genderM,wheelchair,cost) VALUES (?,?,?,?)");
            addToiletSuggestion.setDouble(1, m.getLatlng().getLat());
            addToiletSuggestion.setDouble(2, m.getLatlng().getLng());
            addToiletSuggestion.executeUpdate();

            ResultSet toiletSuggestionId = addToiletSuggestion.getGeneratedKeys();
            int suggestedToiletId = 0;
            while (toiletSuggestionId.next()) {
                //Get New Suggested Toilet Id
                suggestedToiletId = toiletSuggestionId.getInt(1);
            }

            //Add New Suggested Toilet Id
            addToiletSuggestionInfo.setInt(1, suggestedToiletId);

            //Get Gender
            addToiletSuggestionInfo.setInt(2,m.getGenderM());
            //Get Wheelchair Accessible
            addToiletSuggestionInfo.setInt(3, m.getWheelchair());
            //Get Cost
            addToiletSuggestionInfo.setDouble(4, m.getCost());

            addToiletSuggestionInfo.executeUpdate();

            upvoteToilet(suggestedToiletId);

            return suggestedToiletId;
        }catch(SQLException se) {
            se.printStackTrace();
        }
        return 0;
    }
    public int createToiletLoc(MarkerData m) {
        try {
            System.out.println("Adding Toilet");
            PreparedStatement addToiletSuggestion = conn.prepareStatement("INSERT INTO toilet" +
                    "(latitude,longitude) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement addToiletSuggestionInfo = conn.prepareStatement("INSERT INTO toilet_info" +
                    "(toilet_id,genderM,wheelchair,cost) VALUES (?,?,?,?)");
            addToiletSuggestion.setDouble(1, m.getLatlng().getLat());
            addToiletSuggestion.setDouble(2, m.getLatlng().getLng());
            addToiletSuggestion.executeUpdate();

            ResultSet toiletSuggestionId = addToiletSuggestion.getGeneratedKeys();
            int suggestedToiletId = 0;
            while (toiletSuggestionId.next()) {
                //Get New Suggested Toilet Id
                suggestedToiletId = toiletSuggestionId.getInt(1);
            }

            //Add New Suggested Toilet Id
            addToiletSuggestionInfo.setInt(1, suggestedToiletId);

            //Get Gender
            addToiletSuggestionInfo.setInt(2,m.getGenderM());
            //Get Wheelchair Accessible
            addToiletSuggestionInfo.setInt(3, m.getWheelchair());
            //Get Cost
            addToiletSuggestionInfo.setDouble(4, m.getCost());

            addToiletSuggestionInfo.executeUpdate();

            upvoteToilet(suggestedToiletId);

            return suggestedToiletId;
        }catch(SQLException se) {
            se.printStackTrace();
        }
        return 0;
    }
    //Check If Toilet Approval Rating Equal Or Greater Than 6
    public void approvalCheck(int toiletRequestId) {
        try {
            PreparedStatement getToiletApproval = conn.prepareStatement("SELECT approval FROM toilet_request_info WHERE toilet_request_id = ?;");
            getToiletApproval.setInt(1,toiletRequestId);
            ResultSet toiletApproval= getToiletApproval.executeQuery();
            while (toiletApproval.next()) {
                int approval = toiletApproval.getInt("approval");

                if (approval >= 6) {
                    //Toilet Approved
                    createToilet(toiletRequestId);
                }
            }
        }catch(SQLException se) {
                se.printStackTrace();
            }
    }

    public int userApprovalAmt() {
        switch(userLevel) {
            case 0:
                //Anonymous User
                return 1;
            case 1:
                //Logged In User
                return 2;
            case 2:
                //Trusted/ Admin User
                return 6;
        }
        return 0;
    }

    //Method call for when a User upvotes the toilet
    public void upvoteToilet(int toiletId) {
        try {
            System.out.println("Upvoting In Database");
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_request_info " +
                    "Set approval = approval + ? " +
                    "WHERE toilet_request_id = ?;");
            upvoteToilet.setInt(1,userApprovalAmt());
            upvoteToilet.setInt(2, toiletId);
            upvoteToilet.executeUpdate();
            approvalCheck(toiletId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Method call for when a User un-upvotes/Downvote the toilet
    public void downvoteToilet(int toiletId) {
        try {
            System.out.println("Downvoting In Database");
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_request_info " +
                    "Set approval = approval - ? " +
                    "WHERE toilet_request_id = ?;");
            upvoteToilet.setInt(1,userApprovalAmt());
            upvoteToilet.setInt(2, toiletId);
            upvoteToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Method call when rating toilets
    public void rateToiletRequest(int toiletId,int rating) {
        try {
            PreparedStatement rateToilet = conn.prepareStatement(" UPDATE toilet_request_info " +
                    "Set amt_of_rating = amt_of_rating + 1," +
                    "rating = rating + ? " +
                    "WHERE toilet_request_id = ?;");
            rateToilet.setInt(1,rating);
            rateToilet.setInt(2,toiletId);
            rateToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rateToiletApproved(int toiletId,int rating) {
        try {
            PreparedStatement rateToilet = conn.prepareStatement("UPDATE toilet_info " +
                    "Set amt_of_rating = amt_of_rating + 1," +
                    "rating = rating + ? " +
                    "WHERE toilet_id = ?;");
            rateToilet.setInt(1,rating);
            rateToilet.setInt(2,toiletId);
            rateToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //When user Flags toilet for removal
    public void flagSuggestionToilet(int toiletId) {
        try {
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_request_info " +
                    "Set removal_flags = removal_flags + ? " +
                    "WHERE toilet_request_id = ? ;");
            upvoteToilet.setInt(1,userApprovalAmt());
            upvoteToilet.setInt(2,toiletId);
            upvoteToilet.executeUpdate();
            //Changed/ We dont delete from database just dont show only
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void flagApprovedToilet(int toiletId) {
        try {
            PreparedStatement upvoteToilet = conn.prepareStatement("UPDATE toilet_info " +
                    "Set removal_flags = removal_flags + ? " +
                    "WHERE toilet_id = ?");
            System.out.println(toiletId);
            //Removal Amount Will Follow Approval Amount(6 removal_flags = removed)
            upvoteToilet.setInt(1,userApprovalAmt());
            upvoteToilet.setInt(2, toiletId);
            upvoteToilet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Toilet Approval Greater Or Equal To 6
    public void createToilet(int approvedToiletId) {
        try {
                //Get Info Of Approved Toilet
                PreparedStatement getApprovedToilet = conn.prepareStatement("SELECT * FROM toilet_request tr INNER JOIN toilet_request_info tri ON tri.toilet_request_id = tr.id WHERE tr.id=?;");
                getApprovedToilet.setInt(1,approvedToiletId);
                ResultSet approvedToilet = getApprovedToilet.executeQuery();

                //Write
                PreparedStatement createToilet = conn.prepareStatement("INSERT INTO toilet (latitude,longitude) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                PreparedStatement createToiletInfo = conn.prepareStatement("INSERT INTO toilet_info (toilet_id,rating, amt_of_rating, genderM,wheelchair,cost) VALUES (?,?,?,?,?,?)");

                //Delete Toilet That Has Been Approved
                PreparedStatement deleteToiletApproved = conn.prepareStatement("DELETE FROM toilet_request WHERE id=?;");
                PreparedStatement deleteToiletApprovedInfo = conn.prepareStatement( "DELETE FROM toilet_request_info WHERE toilet_request_id=?;");

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
                            createToiletInfo.setInt(4, approvedToilet.getInt("genderM"));
                            createToiletInfo.setInt(5, approvedToilet.getInt("wheelchair"));
                            createToiletInfo.setDouble(6, approvedToilet.getDouble("cost"));
                            createToiletInfo.executeUpdate();

                            //Delete Approved Toilet
                            deleteToiletApproved.setInt(1,approvedToiletId);
                            deleteToiletApprovedInfo.setInt(1,approvedToiletId);
                            deleteToiletApproved.executeUpdate();
                            deleteToiletApprovedInfo.executeUpdate();
                        }
                    }
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get Approved Toilet Markers
    public List<MarkerData> getApprovedToiletMarkers() {
        try {
            List<MarkerData> mList= new ArrayList<>();
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet t INNER JOIN toilet_info ti ON ti.toilet_id = t.id; WHERE removal_flags < 6;");
            ResultSet toilets = getToilets.executeQuery();
            while(toilets.next()) {
                //Toilet Table
                int toiletId = toilets.getInt(1);
                double latitude = toilets.getDouble("latitude");
                double longitude = toilets.getDouble("Longitude");

                //ToiletInfo Table
                int toiletInfoId = toilets.getInt(5);
                int rating = toilets.getInt("rating");
                int amt_of_rating = toilets.getInt("amt_of_rating");
                int genderM = toilets.getInt("genderM");
                int wheelchair = toilets.getInt("wheelchair");
                double cost = toilets.getDouble("cost");
                MarkerData m = new MarkerData(new LatLng(latitude,longitude),rating,amt_of_rating, genderM, toiletId, toiletInfoId,wheelchair,cost);
                mList.add(m);
            }
            return mList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<MarkerData> getRequestedToiletMarkers() {
        try {
            List<MarkerData> mList= new ArrayList<>();
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet_request tr INNER JOIN toilet_request_info tri ON tri.toilet_request_id = tr.id WHERE removal_flags < 6;");
            ResultSet toilets = getToilets.executeQuery();
            while(toilets.next()) {
                //Toilet Request Table
                int toiletId = toilets.getInt(1);
                double latitude = toilets.getDouble("latitude");
                double longitude = toilets.getDouble("Longitude");

                //ToiletInfo Table
                int toiletInfoId = toilets.getInt(5);
                int genderM = toilets.getInt("genderM");
                int ratings = toilets.getInt("rating");
                int amt_ratings = toilets.getInt("amt_of_rating");
                int wheelchair = toilets.getInt("wheelchair");
                double cost = toilets.getDouble("cost");
                MarkerRequestData m = new MarkerRequestData(new LatLng(latitude,longitude), ratings, amt_ratings, genderM, toiletId, toiletInfoId, wheelchair, cost);
                mList.add(m);
            }
            return mList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


//    Personal Map Section
    public List<FolderData> getFolders() {
        try {
        List<FolderData> fdL = new ArrayList<>();
        PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders");
        ResultSet folderRS = getFolders.executeQuery();
        while (folderRS.next()) {
            FolderData fd = new FolderData(
                    folderRS.getInt("id"),
                    "Empty",
                    folderRS.getString("folder_name")
            );
            fdL.add(fd);
        }
        return fdL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
