package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import pl.edu.agh.util.Router;

public class HelloController {
    public void handleAction(ActionEvent actionEvent) {
        Router.routeTo("Account");
    }
}
