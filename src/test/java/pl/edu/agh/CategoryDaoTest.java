package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.SubcategoryDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryDaoTest {

    private CategoryDao categoryDao;
    private boolean clearDBAfterEveryTest = true; // Constraint on category name. Examples can also be changed


    @BeforeEach
    public void beforeEach() {
        SessionUtil.openSession();
        categoryDao = new CategoryDao();
    }

    @AfterEach
    public void afterEach() {

        if (clearDBAfterEveryTest) {
            Session session = SessionUtil.getSession();
            Transaction tx = session.getTransaction();

            if (!tx.isActive()) {
                tx.begin();
            }

            session.createQuery("DELETE FROM Categories").executeUpdate();
            tx.commit();
        }

        SessionUtil.closeSession();
    }

    @Test
    public void saveCategoryTest() {
        // given
        Category category = new Category("Category 1");

        // when
        categoryDao.save(category);

        // then
        Category result = SessionUtil.getSession()
                .createQuery("From Categories", Category.class).getSingleResult();
        assertEquals(category, result);
    }

    @Test
    public void getAllCategories() {
        // given
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");

        // when
        categoryDao.save(category2);
        categoryDao.save(category3);

        // then
        List<Category> result = categoryDao.getAllCategories();
        assertTrue(result.contains(category2) && result.contains(category3));
    }

    @Test
    public void findCategoryById() {
        // given
        Category category4 = new Category("Category 4");
        Category category5 = new Category("Category 5");

        // when
        categoryDao.save(category4);
        categoryDao.save(category5);

        // then
        Category resultCategory = categoryDao.findCategoryByName("Category 5");
        assertEquals(category5, resultCategory);
    }

}
