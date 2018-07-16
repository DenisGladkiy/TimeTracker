import com.denis.timetracking.mvc.model.dao.ActivityDao;
import com.denis.timetracking.mvc.model.dao.DaoFactory;
import com.denis.timetracking.mvc.model.entity.Activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denys_Hladkyi on 7/16/2018.
 */
public class MainTest {
    public static void main(String[] args) {
        ActivityDao dao = (ActivityDao) new DaoFactory().getDao("ACTIVITY");
        dao.openCurrentSession();
        System.out.println("all = " + dao.getAll());
        System.out.println("actual = " + dao.getActual());
        System.out.println("add request = " + dao.getAdded());
        System.out.println("remove request = " + dao.getRemoved());
        System.out.println("completed = " + dao.getCompleted());
        System.out.println("By user id = " + dao.getByUserId(20));
        System.out.println("By activity id = " + dao.getById(102));
        System.out.println("Is exist = " + dao.isExist(102));
        dao.closeCurrentSession();
        //dao.insert(createActivity());
        dao.openCurrentSession();
        //dao.update(createActivity());
        //System.out.println("all after insert = " + dao.getAll());
        //dao.delete(createActivity());
        dao.acceptActivities(createActivityList());
        dao.closeCurrentSession();
        dao.closeSessionFactory();
    }

    private static Activity createActivity(){
        Activity activity = new Activity();
        //activity.setId(104);
        activity.setName("aaaaaaaa");
        activity.setDescription("bbbbbbbbbb");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            activity.setCreationDate(format.parse("2018-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activity.setAddRequest(false);
        activity.setRemoveRequest(false);
        activity.setCompleted(false);
        return activity;
    }

    private static List<Activity> createActivityList(){
        List<Activity> activities = new ArrayList<>();
        activities.add(createActivity());
        activities.add(createActivity());
        activities.get(0).setId(105);
        activities.get(0).setAddRequest(false);
        activities.get(1).setId(106);
        activities.get(1).setAddRequest(false);
        return activities;
    }
}
