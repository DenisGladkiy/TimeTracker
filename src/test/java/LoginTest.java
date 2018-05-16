import com.epam.timetracking.mvc.controller.command.executors.Login;
import com.epam.timetracking.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Denis on 16.05.2018.
 */
public class LoginTest {

    @Test
    public void testAdminLogin(){
        when(TestSuite.request.getParameter("login")).thenReturn("ivan@ukr.net");
        when(TestSuite.request.getParameter("password")).thenReturn("1234");
        assertEquals(Constants.ADMIN_INDEX, new Login().execute(TestSuite.request, TestSuite.response));
        assertNotNull(TestSuite.attributes.get("User"));
    }

    @Test
    public void testUserLogin(){
        when(TestSuite.request.getParameter("login")).thenReturn("alex@a.b");
        when(TestSuite.request.getParameter("password")).thenReturn("alex");
        assertEquals(Constants.USER_INDEX, new Login().execute(TestSuite.request, TestSuite.response));
        assertNotNull(TestSuite.attributes.get("Activities"));
        assertNotNull(TestSuite.attributes.get("User"));
    }
}
