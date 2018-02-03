import org.primefaces.context.RequestContext;

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
                            "   content: " +  //Fill In Content Methood Here
                            "});" +
                            "newPMarker"+m.getUniqueNo()+" = " +
                            "new google.maps.Marker({ " +
                            "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                            "map:PF('mapDisplay').getMap()," +
                            "icon:'"+m.getImage()+"'});" +
                            "newMarker"+m.getRandomId()+".addListener('click',function() {" +
                            "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
                            "});"
                            + "markers.push(newMarker"+m.getRandomId()+");");
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
}
