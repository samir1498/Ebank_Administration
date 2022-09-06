/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cardsRequests.dao;

import com.Account.bean.Account;
import com.Account.dao.AccountDao;
import com.Account.dao.AccountDaoImpl;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.cardsRequests.bean.Card;
import connexion.util.SingletonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class CardDaoImpl implements CardDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public String ApprouveCard(String cardNumber) {
    String query = "update card set cardstate='active' where cardnumber =?";

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, cardNumber);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Failed";
    }

    return "SUCCES";

  }

  @Override
  public String RejectCard(String cardNumber) {
    String query = "delete from card where cardnumber =?";

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, cardNumber);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Failed";
    }

    return "SUCCES";
  }

  @Override
  public Card getCardRequest(String cardnumber) {
    String query = "select "
            + "cardnumber, pin,"
            + " creationdate,"
            + " expirationdate,"
            + " cardstate,"
            + " withdrawlimit,"
            + " holdername, cvv, id, idaccount, to_char(expirationdate,'yy') as year, to_char(expirationdate,'mm') as month "
            + "from card where cardnumber=? and cardstate='new';";
    Card card = new Card();
    AccountDao accountdao = new AccountDaoImpl();
    
    ClientDao clientdao = new ClientDaoImpl();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, cardnumber);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        Client client = clientdao.getClientById(rs.getInt("id"));
        card.setClient(client);
        card.setCardNumber(rs.getString("cardnumber"));
        card.setCardState(rs.getString("cardstate"));
        card.setCreationDate(rs.getString("creationdate"));
        String date = rs.getString("month") + " / " + rs.getString("year");
        card.setExpirationDate(date);
        card.setHolderName(rs.getString("holdername"));
        card.setPIN(rs.getString("pin"));
        card.setWithdrawLimit(rs.getBigDecimal("withdrawlimit"));
        Account account = accountdao.getAccountById(rs.getInt("idaccount"));
        card.setAccount(account);
        card.setCVV(rs.getInt("cvv"));

      }
    } catch (SQLException e) {
      Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, e);

      try {
        if (connexion != null) {

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return card;
  }
}
