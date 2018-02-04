import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.LatLng;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@ManagedBean
public class PersonalMapController {
    private PersonalMapEntity pme;
    private PersonalMapList pml;
    public int folderId =0;
    public int uniqueNoJs = 0;
    public String folderUser ="";
    public String folderName = "";
    public int folderType = -1;
    public String field1 = "";
    public String field2 = "";
    public Double lat = 0.0;
    public Double lng = 0.0;
    public int reviewRating = 0;
    public String reviewComment = "";
    public String searchFolder = "";
    @PostConstruct
    public void init() {
        pme = new PersonalMapEntity();
        pml = PersonalMapList.getInstance();
    }
    public PersonalMapController() {
    }

    public void resetMarkerList() {
        System.out.println("Personal Map Closing");
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
    }

    public void displayFolderMarker() {
        resetMarkerList();
        FolderData fd = pml.getCurrentFolder();
        List<PersonalMapMarker> pmmList = fd.getPmmL();
        System.out.println("Displaying Folders, Folder Type = " +fd.getWindowType());
        RequestContext.getCurrentInstance().execute("document.getElementById(\"formSubmitToilet:folderType\").value = "+fd.getWindowType()+"");
        if (fd.getIsEditable() == 1) {
            RequestContext.getCurrentInstance().execute("document.getElementById(\"addPersonalMarkerButton\").style.display=\"block\";");
            RequestContext.getCurrentInstance().execute("document.getElementById(\"changeFolder\").style.display = \"block\";" +
                    " document.getElementById(\"changeFolder\").style.bottom = \"186px\";");
        }else {
            RequestContext.getCurrentInstance().execute("document.getElementById(\"changeFolder\").style.display = \"block\";" +
                    " document.getElementById(\"changeFolder\").style.bottom = \"120" +
                    "px\";");
        }
        for (PersonalMapMarker m:pmmList) {
                RequestContext.getCurrentInstance().execute(
                        "var infowindowP" + m.getUniqueNo() + " = new google.maps.InfoWindow({" +
                                "   content: returnPersonalWindow(" + fd.getWindowType() + "," + m.getUniqueNo() + ",\"" + m.getField1() + "\",\"" + m.getField2() + "\"," + fd.getIsEditable() + ", "+m.getAvg_rating()+","+m.getAmt_of_ratings()+")" +  //Fill In Content Methood Here
                                "});" +
                                "newPMarker" + m.getUniqueNo() + " = " +
                                "new google.maps.Marker({ " +
                                "position:new google.maps.LatLng(" + m.getLatlng().getLat() + ", " + m.getLatlng().getLng() + "), " +
                                "map:PF('mapDisplay').getMap()});" +
                                "newPMarker" + m.getUniqueNo() + ".addListener('click',function() {" +
                                "   infowindowP" + m.getUniqueNo() + ".open(map,newPMarker" + m.getUniqueNo() + ");" +
                                "});"
                                + "markers.push(newPMarker" + m.getUniqueNo() + ");");
        }
    }
    public void folderSearch() {
        List<FolderData> searchFolderL = pme.searchFolder(searchFolder);
        for (FolderData f : searchFolderL) {
            String script = "addFolder(" + f.getFolderId() + ", \"" + f.getUser_name() + " \", \"" + f.getFolderName() + " \" , \"searchContainer\")";
            RequestContext.getCurrentInstance().execute(script);
        }
    }
    public void displayFolders() {
        List<FolderData> sponsorFolders = pme.getSponsorFolders();
        if (sponsorFolders != null) {
            if (sponsorFolders.size() > 0) {
                String script = "addDivider(\"Sponsor's Folder\",\"sponsorFolder\")";
                RequestContext.getCurrentInstance().execute(script);
            }
            for (FolderData f : sponsorFolders) {
                String script = "addFolder(" + f.getFolderId() + ", \"" + f.getUser_name() + " \", \"" + f.getFolderName() + " \" , \"sponsorFolder\")";
                RequestContext.getCurrentInstance().execute(script);
            }
        }
        List<FolderData> userFolders = pme.getUserFolders();
        String userSection = "addDivider(\"User's Folder\",\"userFolder\")";
        RequestContext.getCurrentInstance().execute(userSection);
        for(FolderData f: userFolders) {
            System.out.println("Displaying User Folder");
            String script = "addUserFolder(" + f.getFolderId() + ", \"" + f.getUser_name() +" \", \""+ f.getFolderName() + " \" , \"userFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }

        String addFolder = "addNewGroupFolder()";
        RequestContext.getCurrentInstance().execute(addFolder);

        //Imports
        List<FolderData> importFolders = pme.getImportedFolders();
        if (importFolders.size() > 0) {
            String script = "addDivider(\"Imported Folder\",\"importFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }
        for(FolderData f: importFolders) {
            //Display Folders In HTML
            String script = "addUserFolder(" + f.getFolderId() + ", \"" + f.getUser_name() +" \", \""+ f.getFolderName() + " \" , \"importFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }

        //Trending
        List<FolderData> trendingFolder = pme.getTrendingFolders();
        if (trendingFolder.size() > 0) {
            String script = "addDivider(\"Trending Folders\",\"trendingFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }
        for(FolderData f: trendingFolder) {
            //Display Folders In HTML
            String script = "addFolder(" + f.getFolderId() + ", \"" + f.getUser_name() +" \", \""+ f.getFolderName() + " \" , \"trendingFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }
    }

    public void createFolder() {
        folderId = 0;
        RequestContext.getCurrentInstance().execute("document.getElementById(\"addPersonalMarkerButton\").style.display=\"block\";");
        FolderData fd = pml.getCurrentFolder();
        fd.setFolderName(folderName);
        fd.setWindowType(folderType);
        pme.createFolderDatabase();
    }

    public void getFolderMarker() {
        FolderData fd = pml.getCurrentFolder();
        pme.getFolderMarkers(folderId);
        folderType = fd.getWindowType();
        displayFolderMarker();
    }

    public void addMarker() {
        System.out.println(folderId);
        PersonalMapMarker newPmm = new PersonalMapMarker();
        newPmm.setField1(field1);
        newPmm.setField2(field2);
        field1 = "";
        field2 = "";
        LatLng loc = new LatLng(lat,lng);
        newPmm.setLatlng(loc);
        pme.addMarker(newPmm);
        getFolderMarker();
    }
    public void submitReview() {
        FolderData fd = pml.getCurrentFolder();
        List<PersonalMapMarker> pmmList = fd.getPmmL();
        int idMarker = 0;
        for(PersonalMapMarker m: pmmList) {
            if (m.getUniqueNo() == uniqueNoJs) {
                idMarker = m.getId();
            }
        }
        pme.submitReview(idMarker,reviewRating, reviewComment);
    }
    public void editMarkerInfo() {
        FolderData fd = pml.getCurrentFolder();
        List<PersonalMapMarker> pmmList = fd.getPmmL();
        int idMarker = 0;
        for(PersonalMapMarker m: pmmList) {
            if (m.getUniqueNo() == uniqueNoJs) {
                idMarker = m.getId();
            }
        }
        pme.editMarker(idMarker,field1,field2);
    }
    public void deleteMarker() {
        FolderData fd = pml.getCurrentFolder();
        List<PersonalMapMarker> pmmList = fd.getPmmL();
        int idMarker = 0;
        for(PersonalMapMarker m: pmmList) {
            if (m.getUniqueNo() == uniqueNoJs) {
                idMarker = m.getId();
            }
        }
        pme.deleteMarker(idMarker);
        getFolderMarker();
    }
    public void userImportFolder() {
        if (pme.addImport(folderId) == false) {
            RequestContext.getCurrentInstance().execute("importFailed();");
        }
        System.out.println("Importing "+folderId);
    }
    public void userDeleteFolder() {
        pme.addDelete(folderId);
        resetMarkerList();
    }
    private UploadedFile uploadedFile;

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        uploadedFile = event.getFile();
        if (uploadedFile != null) {
            FolderData fd = pml.getCurrentFolder();
            List<PersonalMapMarker> pmmList = fd.getPmmL();
            int idMarker = 0;
            for(PersonalMapMarker m: pmmList) {
                if (m.getUniqueNo() == uniqueNoJs) {
                    idMarker = m.getId();
                }
            }
            pme.saveImg(2, uploadedFile);
            String image = Base64.getEncoder().encodeToString(uploadedFile.getContents());
            System.out.println(image);
            String script = "document.getElementById(\"2Image\").src = \" "+image+" \";";
            RequestContext.getCurrentInstance().execute(script);
        }
    }
    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderUser() {
        return folderUser;
    }

    public void setFolderUser(String folderUser) {
        this.folderUser = folderUser;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderType() { return folderType; }

    public void setFolderType(int folderType) { this.folderType = folderType; }

    public String getField1() { return field1; }

    public void setField1(String field1) { this.field1 = field1; }

    public String getField2() { return field2; }

    public void setField2(String field2) { this.field2 = field2; }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getUniqueNoJs() {
        return uniqueNoJs;
    }

    public void setUniqueNoJs(int uniqueNoJs) {
        this.uniqueNoJs = uniqueNoJs;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public String getSearchFolder() {
        return searchFolder;
    }

    public void setSearchFolder(String searchFolder) {
        this.searchFolder = searchFolder;
    }
}
