import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean
public class PersonalMapList {

    private static PersonalMapList instance;
//    Keep Markers Of Currently To Display Markers
    private List<PersonalMapMarker> displayedList = new ArrayList<>();

    protected PersonalMapList() {
    }
    @PostConstruct
    public void init() {
        PersonalMapList ml = PersonalMapList.getInstance();
    }
    public static PersonalMapList getInstance() {
        if(instance == null) {
            instance = new PersonalMapList();
        }
        return instance;
    }


}
