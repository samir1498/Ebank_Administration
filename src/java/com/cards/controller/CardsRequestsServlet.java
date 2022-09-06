/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cards.controller;

import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.cardsRequests.bean.Card;
import com.cardsRequests.dao.CardDao;
import com.cardsRequests.dao.CardDaoImpl;
import com.email_sender.bean.Email;
import com.email_sender.util.EmailUtility;
import com.registerRequest.bean.RegisterRequest;
import com.registerRequest.controller.RegisterRequestsServlet;
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
@WebServlet(name = "CardsRequestsServlet", urlPatterns = {"/CardsRequests"})
public class CardsRequestsServlet extends HttpServlet {

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

    AccountDao accountdao = new AccountDaoImpl();
    List<Card> l = accountdao.listCardsRequests();
    request.getSession().setAttribute("CardRq", l);
    request.getRequestDispatcher("Views/Admin/CardsRequests.jsp").forward(request, response);

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
    String approuve = request.getParameter("approuve");
    String reject = request.getParameter("reject");
    String cardnumber = request.getParameter("cardnumber");

    if (approuve != null) {
      try {
        CardDao carddao = new CardDaoImpl();

        //add email sending
        Card card = carddao.getCardRequest(cardnumber);
        Client user = card.getClient();
        System.out.println("doPost card" + card);
        System.out.println("doPost " + user);

        carddao.ApprouveCard(cardnumber);
        SingletonConnection.getConnection().commit();

        try {
          Email email = new Email();
          email.setRecipient(user.getEmail());

          email.setSubject("Card Request Approuved ");
          String content = "Card Request Approuved ";
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
        CardDao carddao = new CardDaoImpl();
        //add email sending
        Card card = carddao.getCardRequest(cardnumber);
        Client user = card.getClient();

        carddao.RejectCard(cardnumber);

        //add email sending
        //check if file exists
        SingletonConnection.getConnection().commit();

        Email email = new Email();
        email.setRecipient(user.getEmail());

        email.setSubject("Card Request rejected ");
        String content = "Card Request rejected ";
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
    AccountDao accountdao = new AccountDaoImpl();
    List<Card> l = accountdao.listCardsRequests();
    request.getSession().setAttribute("CardRq", l);
    LoadRegisterRequests(request, response);
    request.getRequestDispatcher("Views/Admin/CardsRequests.jsp").forward(request, response);
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
