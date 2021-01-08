package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.SubcategoryBudgetDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.SubcategoryBudget;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubcategoryBudgetDaoTest {

    private SubcategoryBudgetDao categoryBudgetDao;
    private boolean clearDBAfterEveryTest = true;

    @BeforeEach
    public void beforeEach() {
        SessionUtil.openSession();
        categoryBudgetDao = new SubcategoryBudgetDao();
    }

    @AfterEach
    public void afterEach() {

        if (clearDBAfterEveryTest) {
            Session session = SessionUtil.getSession();
            Transaction tx = session.getTransaction();

            if (!tx.isActive()) {
                tx.begin();
            }

            session.createQuery("DELETE FROM SubcategoryBudgets").executeUpdate();
            session.createQuery("DELETE FROM Categories").executeUpdate();
            tx.commit();
        }

        SessionUtil.closeSession();
    }

    @Test
    public void saveCategoryBudgetTest() {
        // given
        Category category = new Category("Category 1");
        Subcategory subcategory = new Subcategory("subcategory a", category);
        SubcategoryBudget subcategoryBudget = new SubcategoryBudget(subcategory, new BigDecimal(100));

        // when
        categoryBudgetDao.save(subcategoryBudget);

        // then
        SubcategoryBudget result = SessionUtil.getSession()
                .createQuery("from SubcategoryBudgets", SubcategoryBudget.class).getSingleResult();
        assertEquals(subcategoryBudget, result);
    }
}
