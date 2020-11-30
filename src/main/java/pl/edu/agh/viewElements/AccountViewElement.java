package pl.edu.agh.viewElements;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.edu.agh.model.Account;

public class AccountViewElement extends VBox {
    private final Account account;

    public AccountViewElement(Account account) {
        this.account = account;
        getChildren().addAll(new Text(account.getName()),
                new Text(account.getBalance() + " PLN"),
                new Button("Open"));

    }
}
