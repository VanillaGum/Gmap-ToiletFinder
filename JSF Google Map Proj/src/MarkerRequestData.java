import org.primefaces.model.map.LatLng;

//Used for suggestion markers
public class MarkerRequestData extends MarkerData {
    public MarkerRequestData(LatLng latlng, int genderM, int rating) {
        super(latlng, genderM, rating);
        this.image = getImageStrings(genderM);
    }

    private String getImageStrings(int genderM) {
        switch(genderM) {
            case 0:
                return "images/toilet_female.png";
            case 1:
                return "images/toilet_male_request.png";
            case 2:
                return "images/toilet_male_request.png";
        }
        return null;
    }

}
