import java.util.List;

public class PersonalMapEntity {
    private DatabaseClass dc;
    private PersonalMapList pml;
    public PersonalMapEntity() {
        dc = new DatabaseClass();
        pml = PersonalMapList.getInstance();
    }

    public List<FolderData> getUserFolders() {
        return dc.getFolders();
    }

    public void getFolderMarkers(int folderId) {
        pml.refreshCurrentFolder();
        FolderData currentFolder = pml.getCurrentFolder();
        currentFolder.setFolderId(folderId);
        dc.getFolderMarkers(folderId);
    }
    public void createFolderDatabase() {
        dc.createFolder();
    }

}
