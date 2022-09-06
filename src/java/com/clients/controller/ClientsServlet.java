/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.clients.controller;

import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import connexion.util.SingletonConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samir
 */
@WebServlet(name = "ClientsServlet", urlPatterns = {"/Clients"})
public class ClientsServlet extends HttpServlet {

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
    ClientDao clientdao = new ClientDaoImpl();
    String ClientId = request.getParameter("id");
    String Block = request.getParameter("block");
    String unBlock = request.getParameter("unblock");

    if (Block!=null && Block.equals("block")) {
      clientdao.ChangeClientState("Blocked", Integer.parseInt(ClientId));
      try {
        SingletonConnection.getConnection().commit();
      } catch (SQLException ex) {
        Logger.getLogger(ClientsServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else if (unBlock!=null && unBlock.equals("unblock")) {
      clientdao.ChangeClientState("active", Integer.parseInt(ClientId));
      try {
        SingletonConnection.getConnection().commit();
      } catch (SQLException ex) {
        Logger.getLogger(ClientsServlet.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    response.sendRedirect("ClientsList");

  }

}
