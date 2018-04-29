package com.epam.timetracking;

import com.epam.timetracking.mvc.model.dao.AbstractDao;
import com.epam.timetracking.mvc.model.dao.DaoManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Denis on 29.04.2018.
 */
@WebServlet(name="Time-Tracking", urlPatterns={"/timetracking"})
public class Main extends HttpServlet {
    static Logger logger = Logger.getLogger(Main.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        logger.debug("Testlog");
        DaoManager manager = new DaoManager();
        AbstractDao dao = manager.getDao("Activity");
        logger.debug(dao.getAll());
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            out.println(dao.getAll());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            dao.closeConnection();
        }
    }
}
