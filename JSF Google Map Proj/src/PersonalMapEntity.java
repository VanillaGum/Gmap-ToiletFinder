import java.util.List;

public class PersonalMapEntity {
    private DatabaseClass dc;
    private PersonalMapList pml;
    public PersonalMapEntity() {
        dc = new DatabaseClass();
        pml = PersonalMapList.getInstance();
    }

    public List<FolderData> getUserFolders() {
        return dc.getUserFolders();
    }
    public List<FolderData> getSponsorFolders() {
        return dc.getSponsorFolders();
    }
    public List<FolderData> getImportedFolders() {
        return dc.getImportedFolders();
    }
    public List<FolderData> getTrendingFolders() {
        return dc.getTrendingFolders();
    }
    public boolean addImport(int folderId) {return dc.addImport(folderId); }
    public void addDelete(int folderId) {dc.addDelete(folderId); }
    public void getFolderMarkers(int folderId) {
        pml.refreshCurrentFolder();
        FolderData currentFolder = pml.getCurrentFolder();
        currentFolder.setFolderId(folderId);
        dc.getFolderMarkers(folderId);
    }
    public void addMarker(PersonalMapMarker pmm) {
        dc.addMarker(pmm);
    }
    public void editMarker(int id, String field1, String field2) {
        dc.editPersonalMarker(id, field1, field2);
    }
    public void deleteMarker(int id) {dc.deletePersonalMarker(id);}
    public void createFolderDatabase() {
        dc.createFolder();
    }

}
