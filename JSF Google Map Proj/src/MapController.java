

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
public class MapController implements Serializable{
    private double locLng = 0;
    private double locLat = 0;
    private int toiletGender = 0;
    private List<Marker> initMarkerList = new ArrayList<>();
    @PostConstruct
    public void init() {
        initToiletLoc();
   }
    public void setUserLocMark() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Nothing much"));
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Float lat = (Float) map.get("latval");
        Float lng = (Float) map.get("lngval");
    }
    //Works
    public void createMarker(double latitude,double longitude,String title,Object dataz, int IconNo) {
        //Maybe Add Field For Title
        //Add
        RequestContext.getCurrentInstance().execute("var newMarker = " +
                "new google.maps.Marker({ " +
                "position:new google.maps.LatLng(" + latitude + ", " + longitude + "), " +
                "map:PF('mapDisplay').getMap()," +
                "icon:'"+getImages(IconNo)+"'});"
                + "markers.push(newMarker);");
    }
    public void createMarkerList(List<Marker> mList, Object dataz, int IconNo) {
        RequestContext.getCurrentInstance().execute("clearMarkerList();");
        for (Marker m:mList) {
            RequestContext.getCurrentInstance().execute("var newMarker = " +
                    "new google.maps.Marker({ " +
                    "position:new google.maps.LatLng(" + m.getLatlng().getLat()+ ", " +  m.getLatlng().getLng() + "), " +
                    "map:PF('mapDisplay').getMap()," +
                    "icon:'"+getImages(IconNo)+"'});"
                    + "markers.push(newMarker);");
        }
    }
    public String getImages(int ImgNo) {
        switch (ImgNo) {
            case 0:
                return "images/toilet_male.png";
            case 1:
                return "images/toilet_female.png";
            case 2:
        }
        return "";
    }
    public String getImages(MarkerData m) {
        int iconNo = m.getIconNo();
        switch(m.getGenderM()) {
            case 0:
                //Female Gender
                switch(iconNo) {
                    case -1:
                        return "images/toilet_female.png";
                    case 0:

                    case 1:

                    case 2:

                    case 3:

                    case 4:

                    case 5:
                }
                break;
            case 1:
                //Male Gender
                switch(iconNo) {
                    case -1:
                        return "images/toilet_male.png";
                    case 0:

                    case 1:

                    case 2:

                    case 3:

                    case 4:

                    case 5:
                }
                break;
            case 2:
                //Both Genders
                break;
        }
        return null;
    }

    public void removeAllMarkers() {

    }
    public void addToiletLoc() {
        MarkerData md = new MarkerData(new LatLng(locLat,locLng),toiletGender);
        System.out.println(locLat + "Lng:" + locLng);
        DatabaseClass tdc = new DatabaseClass();
        tdc.suggestToiletLoc(md);
        createMarker(locLat,locLng,"hello",null,toiletGender);
    }
    public void initToiletLoc() {
        DatabaseClass tdc = new DatabaseClass();
//        if (mList.size() > 0) {
//            createMarkerList(mList,null,0);
//        }
    }
    public void onStateChange(StateChangeEvent event) {

    }
    public void setLocLng(double locLng) {
        this.locLng = locLng;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLng() {
        return locLng;
    }

    public double getLocLat() {
        return locLat;
    }

    public int getToiletGender() { return toiletGender; }

    public void setToiletGender(int toiletGender) { this.toiletGender = toiletGender; }
}

