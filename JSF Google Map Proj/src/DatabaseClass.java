import org.primefaces.model.UploadedFile;
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
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet t INNER JOIN toilet_info ti ON ti.toilet_id = t.id WHERE ti.removal_flags < 6;");
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
            PreparedStatement getToilets = conn.prepareStatement("SELECT * FROM toilet_request tr INNER JOIN toilet_request_info tri ON tri.toilet_request_id = tr.id WHERE tri.removal_flags < 6;");
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
//    public List<FolderData> getFolders() {
//        try {
//        List<FolderData> fdL = new ArrayList<>();
//        PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders");
//        ResultSet folderRS = getFolders.executeQuery();
//        while (folderRS.next()) {
//            FolderData fd = new FolderData(
//                    folderRS.getInt("id"),
//                    "Empty",
//                    folderRS.getString("folder_name")
//            );
//            fdL.add(fd);
//        }
//        return fdL;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public List<FolderData> getSponsorFolders() {
        try {
            List<FolderData> fdL = new ArrayList<>();
            PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders uf INNER JOIN users u ON uf.user_id = u.id WHERE uf.sponsor = 1");
            ResultSet folderRS = getFolders.executeQuery();
            while (folderRS.next()) {
                FolderData fd = new FolderData(
                        folderRS.getInt(1),
                        folderRS.getString("username"),
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
    public List<FolderData> getUserFolders() {
        try {
            List<FolderData> fdL = new ArrayList<>();
            PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders uf INNER JOIN users u ON uf.user_id = u.id WHERE uf.user_id = ?");
            UserController uc = UserController.getInstance();
            getFolders.setInt(1, uc.getUser_id());
            ResultSet folderRS = getFolders.executeQuery();
            while (folderRS.next()) {
                FolderData fd = new FolderData(
                        folderRS.getInt(1),
                        folderRS.getString("username"),
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
    public List<FolderData> getImportedFolders() {
        try {
            List<FolderData> fdL = new ArrayList<>();
            PreparedStatement getFolders = conn.prepareStatement("SELECT uf.*, u.username FROM user_folders uf INNER JOIN user_folders_import ufi ON ufi.folder_id = uf.id INNER JOIN users u ON uf.user_id = u.id WHERE ufi.user_id = ?;");
            UserController uc = UserController.getInstance();
            getFolders.setInt(1, uc.getUser_id());
            ResultSet folderRS = getFolders.executeQuery();
            while (folderRS.next()) {
                FolderData fd = new FolderData(
                        folderRS.getInt(1),
                        folderRS.getString("username"),
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
    public List<FolderData> getTrendingFolders() {
        try {
            List<FolderData> fdL = new ArrayList<>();
            PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders uc INNER JOIN users u ON uc.user_id = u.id WHERE import_amt > 0 ORDER BY uc.import_amt DESC,uc.id DESC;");
            ResultSet folderRS = getFolders.executeQuery();
            while (folderRS.next()) {
                FolderData fd = new FolderData(
                        folderRS.getInt(1),
                        folderRS.getString("username"),
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
    public List<FolderData> getSearchedFolder(String field) {
        try {
            List<FolderData> fdL = new ArrayList<>();
            PreparedStatement getFolders = conn.prepareStatement("SELECT * FROM user_folders uc INNER JOIN users u ON uc.user_id = u.id");
            ResultSet folderRS = getFolders.executeQuery();
            UserController uc = UserController.getInstance();
            while (folderRS.next()) {
                if (folderRS.getString("folder_name").contains(field)) {
                    FolderData fd = new FolderData(
                            folderRS.getInt(1),
                            folderRS.getString("username"),
                            folderRS.getString("folder_name")
                    );
                    fdL.add(fd);
                }
            }
            return fdL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean createFolder() {
        try {
            PersonalMapList pml = PersonalMapList.getInstance();
            FolderData fd = pml.getCurrentFolder();
            PreparedStatement createFolder = conn.prepareStatement("INSERT INTO user_folders" +
                    "(user_id,folder_name,window_type) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            UserController uc = UserController.getInstance();
            int userId = uc.getUser_id();
            if (userId == -1) {
                userId = -2;
            }
            createFolder.setInt(1, userId);
            createFolder.setString(2, fd.getFolderName());
            createFolder.setInt(3, fd.getWindowType());
            createFolder.executeUpdate();

            ResultSet setId = createFolder.getGeneratedKeys();
            int idRtn = 0;
            while (setId.next()) {
                fd.setFolderId( setId.getInt(1));
                idRtn = setId.getInt(1);
                System.out.println("Heyo" + idRtn);
            }
            fd.setFolderId(idRtn);
            fd.setIsEditable(1);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void getFolderMarkers(int folderId) {
        List<PersonalMapMarker> pmmList = new ArrayList<>();
        PersonalMapList pml = PersonalMapList.getInstance();
        try {
            UserController uc = UserController.getInstance();
            FolderData fd = pml.getCurrentFolder();
            PreparedStatement folderType = conn.prepareStatement("SELECT * FROM user_folders WHERE id = ?");
            folderType.setInt(1, folderId);
            ResultSet folType = folderType.executeQuery();
            int markerType= 0;
            while (folType.next()) {
                markerType = folType.getInt("window_type");
                int folder_user_id = folType.getInt("user_id");
                if (uc.getUser_id() == folder_user_id) {
                    System.out.println("Editable");
                    fd.setIsEditable(1);
                }
            }
            System.out.println("Folder:" + folderId + "MarkerType:" + markerType);
            fd.setWindowType(markerType);
            PreparedStatement getMarkers = conn.prepareStatement("SELECT * FROM user_folder_markers usm INNER JOIN user_folder_marker_info ufmi ON ufmi.marker_id = usm.id WHERE usm.folder_id =  ?;");

            getMarkers.setInt(1, folderId);
            ResultSet markers = getMarkers.executeQuery();
            while (markers.next()) {
                PersonalMapMarker pmm = new PersonalMapMarker();
                    LatLng ll = new LatLng(markers.getDouble("latitude"), markers.getDouble("longitude"));
                    System.out.println("Marker Lat:" + markers.getDouble("latitude") + "Longitude:" + markers.getDouble("longitude") );
                    pmm.setLatlng(ll);
                    pmm.setId(markers.getInt(1));
                    pmm.setField1(markers.getString("field1"));
                    pmm.setField2(markers.getString("field2"));
                    if(markerType == 2 || markerType == 3) {
                        pmm.setRating(markers.getInt("rating"));
                        pmm.setAmt_of_ratings(markers.getInt("amt_of_ratings"));
                        pmm.calAvg();
                    }
                List<PersonalMapReviews> pmRL = new ArrayList<>();
                PreparedStatement getReviews = conn.prepareStatement("SELECT * FROM user_folder_marker_review WHERE marker_id = ?");
                getReviews.setInt(1,markers.getInt(1));
                ResultSet reviews = getReviews.executeQuery();
                while (reviews.next()) {
                    PersonalMapReviews pmr = new PersonalMapReviews(reviews.getInt("rating"), reviews.getString("user_name"), reviews.getString("comment"));
                    pmRL.add(pmr);
                }
                pmm.setPmr(pmRL);
                pmmList.add(pmm);
            }

            fd.setWindowType(markerType);
            fd.setPmmL(pmmList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addMarker(PersonalMapMarker pmm) {
        try {
            PreparedStatement addMarker = conn.prepareStatement("INSERT INTO user_folder_markers (latitude, longitude, folder_id) VALUES (?,?,?)" , Statement.RETURN_GENERATED_KEYS);
            addMarker.setDouble(1, pmm.getLatlng().getLat());
            addMarker.setDouble(2, pmm.getLatlng().getLng());
            PersonalMapList pml = PersonalMapList.getInstance();
            FolderData fd = pml.getCurrentFolder();
            addMarker.setInt(3, fd.getFolderId());
            addMarker.executeUpdate();
            ResultSet markerId = addMarker.getGeneratedKeys();
            int newMarkerId = -1;
            while (markerId.next()) {
                newMarkerId = markerId.getInt(1);
            }
            PreparedStatement addMarkerInfo = conn.prepareStatement("INSERT INTO user_folder_marker_info (marker_id,field1,field2) VALUES (?,?,?)");
            addMarkerInfo.setInt(1 , newMarkerId);
            addMarkerInfo.setString(2, pmm.getField1());
            addMarkerInfo.setString(3, pmm.getField2());
            addMarkerInfo.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void editPersonalMarker(int id, String field1, String field2) {
        try {
            PreparedStatement editMarker = conn.prepareStatement("UPDATE user_folder_marker_info" +
                    " SET field1 = ?," +
                    " field2 = ?" +
                    "WHERE marker_id = ?;");
            editMarker.setString(1, field1);
            editMarker.setString(2, field2);
            editMarker.setInt(3, id);
            editMarker.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addImport(int folderId) {
        try {
            Boolean checkBool = true;
            UserController uc = UserController.getInstance();
            PreparedStatement checkFolder = conn.prepareStatement("SELECT * FROM user_folders_import WHERE user_id = ? AND folder_id = ?;");
            PreparedStatement checkFolder2 = conn.prepareStatement("SELECT * FROM user_folders WHERE user_id = ? AND id = ?;");
            checkFolder.setInt(1, uc.getUser_id());
            checkFolder.setInt(2, folderId);
            checkFolder2.setInt(1, uc.getUser_id());
            checkFolder2.setInt(2, folderId);
            ResultSet rsCheck1 = checkFolder.executeQuery();
            ResultSet rsCheck2 = checkFolder2.executeQuery();
            while (rsCheck1.next() || rsCheck2.next()) {
                checkBool = false;
            }
            if (checkBool == true) {
                PreparedStatement importFolder = conn.prepareStatement("INSERT INTO user_folders_import (user_id,folder_id) VALUES (?,?)");
                importFolder.setInt(1, uc.getUser_id());
                importFolder.setInt(2, folderId);
                importFolder.executeUpdate();
            }
            System.out.println(checkBool);
            return checkBool;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void addDelete(int folderId) {
        try {
            Boolean check = true;
            PreparedStatement checkOwned = conn.prepareStatement("SELECT * FROM user_folders WHERE user_id = ? AND id = ?");
            UserController uc = UserController.getInstance();
            checkOwned.setInt(1,uc.getUser_id());
            checkOwned.setInt(2, folderId);
            ResultSet checkOwner = checkOwned.executeQuery();
            if(!checkOwner.isBeforeFirst() ) {
                check = false;
            }
            if (check == true) {
//                User is owner
                PreparedStatement deleteFromImport = conn.prepareStatement("DELETE user_folders_import WHERE folder_id = ?;");
                PreparedStatement deleteFromFolders = conn.prepareStatement("DELETE FROM user_folders WHERE id = ?;");
                deleteFromImport.setInt(1, folderId);
                deleteFromFolders.setInt(1, folderId);
                deleteFromFolders.executeUpdate();
                deleteFromImport.executeUpdate();
            }else {
                PreparedStatement deleteFromImport = conn.prepareStatement("DELETE FROM user_folders_import WHERE folder_id = ? AND user_id = ?;");
                deleteFromImport.setInt(2,uc.getUser_id());
                deleteFromImport.setInt(1, folderId);
                deleteFromImport.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deletePersonalMarker(int id) {
        try {
            PreparedStatement deleteMarker = conn.prepareStatement("DELETE FROM user_folder_markers WHERE id = ?");
            PreparedStatement deleteMarkerInfo = conn.prepareStatement("DELETE FROM user_folder_marker_info WHERE marker_id = ?");
            deleteMarker.setInt(1, id);
            deleteMarkerInfo.setInt(1, id);
            deleteMarker.executeUpdate();
            deleteMarkerInfo.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void createReview(int markerId, int rating, String comments) {
        try {
            String userName = "Anonymous";
            PreparedStatement getUserName = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            UserController uc = UserController.getInstance();
            getUserName.setInt(1, uc.getUser_id());
            ResultSet name = getUserName.executeQuery();
            while (name.next()) {
                userName = name.getString("username");
            }
            PreparedStatement addReview = conn.prepareStatement( "INSERT INTO user_folder_marker_review (marker_id, rating, comment, user_name) VALUES (?,?,?,?)");
            addReview.setInt(1, markerId);
            addReview.setInt(2, rating);
            addReview.setString(3, comments);
            addReview.setString(4, userName);
            addReview.executeUpdate();
            PreparedStatement  addToInfo = conn.prepareStatement("UPDATE user_folder_marker_info SET rating = rating + ?, amt_of_ratings = amt_of_ratings + 1 WHERE marker_id = ?");
            addToInfo.setInt(1, rating);
            addToInfo.setInt(2, markerId);
            addToInfo.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
