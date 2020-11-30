package pl.edu.agh.service;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import pl.edu.agh.dao.IAccountDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.util.SessionUtil;
import java.util.List;

public class AccountService {

    private final IAccountDao accountDao;

    @Inject
    public AccountService(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void createAccount(Account account){
        SessionUtil.openSession();
        accountDao.saveAccount(account);
        SessionUtil.closeSession();
    }

    public List<Account> getAllAccounts(){
        SessionUtil.openSession();
        var list = accountDao.getAllAccounts();
        SessionUtil.closeSession();
        return list;
    }
}
