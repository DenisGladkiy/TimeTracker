import com.denis.timetracking.exception.IncorrectInputException;
import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.mvc.model.entity.UserRoleEnum;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Denis on 21.05.2018.
 */
public class UserDaoTest {

    private DaoFactory manager;
    private UserDao dao;
    private HttpServletRequest request;
    private static TestInitializer initializer;
    private HttpServletResponse response;
    private List<User> users;

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
        dao = (UserDao) manager.getDao("USER");
        dao.openCurrentSession();
    }

    @After
    public void clearData(){
        initializer.clearData();
        dao.closeCurrentSession();
        dao.closeConnection();
    }

    @Test
    public void testSelectAll() throws SQLException {
        assertEquals(3, dao.getAll().size());
    }

    @Test
    public void testGetById() throws SQLException, IncorrectInputException {
        User user = dao.getById(1);
        assertNotNull(user);
        assertEquals("ivan@q.w", user.getEmail());
    }

    @Test
    public void testGetByLogin() throws SQLException, IncorrectInputException {
        User user = dao.getByLogin("ivan@q.w");
        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Ignore
    @Test(expected = IncorrectInputException.class)
    public void testIncorrectInput() throws SQLException, IncorrectInputException {
        dao.insert(null);
    }
    @Ignore
    @Test(expected = IncorrectInputException.class)
    public void testIncorrectId() throws SQLException, IncorrectInputException {
        dao.getById(6);
    }

    @Ignore
    @Test(expected = IncorrectInputException.class)
    public void testIncorrectLogin() throws SQLException, IncorrectInputException {
        dao.getByLogin("");
    }

    @Test
    public void testInsertUser() throws SQLException, IncorrectInputException {
        assertEquals(3, dao.getAll().size());
        User user = createTestUser();
        dao.insert(user);
        assertEquals(4, dao.getAll().size());
        assertEquals("Alex", dao.getById(4).getFirstName());
    }

    @Test
    public void testDelete() throws SQLException, IncorrectInputException {
        User user = createTestUser();
        dao.insert(user);
        assertEquals(4, dao.getAll().size());
        dao.delete(user);
        assertEquals(3, dao.getAll().size());
    }

    @Test
    public void testExist() throws SQLException {
        assertTrue(dao.isExist(1));
    }

    private User createTestUser(){
        User user = new User(0, "Alex", "Pushkin");
        user.setEmail("alex@i.ua");
        user.setPassword("qw1234");
        user.setRole(UserRoleEnum.USER);
        return user;
    }


}
