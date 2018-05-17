import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.Login;
import com.epam.timetracking.mvc.controller.command.executors.UserDelete;
import com.epam.timetracking.mvc.controller.command.executors.UserInsert;
import com.epam.timetracking.mvc.controller.command.executors.UserSelect;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.mvc.model.entity.User;
import com.epam.timetracking.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 16.05.2018.
 */
public class UsersTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DaoManager manager;
    private UserDao dao;

    @Before
    public void init(){
        request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(TestSuite.session);
        manager = DaoManager.getInstance();
        dao = (UserDao) manager.getDao("USER");
        DbInitializer.initializeUsers(request);
    }

    @After
    public void clearData(){
        DbInitializer.clearData();
    }

    @Test
    public void testInsert(){
        assertEquals(3, dao.getAll().size());
        insertUser();
        assertEquals(4, dao.getAll().size());
        dao.closeConnection();
    }

    @Test
    public void testDelete(){
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
        assertNotNull(TestSuite.testSession.get("User"));
    }

    @Test
    public void testUserLogin(){
        when(request.getParameter("login")).thenReturn("ivan@a.b");
        when(request.getParameter("password")).thenReturn("1234");
        assertEquals(Constants.USER_INDEX, new Login().execute(request, response));
        assertNotNull(TestSuite.testSession.get("Activities"));
        assertNotNull(TestSuite.testSession.get("User"));
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
        return (List<User>) TestSuite.testSession.get("Users");
    }

    private void deleteUser(){
        when(request.getParameter("id")).thenReturn("3");
        GeneralCommand delete = new UserDelete();
        delete.execute(request, response);
    }
}
