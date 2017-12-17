import com.sun.javafx.collections.MappingChange;
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.swing.*;
import java.util.List;
import java.util.Map;

@ManagedBean
public class MapController {
    private MapModel displayMap;
    private double locLng = 0;
    private double locLat = 0;
    @PostConstruct
    public void init() {
        System.out.println("Making new map model");
        SingletonMapController smc = SingletonMapController.getInstance();
        if (smc.mapCheck() == true) {
            displayMap = smc.getDisplayMap();
            System.out.println(displayMap);
        }else {
            displayMap = new DefaultMapModel();
            smc.setDisplayMap(displayMap);
            System.out.println("Setting display Map");
        }
        LatLng coords1= new LatLng(1.379008, 103.849602);
        LatLng coords2= new LatLng(1.379351, 103.850085);
        //displayMap.addOverlay(new Marker(new LatLng(1.379008, 103.849602),"Male Toilet","images/toilet_male.png","images/toilet_male.png"));
        //displayMap.addOverlay(new Marker(coords2,"Female Toilet", null,"images/toilet_female.png"));
        Marker m = new Marker(coords1,"Male Toilet","images/toilet_male.png","images/toilet_male.png");
        createMarker(1.350208, 103.874409,"IDK",null,0);
        getToiletLoc();
   }
    public void setUserLocMark() {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Nothing much"));
        Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Float lat = (Float) map.get("latval");
        Float lng = (Float) map.get("lngval");
    }
    //Works
    public void createMarker(double latitude,double longitude,String title,Object dataz, int IconNo) {
        LatLng coords1= new LatLng(latitude, longitude);
        displayMap.addOverlay(new Marker(coords1, title, null, getImages(IconNo)));
        System.out.println("Displaying Marker");
        RequestContext requestContext = RequestContext.getCurrentInstance();
    }
    public void createMarkerList(List<Marker> mList, Object dataz, int IconNo) {

        for(int i=0; i< mList.size();i++) {
            Marker m = mList.get(i);
            m.setIcon(getImages(IconNo));
            displayMap.addOverlay(m);
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
        System.out.println("Hey Removing All Markers");
        List<Marker> mRemoveList = displayMap.getMarkers();
        if (mRemoveList.size() >0 ) {
            System.out.println("Marker List Is Not Empty");
        }else {
            System.out.println("Marker List Is Empty");
        }
        displayMap.getMarkers().removeAll(mRemoveList);
    }
    public MapModel getDisplayMap() {
        return displayMap;
    }

    public void addToiletLoc() {
        System.out.println("Longitude:" + locLng);
        System.out.println("Latitude:" + locLat);

        TestDatabaseClass tdc = new TestDatabaseClass();
        tdc.InsertValue(locLat,locLng);
//        createMarker(locLat, locLng,"Test",null,1);
    }
    public void getToiletLoc() {
        TestDatabaseClass tdc = new TestDatabaseClass();
        List<Marker> mList = tdc.DisplayMarker();
        if (mList.size() > 0) {
            createMarkerList(mList,null,0);
        }
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

