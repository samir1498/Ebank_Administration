/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.registerRequest.controller;

import com.email_sender.bean.Email;
import com.email_sender.util.EmailUtility;
import com.registerRequest.bean.RegisterRequest;
import com.registerRequest.dao.RegisterRequestDao;
import com.registerRequest.dao.RegisterRequestDaoImpl;
import connexion.util.SingletonConnection;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Samir
 */
@WebServlet(name = "RegisterRequestsServlet", urlPatterns = {"/RegisterRequests"})
public class RegisterRequestsServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code>
   * method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a
   * servlet-specific error occurs
   * @throws IOException if an I/O error
   * occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    LoadRegisterRequests(request, response);
    request.getRequestDispatcher("Views/Admin/RegisterRequests.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String approuve = request.getParameter("approuve");
    String reject = request.getParameter("reject");
    int id = Integer.parseInt(request.getParameter("id"));
    String path = request.getParameter("path");
    if (approuve != null) {
      try {
        RegisterRequestDao RegisterRequestDao = new RegisterRequestDaoImpl();

        //add email sending
        RegisterRequest user = RegisterRequestDao.getRegisterRequest(id);
        
        RegisterRequestDao.ApprouveRegisterRequest(id);
        
        SingletonConnection.getConnection().commit();
        
        LoadRegisterRequests(request, response);
        request.getRequestDispatcher("Views/Admin/RegisterRequests.jsp").forward(request, response);

        try {
          Email email = new Email();
          email.setRecipient(user.getEmail());

          email.setSubject("Request Approuved ");
          String content = "Request Approuved ";
          email.setContent(content);
          EmailUtility.sendEmail(email);

        } catch (MessagingException ex) {
          Logger.getLogger(RegisterRequestsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

      } catch (SQLException ex) {
        Logger.getLogger(RegisterRequestsServlet.class.getName()).log(Level.SEVERE, null, ex);
      }

    } else if (reject != null) {

      try {
        RegisterRequestDao RegisterRequestDao = new RegisterRequestDaoImpl();
        //add email sending
        RegisterRequest user = RegisterRequestDao.getRegisterRequest(id);
        RegisterRequestDao.RemoveRegisterRequest(id);

        //add email sending
        //check if file exists
        File file = new File(path);
        if (file.exists()) {
          FileUtils.deleteDirectory(file);
          SingletonConnection.getConnection().commit();
          LoadRegisterRequests(request, response);
          request.getRequestDispatcher("Views/Admin/RegisterRequests.jsp").forward(request, response);

        }

        Email email = new Email();
        email.setRecipient(user.getEmail());

        email.setSubject("Request rejected ");
        String content = "Request rejected ";
        email.setContent(content);

        try {

          EmailUtility.sendEmail(email);

        } catch (MessagingException ex) {
          Logger.getLogger(RegisterRequestsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

      } catch (SQLException ex) {
        Logger.getLogger(RegisterRequestsServlet.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }

  public void LoadRegisterRequests(HttpServletRequest request, HttpServletResponse response) {
    RegisterRequestDao RegisterRequestDao = new RegisterRequestDaoImpl();

    List<RegisterRequest> registerRequests = new LinkedList();

    registerRequests.addAll(RegisterRequestDao.getRegisterRequests());

    for (int i = 0; i < registerRequests.size(); i++) {
      RegisterRequest n = registerRequests.get(i);
      List<String> l = new LinkedList();
      File file = new File(n.getDocumentsPath());
      if (file.exists()) {
        for (File child : file.listFiles(File::isFile)) {
          l.add(n.getDocumentsPath() + File.separator + child.getName());
        }
        n.setFiles(l);
      }
    }

    request.getSession().setAttribute("rq", registerRequests);
  }
}
