package controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.cmd.Query;
import design_java_rest.RESTFactory;
import design_java_rest.RESTGeneralError;
import design_java_rest.RESTGeneralSuccess;
import design_java_rest.entity.RESTDocumentSingle;
import design_java_rest.entity.RESTError;
import entity.Student;
import model.mysql.FqlModel;
import model.mysql.FqlService;
import utility.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        try {
//            RESTDocumentSingle documentSingle = RESTDocumentSingle.getInstanceFromRequest(req);
//            Student student = documentSingle.getData().getInstance(Student.class);
//            student.setId(System.currentTimeMillis());
//            student.setCreatedAt(System.currentTimeMillis());
//            student.setUpdatedAt(System.currentTimeMillis());
//            student.setStatus(1);
//            List<RESTError> listErrors = Validator.getInstance().validateStudent(student);
//            if (listErrors.size() > 0) {
//                RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(listErrors).doResponse(resp);
//                return;
//            }
//            if (Validator.checkRollnumerExist(student.getRollNumber())) {
//                RESTFactory.make(RESTGeneralError.CONFLICT).putErrors(RESTGeneralError.CONFLICT.code(), "RollNumber Conflict", "RollNUmber existed").doResponse(resp);
//                return;
//            }
//            ofy().save().entity(student).now();
//            RESTFactory.make(RESTGeneralSuccess.CREATED).putData(student).doResponse(resp);
//            return;
//        } catch (JsonSyntaxException e) {
//            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Format Data invalid", e.getMessage()).doResponse(resp);
//            return;
//        }

        FqlService.connect("jdbc:mysql://35.224.127.210/fpt_university?user=focus2&password=hoang123&useSSL=false");
        FqlModel<Student> studentM = FqlService.getModel("student");
        try {
            studentM.insertOne(new Student(System.currentTimeMillis(), "A002", "Hoang", 2, "a", "b", "c", System.currentTimeMillis(), "a", System.currentTimeMillis(), System.currentTimeMillis(), 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path;
        if (req.getPathInfo() == null) path = "";
        else path = req.getPathInfo().substring(1);

        if (path.equals("")) {
            getMulti(req, resp);
        } else {
            getSingle(resp, path);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getIdFromRequest(req, resp);
        if(id == 0){
            return;
        }
        Student student = ofy().load().type(Student.class).id(id).now();
        if (student == null) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Not found", "Student not exist or deleted").doResponse(resp);
            return;
        }
        try {
            RESTDocumentSingle documentSingle = RESTDocumentSingle.getInstanceFromRequest(req);
            Student st = documentSingle.getData().getInstance(Student.class);
            st.setId(id);
            st.setCreatedAt(student.getCreatedAt());
            st.setUpdatedAt(System.currentTimeMillis());
            st.setStatus(1);
            List<RESTError> listErrors = Validator.getInstance().validateStudent(student);
            if (listErrors.size() > 0) {
                RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(listErrors).doResponse(resp);
                return;
            }
            ofy().save().entity(st).now();
            RESTFactory.make(RESTGeneralSuccess.OK).putData(st).doResponse(resp);
            return;
        } catch (JsonSyntaxException e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Format Data Invalid", e.getMessage()).doResponse(resp);
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getIdFromRequest(req, resp);
        if(id == 0){
            return;
        }
        Student student = ofy().load().type(Student.class).id(id).now();
        if (student == null) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Not Found", "Student not exist or deleted").doResponse(resp);
            return;
        }
        student.setStatus(0);
        ofy().save().entity(student).now();
        RESTFactory.make(RESTGeneralSuccess.OK).putData(student).doResponse(resp);
    }

    private void getMulti(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int limit = 10, page = 1, totalItem, totalPage;
        String temp;
        try {
            if ((temp = req.getParameter("page")) != null) page = Integer.parseInt(temp);
            if ((temp = req.getParameter("limit")) != null) limit = Integer.parseInt(temp);
            Query<Student> query = ofy().load().type(Student.class).filter("status", 1);
            totalItem = query.count();
            totalPage = (int) Math.ceil((double) totalItem / limit);
            List<Student> lStudent = query.limit(limit).offset((page - 1) * limit).list();

            RESTFactory.make(RESTGeneralSuccess.OK).putData(lStudent).putMeta("totalPage", totalPage)
                    .putMeta("totalItem", totalItem).putMeta("page", page).putMeta("limit", limit).doResponse(resp);
            return;
        } catch (NumberFormatException e) {
            System.err.println("Failed when parse the parameter!");
        }
    }

    private void getSingle(HttpServletResponse resp, String path) throws IOException {
        try {
            Student student = ofy().load().type(Student.class).id(Long.parseLong(path)).now();
            if (student == null) {
                RESTFactory.make(RESTGeneralError.NOT_FOUND).putErrors(RESTGeneralError.NOT_FOUND.code(), "Student not exist or deleted", "").doResponse(resp);
                return;
            }
            RESTFactory.make(RESTGeneralSuccess.OK).putData(student).doResponse(resp);
            return;
        } catch (NumberFormatException e) {
            RESTFactory.make(RESTGeneralError.NOT_FOUND).putErrors(RESTGeneralError.NOT_FOUND.code(), "Not Found", "Not Found").doResponse(resp);
            return;
        }
    }

    private long getIdFromRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (req.getPathInfo() == null) {
                RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Bad Request", "Bad Requset").doResponse(resp);
                return 0;
            }
            return Long.parseLong(req.getPathInfo().substring(1));
        } catch (NumberFormatException e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Not Found", "Not found").doResponse(resp);
            return 0;
        }
    }
}
