package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.ICategoryDao;
import pl.edu.agh.dao.ISubcategoryDao;
import pl.edu.agh.dao.SubcategoryDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubcategoryDaoTest {

    private ISubcategoryDao subcategoryDao;
    private ICategoryDao categoryDao;
    private boolean clearDBAfterEveryTest = true; // Constraint on category name. Examples can also be changed

    @BeforeEach
    public void before() {
        SessionUtil.openSession();
        subcategoryDao = new SubcategoryDao();
        categoryDao = new CategoryDao();
    }

    @AfterEach
    public void after() {

        if (clearDBAfterEveryTest) {
            Session session = SessionUtil.getSession();
            Transaction tx = session.getTransaction();

            if (!tx.isActive()) {
                tx.begin();
            }

            session.createQuery("DELETE FROM Categories").executeUpdate();
            session.createQuery("DELETE FROM Subcategories").executeUpdate();
            tx.commit();
        }

        SessionUtil.closeSession();
    }

    @Test
    public void saveSubcategoryTest() {
        // given
        Category category = new Category("Category");
        Subcategory subcategory = new Subcategory("Subcategory 1", category);

        // when
        subcategoryDao.saveSubcategory(subcategory);

        // then
        Subcategory result = SessionUtil.getSession()
                .createQuery("From Subcategories", Subcategory.class).getSingleResult();
        assertEquals(result, subcategory);
        assertEquals(category, subcategory.getCategory());
    }

    @Test
    public void getAllSubcategories() {
        // given
        Category category = new Category("Category");
        Subcategory subcategory1 = new Subcategory("Subcategory 1", category);
        Subcategory subcategory2 = new Subcategory("Subcategory 2", category);

        // when
        subcategoryDao.saveSubcategory(subcategory1);
        subcategoryDao.saveSubcategory(subcategory2);

        // then
        List<Subcategory> result = subcategoryDao.getAllSubcategories();
        assertTrue(result.contains(subcategory1) && result.contains(subcategory2));
        assertTrue(category.getSubcategories().contains(subcategory1));
    }
}
