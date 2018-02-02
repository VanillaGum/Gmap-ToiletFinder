import java.util.List;

public class PersonalMapEntity {
    private DatabaseClass dc;

    public PersonalMapEntity() {
        dc = new DatabaseClass();
    }

    public List<FolderData> getUserFolders() {
        return dc.getFolders();
    }

}
