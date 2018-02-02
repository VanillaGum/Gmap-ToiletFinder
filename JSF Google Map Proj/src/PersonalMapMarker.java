import org.primefaces.context.RequestContext;
import org.primefaces.model.map.LatLng;

public class PersonalMapMarker extends MarkerData{
//    Description Of Place
    private String description;

//    Exact Location Such As #04-01
    private String locationDetails;

//    For Used To Display Image
    private String imageDisplay;


    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocationDetails() { return locationDetails; }

    public void setLocationDetails(String locationDetails) { this.locationDetails = locationDetails; }

    public String getImageDisplay() { return imageDisplay; }

    public void setImageDisplay(String imageDisplay) { this.imageDisplay = imageDisplay; }
}
