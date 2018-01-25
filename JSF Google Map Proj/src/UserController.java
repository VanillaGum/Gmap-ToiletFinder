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

    private int userLevel=3;
    private int user_id = 0;


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
