import com.epam.timetracking.mvc.controller.command.GeneralCommand;
import com.epam.timetracking.mvc.controller.command.executors.ActivityInsert;
import com.epam.timetracking.mvc.controller.command.executors.ActivitySelect;
import com.epam.timetracking.mvc.model.entity.Activity;
import com.epam.timetracking.utils.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Denis on 16.05.2018.
 */
@RunWith(Parameterized.class)
public class SelectionTest {

    private String select;

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

    @Test
    public void testCommands(){
        when(TestSuite.request.getParameter("select")).thenReturn(select);
        when(TestSuite.request.getParameter("userId")).thenReturn("16");
        assertEquals(new ActivitySelect().execute(TestSuite.request, TestSuite.response),select);
        assertTrue(TestSuite.attributes.get("Activities") instanceof ArrayList);
    }


}
