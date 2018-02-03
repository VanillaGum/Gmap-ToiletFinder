import org.primefaces.context.RequestContext;
import org.primefaces.model.map.LatLng;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class PersonalMapController {
    private PersonalMapEntity pme;
    private PersonalMapList pml;
    public int folderId =0;
    public String folderUser ="";
    public String folderName = "";
    public int folderType = -1;
    public String field1 = "";
    public String field2 = "";
    public Double lat = 0.0;
    public Double lng = 0.0;

    @PostConstruct
    public void init() {
        pme = new PersonalMapEntity();
        pml = PersonalMapList.getInstance();
    }
    public PersonalMapController() {
    }

    public void resetMarkerList() {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
    }

    public void displayFolderMarker() {
        FolderData fd = pml.getCurrentFolder();
        List<PersonalMapMarker> pmmList = fd.getPmmL();
        if (fd.getIsEditable() == 1) {
            RequestContext.getCurrentInstance().execute("document.getElementById(\"addPersonalMarkerButton\").style.display=\"block\";");
        }
        for (PersonalMapMarker m:pmmList) {
            RequestContext.getCurrentInstance().execute(
                    "var infowindowP"+m.getUniqueNo()+" = new google.maps.InfoWindow({" +
                            "   content: returnPersonalWindow("+fd.getWindowType()+","+m.getUniqueNo()+","+m.getField1()+","+m.getField2()+")" +  //Fill In Content Methood Here
                            "});" +
                            "newPMarker"+m.getUniqueNo()+" = " +
                            "new google.maps.Marker({ " +
                            "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                            "map:PF('mapDisplay').getMap()});" +
                            "newPMarker"+m.getUniqueNo()+".addListener('click',function() {" +
                            "   infowindowP"+m.getUniqueNo()+".open(map,newPMarker"+m.getUniqueNo()+");" +
                            "});"
                            + "markers.push(newPMarker"+m.getUniqueNo()+");");
        }
    }
    public void displayFolders() {
        System.out.println("Displaying Folder");
        List<FolderData> sponsorFolders = pme.getUserFolders();
        if (sponsorFolders.size() > 1) {
            String script = "addDivider(\"Sponsor's Folder\",\"sponsorFolder\")";
            RequestContext.getCurrentInstance().execute(script);
        }
        for(FolderData f: sponsorFolders) {
            //Display Folders In HTML
            folderId = f.getFolderId();
            String script = "addFolder(" + f.getFolderId() + ", \"" + f.getUser_name() +" \", \""+ f.getFolderName() + " \" , 0)";
            RequestContext.getCurrentInstance().execute(script);
        }
        List<FolderData> userFolders = pme.getUserFolders();
        String userSection = "addDivider(\"User's Folder\",\"userFolder\")";
        RequestContext.getCurrentInstance().execute(userSection);

        String addFolder = "addNewGroupFolder()";
        RequestContext.getCurrentInstance().execute(addFolder);
    }

    public void createFolder() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"addPersonalMarkerButton\").style.display=\"block\";");
        FolderData fd = pml.getCurrentFolder();
        fd.setFolderName(folderName);
        fd.setWindowType(folderType);
        pme.createFolderDatabase();
    }

    public void displayFolder() {
        pme.getFolderMarkers(folderId);
        displayFolderMarker();
    }

    public void addMarker() {
        PersonalMapMarker newPmm = new PersonalMapMarker();
        newPmm.setField1(field1);
        newPmm.setField2(field2);
        field1 = "";
        field2 = "";
        LatLng loc = new LatLng(lat,lng);
        newPmm.setLatlng(loc);
        pme.addMarker(newPmm);
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
}
