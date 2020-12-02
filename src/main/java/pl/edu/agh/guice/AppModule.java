package pl.edu.agh.guice;

import com.google.inject.AbstractModule;
import pl.edu.agh.dao.*;


public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IAccountDao.class).to(AccountDao.class);
        bind(ICategoryDao.class).to(CategoryDao.class);
        bind(ISubcategoryDao.class).to(SubcategoryDao.class);
        bind(ITransactionDao.class).to(TransactionDao.class);
    }
}
