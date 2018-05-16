import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.ActivityInsert;
import com.epam.timetracking.mvc.model.DbConnectionHandler;
import com.epam.timetracking.mvc.model.dao.ActivityDao;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 16.05.2018.
 */
public class DbTest {
    private static DaoManager manager;

    @BeforeClass
    public static void initDb(){
        manager = DaoManager.getInstance();
    }

    @Test
    public void testInsertActivity(){
        GeneralCommand insert = (ActivityInsert)new ActivityInsert();
        when(TestSuite.request.getParameter("select")).thenReturn(Constants.ACTIVITIES);
        when(TestSuite.request.getParameter("name")).thenReturn("test");
        when(TestSuite.request.getParameter("description")).thenReturn("description");
        insert.execute(TestSuite.request, TestSuite.response);
        List<Activity> activities = (List<Activity>) TestSuite.attributes.get("Activities");
        assertEquals(1, activities.size());
    }

    @Test
    public void selectAllActTest(){
        ActivityDao activityDao = (ActivityDao)manager.getDao("ACTIVITY");
        List<Activity> activities = activityDao.getAll();
        System.out.println(activities.size());
        assertTrue(activities.size() == 1);
    }

    private Activity createActivity(){
        Activity activity = new Activity();
        activity.setName("First");
        activity.setDescription("description");
        return activity;
    }
}
