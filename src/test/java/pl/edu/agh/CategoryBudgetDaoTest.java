package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.CategoryBudgetDao;
import pl.edu.agh.dao.ICategoryBudgetDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.CategoryBudget;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryBudgetDaoTest {

    private ICategoryBudgetDao categoryBudgetDao;
    private boolean clearDBAfterEveryTest = true;

    @BeforeEach
    public void beforeEach() {
        SessionUtil.openSession();
        categoryBudgetDao = new CategoryBudgetDao();
    }

    @AfterEach
    public void afterEach() {

        if (clearDBAfterEveryTest) {
            Session session = SessionUtil.getSession();
            Transaction tx = session.getTransaction();

            if (!tx.isActive()) {
                tx.begin();
            }

            session.createQuery("DELETE FROM CategoryBudgets").executeUpdate();
            session.createQuery("DELETE FROM Categories").executeUpdate();
            tx.commit();
        }

        SessionUtil.closeSession();
    }

    @Test
    public void saveCategoryBudgetTest() {
        // given
        Category category = new Category("Category 1");
        CategoryBudget categoryBudget = new CategoryBudget(category, new BigDecimal(100));

        // when
        categoryBudgetDao.saveCategoryBudget(categoryBudget);

        // then
        CategoryBudget result = SessionUtil.getSession()
                .createQuery("from CategoryBudgets", CategoryBudget.class).getSingleResult();
        assertEquals(categoryBudget, result);
    }
}
