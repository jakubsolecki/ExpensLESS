package pl.edu.agh.guice;

import com.google.inject.AbstractModule;
import pl.edu.agh.dao.AccountDao;
import pl.edu.agh.dao.IAccountDao;


public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountDao.class).to(AccountDao.class);
    }
}
