import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.ActivityInsert;
import com.epam.timetracking.mvc.controller.command.executors.UserInsert;
import com.epam.timetracking.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 17.05.2018.
 */
public class DbInitializer {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    static void initializeActivities(HttpServletRequest request){
        when(request.getSession()).thenReturn(TestSuite.session);
        GeneralCommand insert = new ActivityInsert();
        when(request.getParameter("select")).thenReturn(Constants.ACTIVITIES);
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("description")).thenReturn("description");
        insert.execute(request, response);
        when(request.getParameter("deadLine")).thenReturn("2018-06-06");
        insert.execute(request, response);
        when(request.getParameter("added")).thenReturn("true");
        insert.execute(request, response);
    }

    static void initializeUsers(HttpServletRequest request){
        when(request.getSession()).thenReturn(TestSuite.session);
        when(request.getParameter("firstName")).thenReturn("Ivan");
        when(request.getParameter("lastName")).thenReturn("Ivanov");
        when(request.getParameter("email")).thenReturn("ivan@q.w");
        when(request.getParameter("pass")).thenReturn("1234");
        when(request.getParameter("role")).thenReturn("ADMIN");
        GeneralCommand userInsert = new UserInsert();
        userInsert.execute(request, response);
        when(request.getParameter("email")).thenReturn("ivan@a.b");
        when(request.getParameter("role")).thenReturn("USER");
        userInsert.execute(request, response);
        when(request.getParameter("email")).thenReturn("ivan@z.x");
        userInsert.execute(request, response);
    }

    static void clearData(){
        try {
            TestSuite.clearTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
