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

    private MarkerRequestData suggestedMarker = new MarkerRequestData();

    private int uniqueId = 0;

    protected MarkerList() {
    }
    @PostConstruct
    public void init() {
        MarkerList ml = MarkerList.getInstance();
    }
    public static MarkerList getInstance() {
        if(instance == null) {
            instance = new MarkerList();
            instance.getApprovedToilets();
            instance.getSuggestionToilets();
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

    public MarkerRequestData getSuggestedMarker() { return suggestedMarker; }

    public void setSuggestedMarker(MarkerRequestData suggestedMarker) { this.suggestedMarker = suggestedMarker; }

    public void resetSuggestedMarker() {this.suggestedMarker = new MarkerRequestData();}

    public void addApprovedMarker(MarkerData md) {
        this.approvedMarkers.add(md);
    }
    public void addSuggestedMarker(MarkerData md) {
        System.out.println("New Suggested Marker" + md.getRandomId());
        this.suggestionMarkers.add(md);
    }
    public void getApprovedToilets() {
            System.out.println("Getting Approved Markers");
            MarkerEntity me = new MarkerEntity();
            instance.setApprovedMarkers(me.getApprovedMarkers());
            for(MarkerData m:instance.getApprovedMarkers()) {
                System.out.print("Approved Id:" + m.getRandomId() + "Rating:" + m.getAvg_rating());
            }
    }
    public void getSuggestionToilets() {
        System.out.println("Getting Suggestion Markers");
        MarkerEntity me = new MarkerEntity();
        instance.setSuggestionMarkers(me.getSuggestionMarkers());
        for(MarkerData m:instance.getSuggestionMarkers()) {
            System.out.print("Suggested Id" + m.getRandomId());
        }
    }
    public void getToilets() {
        getApprovedMarkers();
        getSuggestionMarkers();
    }
    public void resetToilets() {
        getApprovedToilets();
        getSuggestionToilets();
    }
    public void clearList() {

    }
    public int getUniqueId() {
        this.uniqueId++;
        return uniqueId;
    }
}
