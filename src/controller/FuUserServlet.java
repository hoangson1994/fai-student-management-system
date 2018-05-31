package controller;

import com.google.gson.JsonSyntaxException;
import customModel.FuUserModel;
import design_java_rest.RESTFactory;
import design_java_rest.RESTGeneralError;
import design_java_rest.RESTGeneralSuccess;
import design_java_rest.entity.RESTDocumentSingle;
import design_java_rest.entity.RESTError;
import entity.FuUser;
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

public class FuUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RESTDocumentSingle documentSingle = RESTDocumentSingle.getInstanceFromRequest(req);
            FuUser user = documentSingle.getData().getInstance(FuUser.class);

            FqlModel<FuUser> model = FqlService.getModel("fu_user");
            boolean af = model.insertOne(user);
            RESTFactory.make(RESTGeneralSuccess.CREATED).putData(user).doResponse(resp);
            return;
        } catch (JsonSyntaxException | IllegalAccessException | SQLException e) {
            RESTFactory.make(RESTGeneralError.BAD_REQUEST).putErrors(RESTGeneralError.BAD_REQUEST.code(), "Format Data invalid", e.getMessage()).doResponse(resp);
            return;
        }
    }
}
