/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.registerRequest.controller;

import com.registerRequest.bean.RegisterRequest;
import com.registerRequest.dao.RegisterRequestDao;
import com.registerRequest.dao.RegisterRequestDaoImpl;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samir
 */
@WebServlet(name = "RegisterRequestCounter", urlPatterns = {"/RegisterRequestCounter"})
public class RegisterRequestCounter extends HttpServlet {

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
    HttpSession session = request.getSession();
    List<RegisterRequest> rq = (List<RegisterRequest>) session.getAttribute("rq");
    if (rq == null) {
      LoadRegisterRequests(request, response);
    }
    rq = (List<RegisterRequest>) session.getAttribute("rq");
    RegisterRequestDao RegisterRequestDao = new RegisterRequestDaoImpl();
    int counter = RegisterRequestDao.RegisterRequestsCounter();
    int newRequests = counter - rq.size();
    response.setContentType("text/plain");
    response.getWriter().write(String.valueOf(newRequests));
  }

  /**
   * Handles the HTTP <code>POST</code>
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

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
