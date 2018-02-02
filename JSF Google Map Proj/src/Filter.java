import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

import static java.lang.Double.parseDouble;

@ViewScoped
@ManagedBean
public class Filter implements Serializable {

    private boolean male;
    private boolean female;
    private int rating;
    private boolean wheelchairAccessible;
    private boolean needToPay;
    private int radius;

    private MapController mc = new MapController();
    private DatabaseClass dbc = new DatabaseClass();
    private double computedDistance;

    public Filter() {
        this.male = false;
        this.female = false;
        this.rating = 5;
        this.wheelchairAccessible = false;
        this.needToPay = false;
        this.radius = 100000;
    }

    public Filter(boolean male, boolean female, int rating, boolean wheelchairAccessible, boolean needToPay, int radius) {
        this.male = male;
        this.female = female;
        this.rating = rating;
        this.wheelchairAccessible = wheelchairAccessible;
        this.needToPay = needToPay;
        this.radius = radius;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(boolean wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public void setNeedToPay(boolean needToPay) {
        this.needToPay = needToPay;
    }

    public boolean getNeedToPay() {
        return needToPay;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setComputedDistance(double computedDistance) {
        this.computedDistance = computedDistance;
    }

    public double getComputedDistance() {
        return computedDistance;
    }

    public int searchByFilter() throws InterruptedException {
        if (!validateFilter()) // Check to see if at least one gender is selected
            return 1; // It's a nope

        List<MarkerData> md = dbc.getApprovedToiletMarkers(); // Load toilets from database
        md.addAll(dbc.getRequestedToiletMarkers());

        mc.resetMarkerList();
        for (int i = 0; i < md.size(); i++) {
            while (true) {
                //System.out.println("genderM " + md.get(i).getGenderM() + " avgRating " + md.get(i).getAvg_rating() + " Wheelchair " + md.get(i).getWheelchair() + " needToPay " + md.get(i).getCost());
                int gender;
                if (male)
                    if (female)
                        gender = 2; // Male and Female are both checked
                    else
                        gender = 1; // Male is checked
                else
                    gender = 0; // Female is checked

                RequestContext.getCurrentInstance().execute("calculateDistance(" + md.get(i).getLatlng().getLat() + ", " + md.get(i).getLatlng().getLng() + ")");

                System.out.println(computedDistance);

                //System.out.println(md.get(i).getLatlng().toString());

                if (gender == 1) {
                    if (!(md.get(i).getGenderM() >= 1)) // Are there no male toilets?
                        break; // The place doesn't have male toilets. Stop processing and move on to the next entry.
                } else if (gender == 0) {
                    if (md.get(i).getGenderM() == 1) // Are there no female toilets?
                        break; // The place doesn't have female toilets. Stop processing and move on to the next entry.
                }

                if (!(md.get(i).getAvg_rating() >= rating)) // Does the rating not meet the minimum rating requirements set out by the user?
                    break; // Rating lower than minimum. Stop processing and move on to the next entry.

                if (wheelchairAccessible) // Is the wheelchair accessible box ticked?
                    if (md.get(i).getWheelchair() == 0) // Is the toilet not wheelchair friendly?
                        break; // Not wheelchair friendly. Stop processing and move on to the next entry.

                if (!needToPay) // Is the entry fee checkbox unchecked?
                    if (!(md.get(i).getCost() == 0.00)) // Is the toilet not free?
                        break; // Yes. Stop processing and move on to the next entry.

                // rc.execute("addFilterMarker(" + md.get(i).getLatlng().getLat() + ", " + md.get(i).getLatlng().getLng() + ", " + md.get(i).getTitle() + ")");
                mc.displaySingleMarker(md.get(i));
                break;
            }
            RequestContext.getCurrentInstance().execute("map.setCenter({lat: 1.350416667, lng: 103.82193194})");
            RequestContext.getCurrentInstance().execute("map.setZoom(12)");
        }
        return 0;
    }

    public boolean validateFilter() {
        if (!male && !female) {
            RequestContext.getCurrentInstance().execute("alert('Please select at least one gender')");
            return false;
        } else {
            return true;
        }
    }

    public void resetFilter() {
        mc.resetMarkerList();
        mc.displayMarkersList(dbc.getApprovedToiletMarkers());
        mc.displayMarkersList(dbc.getRequestedToiletMarkers());
    }

    public void saveComputedDistance() {
        this.computedDistance = parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("distance"));
    }

}