import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean
public class PersonalMapList {
    private FolderData currentFolder = new FolderData();
    private static PersonalMapList instance;
    private int uniqueNo = 0;
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

    public void refreshCurrentFolder() {
        this.currentFolder = new FolderData();
    }
    public FolderData getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(FolderData currentFolder) {
        this.currentFolder = currentFolder;
    }

    public List<PersonalMapMarker> getDisplayedList() {
        return displayedList;
    }

    public void setDisplayedList(List<PersonalMapMarker> displayedList) {
        this.displayedList = displayedList;
    }

    public int getUniqueNo() {
        this.uniqueNo++;
        return uniqueNo;
    }
}
