package pl.edu.agh.controller;

import javafx.scene.input.MouseEvent;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

public class BudgetController {
    public void backButtonClicked(MouseEvent mouseEvent) {
        Router.routeTo(View.MENU);
    }
}
