import com.denis.timetracking.mvc.controller.command.GeneralCommand;
import com.denis.timetracking.mvc.controller.command.executors.ActivityInsert;
import com.denis.timetracking.mvc.controller.command.executors.UserInsert;
import com.denis.timetracking.mvc.model.DbConnectionHandler;
import com.denis.timetracking.utils.Constants;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 17.05.2018.
 */
public class TestInitializer {
    Map testSession;
    HttpSession session;
    private DbConnectionHandler dbHandler;
    private Connection connection;
    private HttpServletResponse response;

    void initializeTest(){
        initializeTestConnection();
        initializeTestSession();
    }

    void initializeData(HttpServletRequest request){
        connection = dbHandler.getConnection();
        initializeUsers(request);
        initializeActivities(request);
    }

    private void initializeActivities(HttpServletRequest request){
        when(request.getSession()).thenReturn(session);
        GeneralCommand insert = new ActivityInsert();
        when(request.getParameter("select")).thenReturn(Constants.ACTIVITIES);
        when(request.getParameter("name")).thenReturn("test");
        when(request.getParameter("description")).thenReturn("description");
        when(request.getParameter("userId")).thenReturn("2");
        insert.execute(request, response);
        when(request.getParameter("userId")).thenReturn("0");
        when(request.getParameter("deadLine")).thenReturn("2018-06-06");
        when(request.getParameter("removed")).thenReturn("true");
        insert.execute(request, response);
        when(request.getParameter("removed")).thenReturn("false");
        when(request.getParameter("added")).thenReturn("true");
        insert.execute(request, response);

    }

    private void initializeUsers(HttpServletRequest request){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("firstName")).thenReturn("Ivan");
        when(request.getParameter("lastName")).thenReturn("Ivanov");
        when(request.getParameter("email")).thenReturn("ivan@q.w");
        when(request.getParameter("pass")).thenReturn("1234qwer@");
        when(request.getParameter("role")).thenReturn("ADMIN");
        GeneralCommand userInsert = new UserInsert();
        userInsert.execute(request, response);
        when(request.getParameter("email")).thenReturn("ivan@a.b");
        when(request.getParameter("role")).thenReturn("USER");
        userInsert.execute(request, response);
        when(request.getParameter("email")).thenReturn("ivan@z.x");
        userInsert.execute(request, response);
    }

    private void initializeTestConnection(){
        dbHandler = new DbConnectionHandler("testdb.properties");
    }

    private void initializeTestSession(){
        testSession = new HashMap();
        session = mock(HttpSession.class);
        when(session.getAttribute(anyString())).thenAnswer(aInvocation -> {
            String key = (String) aInvocation.getArguments()[0];
            return testSession.get(key);
        });
        Mockito.doAnswer(aInvocation -> {
            String key = (String) aInvocation.getArguments()[0];
            Object value = aInvocation.getArguments()[1];
            testSession.put(key, value);
            return null;
        }).when(session).setAttribute(anyString(), anyObject());
    }

    private void closeTestConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clearData(){
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            statement.executeUpdate("TRUNCATE activities");
            statement.executeUpdate("TRUNCATE users");
        }catch (SQLException e){
            e.printStackTrace();
        }
        closeTestConnection();
    }
}
