import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.*;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 16.05.2018.
 */
public class ActivityTest {
    private DaoManager manager;
    private ActivityDao dao;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void init(){
        request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(TestSuite.session);
        manager = DaoManager.getInstance();
        dao = (ActivityDao) manager.getDao("ACTIVITY");
        DbInitializer.initializeActivities(request);
        DbInitializer.initializeUsers(request);
    }

    @After
    public void clearData(){
        DbInitializer.clearData();
        dao.closeConnection();
    }

    @Test
    public void testInsert(){
        assertEquals(3, dao.getAll().size());
        insertActivity();
        assertEquals(4, dao.getAll().size());
    }

    @Test
    public void testSelect(){
        List<Activity> activities = selectActivities();
        assertEquals(2, activities.size());
    }

    @Test
    public void testDelete(){
        assertEquals(3, dao.getAll().size());
        deleteActivity();
        assertEquals(2, dao.getAll().size());
    }

    @Test
    public void testUpdate(){
        List<Activity> activities = dao.getAll();
        assertEquals("description", activities.get(0).getDescription());
        updateActivity();
        activities = dao.getAll();
        assertEquals("description1", activities.get(0).getDescription());;
    }

    @Test
    public void testAccept(){
        assertEquals(1, dao.getAdded().size());
        acceptActivity();
        assertEquals(0, dao.getAdded().size());
    }

    @Test
    public void testSelectByUser(){
        updateActivity();
        List<Activity> activities = selectByUser();
        assertEquals(1, activities.size());
        assertEquals(1, activities.get(0).getId());
    }

    @Test
    public void testIncorrectFK(){
        when(request.getParameter("userId")).thenReturn("5");
        assertEquals(3, dao.getAll().size());
        insertActivity();
        assertEquals(3, dao.getAll().size());
    }

    private void insertActivity(){
        GeneralCommand insert = new ActivityInsert();
        insert.execute(request, response);
    }

    private List<Activity> selectActivities(){
        when(request.getParameter("select")).thenReturn(Constants.ACTIVITIES);
        GeneralCommand selectActivities = new ActivitySelect();
        selectActivities.execute(request, response);
        return (List<Activity>) TestSuite.testSession.get("Activities");
    }

    private void deleteActivity(){
        GeneralCommand delete = new ActivityDelete();
        when(request.getParameter("id")).thenReturn("2");
        delete.execute(request, response);
    }

    private void updateActivity(){
        GeneralCommand update = new ActivityUpdate();
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("description")).thenReturn("description1");
        when(request.getParameter("userId")).thenReturn("2");
        update.execute(request, response);
    }

    private void acceptActivity(){
        when(request.getParameter("accepted")).thenReturn("3;test");
        GeneralCommand accept = new ActivityAccept();
        accept.execute(request, response);
    }

    private List<Activity> selectByUser(){
        when(request.getParameter("userId")).thenReturn("2");
        when(request.getParameter("select")).thenReturn(Constants.ACTIVITIES_BY_USER);
        GeneralCommand select = new UsersActivities();
        select.execute(request, response);
        return (List<Activity>) TestSuite.testSession.get("Activities");
    }
}
