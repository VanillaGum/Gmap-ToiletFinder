//Singleton to hold the list of markers for Gmap

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean
public class MarkerList {
    private static MarkerList instance;
    //Approved Markers From Database
    private List<MarkerData> approvedMarkers = new ArrayList<>();
    //Currently SuggestedMarker
    private List<MarkerData> suggestedMarkers = new ArrayList<>();
    //Suggested Markers From Database
    private List<MarkerData> suggestionMarkers = new ArrayList<>();
    protected MarkerList() {
    }
    @PostConstruct
    public void init() {
        MarkerList ml = MarkerList.getInstance();
    }
    public static MarkerList getInstance() {
        if(instance == null) {
            System.out.println("Making Instance");
            instance = new MarkerList();
            instance.getApprovedToilets();
        }
        return instance;
    }

    public List<MarkerData> getApprovedMarkers() {
        return approvedMarkers;
    }

    public void setApprovedMarkers(List<MarkerData> approvedMarkers) {
        this.approvedMarkers = approvedMarkers;
    }

    public List<MarkerData> getSuggestedMarkers() {
        return suggestedMarkers;
    }

    public void setSuggestedMarkers(List<MarkerData> suggestedMarkers) {
        this.suggestedMarkers = suggestedMarkers;
    }

    public List<MarkerData> getSuggestionMarkers() {
        return suggestionMarkers;
    }

    public void setSuggestionMarkers(List<MarkerData> suggestionMarkers) {
        this.suggestionMarkers = suggestionMarkers;
    }

    public void addApprovedMarker(MarkerData md) {
        this.approvedMarkers.add(md);
    }
    public void addSuggestedMarker(MarkerData md) {
        this.suggestedMarkers.add(md);
    }
    public void getApprovedToilets() {
//        if (approvedMarkers.isEmpty()) {
            System.out.println("Empty ArrayList, Getting Markers");
            MarkerEntity me = new MarkerEntity();
            instance.setApprovedMarkers(me.getApprovedMarkers());
//        }
    }
}
