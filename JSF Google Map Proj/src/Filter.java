import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean
public class Filter implements Serializable {

    private boolean male;
    private boolean female;
    private int rating;
    private boolean wheelchairAccessible;
    private Boolean needToPay;

    public Filter() {
        this.male = false;
        this.female = false;
        this.rating = 5;
        this.wheelchairAccessible = false;
    }

    public Filter(boolean male, boolean female, int rating, boolean wheelchairAccessible) {
        this.male = male;
        this.female = female;
        this.rating = rating;
        this.wheelchairAccessible = wheelchairAccessible;
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

    public void setNeedToPay(Boolean needToPay) {
        this.needToPay = needToPay;
    }

    public Boolean getNeedToPay() {
        return needToPay;
    }

    public int searchByFilter() {
        RequestContext rc = RequestContext.getCurrentInstance();
        if (!validateFilter(rc)) // Check to see if at least one gender is selected
            return 1; // It's a nope
        rc.execute("deleteFilterMarkers()");
        DatabaseClass dbc = new DatabaseClass();
        List<MarkerData> md = dbc.getRequestedToiletMarkers(); // Load toilets from database
        for (int i = 0; i < md.size(); i++) {
            while (true) {
                System.out.println("genderM " + md.get(i).getGenderM() + " avgRating " + md.get(i).getAvg_rating() + " Wheelchair " + md.get(i).getWheelchair() + " needToPay " + md.get(i).getCost());
                int gender;
                if (male)
                    if (female)
                        gender = 2; // Male and Female are both checked
                    else
                        gender = 1; // Male is checked
                else
                    gender = 0; // Female is checked
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

                rc.execute("addFilterMarker(" + md.get(i).getLatlng().getLat() + ", " + md.get(i).getLatlng().getLng() + ", " + md.get(i).getTitle() + ")");
                rc.execute("map.setCenter({lat: 1.350416667, lng: 103.82193194})");
                rc.execute("map.setZoom(12)");
                break;
            }
        }
        return 0;
    }

    public boolean validateFilter(RequestContext rc) {
        if (!male && !female) {
            rc.execute("alert('Please select at least one gender')");
            return false;
        } else {
            return true;
        }
    }
}