import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class PersonalMapController {
    private PersonalMapEntity pme;
    public int folderId =0;
    public String folderUser ="";
    public String folderName = "";
    @PostConstruct
    public void init() {
        pme = new PersonalMapEntity();
    }
    public PersonalMapController() {

    }

    public void resetMarkerList() {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
    }

//    public void displayApprovedMarker() {
//        for (PersonalMapMarker m:) {
//            RequestContext.getCurrentInstance().execute(
//                    "var infowindow"+m.getRandomId()+" = new google.maps.InfoWindow({" +
//                            "   content:createApprovedInfoWindow("+m.getRandomId()+","+m.getAvg_rating()+","+m.getGenderM()+","+m.getWheelchair()+","+m.getCost()+","+m.getAmt_of_ratings()+")" +
//                            "});" +
//                            "newMarker"+m.getRandomId()+" = " +
//                            "new google.maps.Marker({ " +
//                            "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
//                            "map:PF('mapDisplay').getMap()," +
//                            "icon:'"+m.getImage()+"'});" +
//                            "newMarker"+m.getRandomId()+".addListener('click',function() {" +
//                            "   infowindow"+m.getRandomId()+".open(map,newMarker"+m.getRandomId()+");" +
//                            "});"
//                            + "markers.push(newMarker"+m.getRandomId()+");");
//        }
//    }
    public void displayFolders() {
        for(FolderData f: pme.getUserFolders()) {
            //Display Folders In HTML
            folderId = f.getFolderId();
            String script = "addFolder(" + f.getFolderId() + ", \"" + f.getUser_name() +" \", \""+ f.getFolderName() + " \")";
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
}
