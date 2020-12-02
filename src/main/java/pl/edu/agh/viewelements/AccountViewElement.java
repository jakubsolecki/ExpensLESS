package pl.edu.agh.viewelements;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.edu.agh.model.Account;
import pl.edu.agh.util.Router;

public class AccountViewElement extends VBox {
    private final Account account;
    private final Button button;

    public AccountViewElement(Account account) {
        this.account = account;
        button = new Button("Open");

        button.setOnAction((event -> {
            Router.routeTo("Hello");
        }));
        getChildren().addAll(new Text(account.getName()),
                new Text(account.getBalance() + " PLN"),
                button);

        this.getStyleClass().add("account-view-element");

    }

}
