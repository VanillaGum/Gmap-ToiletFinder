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

    public List<MarkerData> getSuggestionMarkers() {
        List<MarkerData> mdL = dc.getRequestedToiletMarkers();
        return mdL;
    }
    //Get Requested Toilet Markers
    public List<MarkerRequestData> getRequestedMarkers() {
        return null;
    }

    public int createSingleMarker(MarkerData md) {
        UserController uc = UserController.getInstance();
        if (uc.getUserLevel() == 2) {
            return dc.createToiletLoc(md);
        }else {
            return dc.suggestToiletLoc(md);
        }
    }

    //Upvote Suggested Toilet
    public void upvoteToilet(int toiletId) {
        dc.upvoteToilet(toiletId);
    }
    public void flagSuggestionToilet(int toiletId) {
        dc.flagSuggestionToilet(toiletId);
    }
    public void flagApprovedToilet(int toiletId) {
        dc.flagApprovedToilet(toiletId);
    }
    //Downvote Suggested Toilet
    public void downvoteToilet(int toiletId) {
        dc.downvoteToilet(toiletId);
    }

    public void rateSuggestionToilet(int toiletId, int rating ) {
        dc.rateToiletRequest(toiletId,rating);
    }
    public void rateApprovedToilet(int toiletId, int rating ) {
        dc.rateToiletApproved(toiletId,rating);
    }
}
