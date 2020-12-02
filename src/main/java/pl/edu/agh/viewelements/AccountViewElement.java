package pl.edu.agh.viewelements;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pl.edu.agh.model.Account;
import pl.edu.agh.util.Router;

public class AccountViewElement extends VBox {
    private final Account account;
    private final Button button;

    public AccountViewElement(Account account) {
        this.account = account;
        button = new Button("Otwórz");
        Text balanceText = new Text(account.getBalance() + " PLN");
        balanceText.setFill(account.getBalance() > 0 ? Color.GREEN : Color.RED);

        button.setOnAction((event -> {
            Router.routeTo("Hello");
        }));
        getChildren().addAll(new Text(account.getName()), balanceText, button);

        this.getStyleClass().add("account-view-element");
        this.setSpacing(20);
        button.getStyleClass().add("standard-button");

    }

}
