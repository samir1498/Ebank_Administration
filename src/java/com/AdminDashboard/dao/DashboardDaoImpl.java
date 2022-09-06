/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AdminDashboard.dao;

import com.Client.dao.ClientDaoImpl;
import connexion.util.SingletonConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class DashboardDaoImpl implements DashboardDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public Map<String, Integer> NewClientsCount() {
    String query = "SELECT TO_CHAR(registrationdate,'MM/01/YYYY') AS Month_Year, "
            + "      count(*) as number_of_new_clients"
            + "  FROM client"
            + "  where clientstate='active' and\n"
            + "   registrationdate >= date_trunc('year', CURRENT_TIMESTAMP)\n"
            + " AND registrationdate < (date_trunc('year'::text, CURRENT_TIMESTAMP) + '1 year'::interval)\n"
            + " GROUP BY TO_CHAR(registrationdate,'MM/01/YYYY')\n"
            + " order by TO_CHAR(registrationdate,'MM/01/YYYY');";

    Map<String, Integer> clientsCount = new HashMap();
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        clientsCount.put(rs.getString("Month_Year"), rs.getInt("number_of_new_clients"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return clientsCount;
  }

  @Override
  public BigDecimal getOutcome() {
    String query = "select sum(convertedamoutlocalcurrency) as outcome\n" +
"            from transactions where (type_transaction like '%(external)' or type_transaction like '%(International)')";

    BigDecimal Outcome = BigDecimal.ZERO;
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        Outcome = rs.getBigDecimal("outcome");
        return Outcome;
      }

    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Outcome;
  }

  @Override
  public BigDecimal getIncome() {
    String query = "select sum(convertedamoutlocalcurrency)*1.35 as income"
            + " from transactions where (type_transaction like '%(external)' or type_transaction like '%(International)');";

    BigDecimal Income = BigDecimal.ZERO;
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        Income = rs.getBigDecimal("income");
        return Income;
      }

    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Income;
  }

  @Override
  public Map<String, BigDecimal> TransactionsSatas() {
    String query = "\n"
            + "SELECT TO_CHAR(date_transaction,'MM/01/YYYY') AS Month_Year,\n"
            + "      sum(convertedamoutlocalcurrency) as amount_sum\n"
            + "  FROM transactions\n"
            + "  where \n"
            + "   date_transaction >= date_trunc('year', CURRENT_TIMESTAMP)\n"
            + " AND date_transaction < (date_trunc('year'::text, CURRENT_TIMESTAMP) + '1 year'::interval)\n"
            + " GROUP BY TO_CHAR(date_transaction,'MM/01/YYYY')\n"
            + " order by TO_CHAR(date_transaction,'MM/01/YYYY');";

    Map<String, BigDecimal> transactionStats = new HashMap();
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        transactionStats.put(rs.getString("Month_Year"), rs.getBigDecimal("amount_sum"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(DashboardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return transactionStats;
  }

}
