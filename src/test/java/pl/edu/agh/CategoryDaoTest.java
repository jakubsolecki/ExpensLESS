package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.ICategoryDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryDaoTest {

    private ICategoryDao categoryDao;

    @BeforeEach
    public void before() {
        SessionUtil.openSession();
        categoryDao = new CategoryDao();
    }

    @AfterEach
    public void after() {
//        Session session = SessionUtil.getSession();
//        session.createQuery("DELETE FROM Categories").executeUpdate();
        SessionUtil.closeSession();

    }

    @Test
    public void saveCategoryTest() {
        // given
        Category category = new Category("Category 1");

        // when
        categoryDao.saveCategory(category);

        // then
        Category result = SessionUtil.getSession()
                .createQuery("From Categories", Category.class).getSingleResult();
        assertEquals(result, category);
    }

    @Test
    public void getAllCategories() {
        // given
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");

        // when
        categoryDao.saveCategory(category1);
        categoryDao.saveCategory(category2);

        // then
        List<Category> result = categoryDao.getAllCategories();
        assertTrue(result.contains(category1) && result.contains(category1));
    }

}
