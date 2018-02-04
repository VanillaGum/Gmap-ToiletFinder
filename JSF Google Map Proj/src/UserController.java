public class UserController {
    //Make User Controller A Singleton
    private static UserController instance = null;

    protected UserController() {
        // Prevent instantiation.
    }
    public static UserController getInstance() {
        if(instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    //O is Anon
    //1 Is Logged in
    //2 is admin/trusted
    private int userLevel=1;
    private int user_id = 0;
    //-1 user_id is default
    private String username = "bob";

    public int getUserLevel() {
        return userLevel;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
