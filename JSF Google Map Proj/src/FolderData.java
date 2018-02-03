import java.util.List;

public class FolderData {
    private int folderId;

    private String user_name;

    private String folderName;

    private int windowType;

    private int isEditable;

    private List<PersonalMapMarker> pmmL;
    public FolderData() {
    }

    public FolderData(int folderId, String user_name, String folderName) {
        this.folderId = folderId;
        this.user_name = user_name;
        this.folderName = folderName;
    }

    public int getFolderId() { return folderId; }

    public void setFolderId(int folderId) { this.folderId = folderId; }

    public String getUser_name() { return user_name; }

    public void setUser_name(String user_name) { this.user_name = user_name; }

    public String getFolderName() { return folderName; }

    public void setFolderName(String folderName) { this.folderName = folderName; }

    public int getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(int isEditable) {
        this.isEditable = isEditable;
    }

    public int getWindowType() {
        return windowType;
    }

    public void setWindowType(int windowType) {
        this.windowType = windowType;
    }

    public List<PersonalMapMarker> getPmmL() {
        return pmmL;
    }

    public void setPmmL(List<PersonalMapMarker> pmmL) {
        this.pmmL = pmmL;
    }
    public void addpmml(PersonalMapMarker pmm) {
        this.pmmL.add(pmm);
    }
}
