package pl.edu.agh.util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.HashMap;


public class Router {

    private static final HashMap<String, Pane> paneMap = new HashMap<>();
    @Setter
    private static Scene mainScene;

    public static void addPane(String name, Pane pane){
        paneMap.put(name, pane);
    }

    public static void routeTo(String name){
        mainScene.setRoot(paneMap.get(name));
    }

}
