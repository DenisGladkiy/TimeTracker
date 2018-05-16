import com.epam.timetracking.mvc.model.DbConnectionHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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
 * Created by Denis on 16.05.2018.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ DbTest.class, SelectionTest.class, LoginTest.class,})
public class TestSuite {

    static HttpServletRequest request;
    static HttpServletResponse response;
    static HttpSession session;
    static Map attributes;
    private  static Connection connection;

    @BeforeClass
    public static void initialize(){
        DbConnectionHandler dbHandler = new DbConnectionHandler("testdb.properties");
        connection = dbHandler.getConnection();
        attributes = new HashMap();
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenAnswer(aInvocation -> {
            String key = (String) aInvocation.getArguments()[0];
            return attributes.get(key);
        });
        Mockito.doAnswer(aInvocation -> {
            String key = (String) aInvocation.getArguments()[0];
            Object value = aInvocation.getArguments()[1];
            attributes.put(key, value);
            return null;
        }).when(session).setAttribute(anyString(), anyObject());
    }

    @AfterClass
    public static void finalizeTest(){
        try(Statement statement = connection.createStatement();) {
            statement.executeUpdate("TRUNCATE activities");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
