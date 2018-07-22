import com.denis.timetracking.exception.DaoException;
import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.entity.Activity;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by Denis on 21.05.2018.
 */
public class ActivityDaoTest {
    private DaoFactory manager;
    private ActivityDao dao;
    private HttpServletRequest request;
    private static TestInitializer initializer;
    private HttpServletResponse response;
    private List<Activity> activities;

    @BeforeClass
    public static void initClass(){
        initializer = new TestInitializer();
        initializer.initializeTest();
    }

    @Before
    public void initTest(){
        request = mock(HttpServletRequest.class);
        initializer.initializeData(request);
        manager = new DaoFactory();
        dao = (ActivityDao) manager.getDao("ACTIVITY");
        dao.openCurrentSession();
    }

    @After
    public void clearData(){
        initializer.clearData();
        dao.closeCurrentSession();
    }

    @Test
    public void testGetAll() throws SQLException {
        activities = dao.getAll();
        assertEquals(3, activities.size());
    }

    @Test
    public void testGetActual() throws SQLException {
        activities = dao.getActual();
        assertEquals(1, activities.size());
    }

    @Test
    public void testGetAdded() throws SQLException {
        activities = dao.getAdded();
        assertEquals(1, activities.size());
    }

    @Test
    public void testGetRemoved() throws SQLException {
        activities = dao.getRemoved();
        assertEquals(1, activities.size());
    }

    @Test
    public void testGetByUserId() throws SQLException {
        activities = dao.getByUserId(2);
        assertEquals(1, activities.size());
    }

    @Test
    public void testGetById() throws SQLException {
        Activity activity = dao.getById(1);
        assertNotNull(activity);
        assertNull(activity.getDeadLine());
    }

    @Test
    public void testDoesExist() throws SQLException {
        boolean activity = dao.isExist(1);
        assertTrue(activity);
    }

    @Test
    public void testInsert() throws SQLException, IncorrectInputException {
        dao.insert(createTestActivity());
        assertEquals(4, dao.getAll().size());
    }

    @Test(expected = DaoException.class)
    public void testInsertNull() throws SQLException, IncorrectInputException {
        dao.insert(null);
    }

    @Test
    public void testUpdate() throws SQLException, IncorrectInputException {
        Activity activity = createTestActivity();
        dao.insert(activity);
        assertEquals("New Description", dao.getById(4).getDescription());
        activity.setDescription("New");
        dao.update(activity);
        assertEquals("New", dao.getById(4).getDescription());
    }

    @Test
    public void testDelete() throws SQLException, IncorrectInputException {
        Activity activity = createTestActivity();
        dao.insert(activity);
        assertEquals(4, dao.getAll().size());
        dao.delete(activity);
        assertEquals(3, dao.getAll().size());
    }

    private Activity createTestActivity(){
        Activity activity = new Activity();
        activity.setName("New Activity");
        activity.setDescription("New Description");
        return activity;
    }
}
