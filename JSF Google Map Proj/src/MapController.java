

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean
public class MapController implements Serializable{
    private double locLng = 0;
    private double locLat = 0;
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
    public void removeAllMarkers() {

    }
    public void addToiletLoc() {
        TestDatabaseClass tdc = new TestDatabaseClass();
        tdc.InsertValue(locLat,locLng);
        createMarker(locLat,locLng,"hello",null,1);
    }
    public void initToiletLoc() {
        TestDatabaseClass tdc = new TestDatabaseClass();
        List<Marker> mList = tdc.DisplayMarker();
        //Test Passed
                int i= 0;
        for (Marker m:mList) {
            i++;
            System.out.println("Marker:"+i);
        }
        
        if (mList.size() > 0) {
            createMarkerList(mList,null,0);
        }
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
}

