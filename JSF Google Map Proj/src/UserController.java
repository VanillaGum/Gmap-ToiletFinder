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
    private int user_id = 1;
    //-1 user_id is default


    public int getUserLevel() {
        return userLevel;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
