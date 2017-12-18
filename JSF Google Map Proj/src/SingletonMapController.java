import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
final public class SingletonMapController {
    //Used to keep a copy of the MapModel For Reference
    //Keeps a copy of all currently displayed Markers
    //Keeps copy of suggestions to toilet location
    private static SingletonMapController instance = null;
    private MapModel displayMap;
    private SingletonMapController() {
    }
    public static SingletonMapController getInstance() {
        if (instance == null) {
            instance = new SingletonMapController();
        }
        return instance;
    }
}
