/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.AdminDashboard.controller;

import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.AdminDashboard.dao.DashboardDao;
import com.AdminDashboard.dao.DashboardDaoImpl;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samir
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/Dashboard"})
public class DashboardServlet extends HttpServlet {

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
    DashboardDao dashboarddao = new DashboardDaoImpl();
    BigDecimal income = dashboarddao.getIncome();

    BigDecimal outcome = dashboarddao.getOutcome();
    request.getSession().setAttribute("clientsCount", dashboarddao.NewClientsCount());
    request.getSession().setAttribute("TransactionsStats", dashboarddao.TransactionsSatas());
    BigDecimal total;
    if (income == null) {
      income = BigDecimal.ZERO;
    }
    if (outcome == null) {
      outcome = BigDecimal.ZERO;
    }

    total = income.add(outcome);

    request.getSession().setAttribute("income", income);
    BigDecimal income_per;

    if (total.compareTo(BigDecimal.ZERO) == 0) {
      income_per = BigDecimal.ZERO;
    } else {
      income_per = (income.divide(total, MathContext.DECIMAL64)).multiply(new BigDecimal("100"));
    }

    income_per = income_per.setScale(2, RoundingMode.FLOOR);

    request.getSession().setAttribute("income_per", income_per);
    request.getSession().setAttribute("outcome", outcome);

    AccountDao accountdao = new AccountDaoImpl();
    String BankCurrencySymbol = accountdao.getCurrency((String) request.getSession().getAttribute("localcurrency")).getSymbol();
    request.getSession().setAttribute("lc", BankCurrencySymbol);
    request.getRequestDispatcher("Views/Admin/adminDashboard.jsp").forward(request, response);
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

}
