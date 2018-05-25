import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.controller.command.executors.Login;
import com.denis.timetracking.mvc.controller.command.executors.UserDelete;
import com.denis.timetracking.mvc.controller.command.executors.UserInsert;
import com.denis.timetracking.mvc.controller.command.executors.UserSelect;
import com.denis.timetracking.mvc.model.dao.DaoManager;
import com.denis.timetracking.mvc.model.dao.UserDao;
import com.denis.timetracking.mvc.model.entity.User;
import com.denis.timetracking.utils.Constants;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 16.05.2018.
 */
public class UserCommandsTest {
    private static TestInitializer initializer;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DaoManager manager;
    private UserDao dao;

    @BeforeClass
    public static void initClass(){
        initializer = new TestInitializer();
        initializer.initializeTest();
    }

    @Before
    public void initTest(){
        request = mock(HttpServletRequest.class);
        initializer.initializeData(request);
        manager = DaoManager.getInstance();
        dao = (UserDao) manager.getDao("USER");
    }

    @After
    public void clearData(){
        initializer.clearData();
        dao.closeConnection();
    }

    @Test
    public void testInsert() throws SQLException {
        assertEquals(3, dao.getAll().size());
        insertUser();
        assertEquals(4, dao.getAll().size());
        dao.closeConnection();
    }

    @Test
    public void testDelete() throws SQLException {
        assertEquals(3, dao.getAll().size());
        deleteUser();
        assertEquals(2, dao.getAll().size());
        dao.closeConnection();
    }

    @Test
    public void testAdminLogin(){
        when(request.getParameter("login")).thenReturn("ivan@q.w");
        when(request.getParameter("password")).thenReturn("1234");
        assertEquals(Constants.ADMIN_INDEX, new Login().execute(request, response));
        assertNotNull(initializer.testSession.get("User"));
    }

    @Test
    public void testUserLogin(){
        when(request.getParameter("login")).thenReturn("ivan@a.b");
        when(request.getParameter("password")).thenReturn("1234");
        assertEquals(Constants.USER_INDEX, new Login().execute(request, response));
        assertNotNull(initializer.testSession.get("Activities"));
        assertNotNull(initializer.testSession.get("User"));
    }

    @Test
    public void testSelect(){
        List<User> users = selectAllUsers();
        assertEquals(3, users.size());
    }

    private void insertUser(){
        when(request.getParameter("email")).thenReturn("test@z.x");
        GeneralCommand userInsert = new UserInsert();
        userInsert.execute(request, response);
    }

    private List<User> selectAllUsers(){
        GeneralCommand selectUsers = new UserSelect();
        selectUsers.execute(request, response);
        return (List<User>) initializer.testSession.get("Users");
    }

    private void deleteUser(){
        when(request.getParameter("id")).thenReturn("3");
        GeneralCommand delete = new UserDelete();
        delete.execute(request, response);
    }
}
