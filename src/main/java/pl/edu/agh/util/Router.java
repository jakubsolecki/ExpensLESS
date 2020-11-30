package pl.edu.agh.util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Router {
    private static final HashMap<String, Pane> paneMap = new HashMap<>();
    private static Scene mainScene;

    public static void setMainScene(Scene scene){
        if (mainScene == null){
            mainScene = scene;
        }
    }

    public static void addPane(String name, Pane pane){
        paneMap.put(name, pane);
    }

    public static void routeTo(String name){
        mainScene.setRoot(paneMap.get(name));
    }
}
