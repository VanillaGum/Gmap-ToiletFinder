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

    private int userLevel=2;


    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
