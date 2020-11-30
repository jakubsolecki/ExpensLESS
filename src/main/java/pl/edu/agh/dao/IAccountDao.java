package pl.edu.agh.dao;

import pl.edu.agh.model.Account;

import java.util.List;

public interface IAccountDao {

    void saveAccount(Account account);
    List<Account> getAllAccounts();
    void addTransaction(Account account, Object transaction);
}
