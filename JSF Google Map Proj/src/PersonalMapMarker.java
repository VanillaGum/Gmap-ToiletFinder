import org.primefaces.context.RequestContext;
import org.primefaces.model.map.LatLng;

public class PersonalMapMarker extends MarkerData{
//    Description Of Place
    private String description;

//    Exact Location Such As #04-01
    private String locationDetails;

//    For Used To Display Image
    private String imageDisplay;

    private int id;

    private String field1 = "";
    private String field2 = "";

    private int uniqueNo;

    public PersonalMapMarker() {
        PersonalMapList pml = PersonalMapList.getInstance();
        this.uniqueNo = pml.getUniqueNo();
    }

    public PersonalMapMarker(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
        PersonalMapList pml = PersonalMapList.getInstance();
        this.uniqueNo = pml.getUniqueNo();
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocationDetails() { return locationDetails; }

    public void setLocationDetails(String locationDetails) { this.locationDetails = locationDetails; }

    public String getImageDisplay() { return imageDisplay; }

    public void setImageDisplay(String imageDisplay) { this.imageDisplay = imageDisplay; }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }


    public int getUniqueNo() {
        return uniqueNo;
    }
}
