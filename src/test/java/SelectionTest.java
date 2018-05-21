import com.epam.timetracking.mvc.controller.command.executors.ActivitySelect;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import com.epam.timetracking.mvc.model.dao.UserDao;
import com.epam.timetracking.utils.Constants;
import org.junit.*;
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
    private static TestInitializer initializer;
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
    public static void initClass(){
        initializer = new TestInitializer();
        initializer.initializeTest();
    }

    @Before
    public void initTest(){
        request = mock(HttpServletRequest.class);
        initializer.initializeData(request);
    }

//    @AfterClass
//    public static void finalizeTest(){
//        initializer.closeTestConnection();
//    }

    @After
    public void clearData(){
        initializer.clearData();
    }

    @Test
    public void testSelectCommands(){
        when(request.getParameter("select")).thenReturn(select);
        when(request.getParameter("userId")).thenReturn("1");
        assertEquals(select, new ActivitySelect().execute(request, response));
        assertTrue(initializer.testSession.get("Activities") instanceof ArrayList);
    }
}
