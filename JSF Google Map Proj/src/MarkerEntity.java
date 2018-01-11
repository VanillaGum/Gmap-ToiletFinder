import org.primefaces.model.map.LatLng;

import java.util.List;

public class MarkerEntity {
    //Use this to get data And make markers

    DatabaseClass dc;
    public MarkerEntity() {
        dc = new DatabaseClass();
    }

    //Get Approved Toilet Markers
    public List<MarkerData> getApprovedMarkers() {
        List<MarkerData> mdL = dc.getApprovedToiletMarkers();
        return mdL;
    }

    //Get Requested Toilet Markers
    public List<MarkerRequestData> getRequestedMarkers() {
        return null;
    }

    public void createSingleMarker(MarkerData md) {
        dc.suggestToiletLoc(md);
    }
}
