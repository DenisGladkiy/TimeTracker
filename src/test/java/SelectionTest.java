import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.ActivityInsert;
import com.epam.timetracking.mvc.controller.command.executors.ActivitySelect;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Denis on 16.05.2018.
 */
@RunWith(Parameterized.class)
public class SelectionTest {

    private String select;
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    public SelectionTest(String select){
        this.select = select;
    }

    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[] { Constants.ACTIVITIES,
                                Constants.ACTIVITIES_BY_USER,
                                Constants.ADDED_ACTIVITIES,
                                Constants.COMPLETED_ACTIVITIES,
                                Constants.REMOVED_ACTIVITIES,
                                Constants.USER_INDEX};
    }

    @BeforeClass
    public static void init(){
        request = mock(HttpServletRequest.class);
        DbInitializer.initializeActivities(request);
    }

    @AfterClass
    public static void clear(){
        DbInitializer.clearData();
    }

    @Test
    public void testCommands(){
        when(request.getParameter("select")).thenReturn(select);
        when(request.getParameter("userId")).thenReturn("1");
        assertEquals(new ActivitySelect().execute(request, response),select);
        assertTrue(TestSuite.testSession.get("Activities") instanceof ArrayList);
    }
}
