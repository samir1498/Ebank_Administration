/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Account.dao;

import com.Account.bean.Account;
import com.Account.bean.CurrentAccount;
import com.Account.bean.SavingsAccount;
import com.Client.bean.Client;
import com.Client.dao.ClientDao;
import com.Client.dao.ClientDaoImpl;
import com.cardsRequests.bean.Card;
import com.currency.bean.Currency;
import connexion.util.SingletonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samir
 */
public class AccountDaoImpl implements AccountDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public List<Account> listAccountsRequests() {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib, iban"
            + " FROM public.account where accountstate='new';";

    List<Account> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        if (rs.getString("accounttype").equals("saving")) {
          SavingsAccount saving = new SavingsAccount();
          saving.setId(rs.getInt("id"));
          saving.setName(rs.getString("accountname"));
          saving.setBalance(rs.getBigDecimal("balance"));
          saving.setOwnerID(rs.getInt("ownerid"));
          saving.setCurrency(getCurrency(rs.getString("currency")));
          saving.setAccountState(rs.getString("accountstate"));
          saving.setTransferLimit(rs.getBigDecimal("transferlimit"));
          saving.setAccountType(rs.getString("accounttype"));
          saving.setCreationDate(rs.getString("creationdate"));
          saving.setRIB(rs.getString("rib"));
          saving.setIBAN(rs.getString("iban"));

          String savingsQuery = "SELECT saving_goal\n"
                  + "FROM savings_account where id=?;";
          PreparedStatement psSavings = connexion.prepareStatement(savingsQuery);
          psSavings.setInt(1, rs.getInt("id"));
          ResultSet rsSavings = psSavings.executeQuery();
          if (rsSavings.next()) {
            saving.setSavingGoal(rsSavings.getBigDecimal("saving_goal"));
          }
          l.add(saving);
        } else if (rs.getString("accounttype").equals("checking")) {
          CurrentAccount current = new CurrentAccount();

          current.setId(rs.getInt("id"));
          current.setName(rs.getString("accountname"));
          current.setBalance(rs.getBigDecimal("balance"));
          current.setOwnerID(rs.getInt("ownerid"));
          current.setCurrency(getCurrency(rs.getString("currency")));
          current.setAccountState(rs.getString("accountstate"));
          current.setTransferLimit(rs.getBigDecimal("transferlimit"));
          current.setAccountType(rs.getString("accounttype"));
          current.setCreationDate(rs.getString("creationdate"));
          current.setRIB(rs.getString("rib"));
          current.setIBAN(rs.getString("iban"));

          String currentQuery = "SELECT budget, \"period\"\n"
                  + "FROM checking_account where id=?;";

          PreparedStatement psChecking = connexion.prepareStatement(currentQuery);
          psChecking.setInt(1, rs.getInt("id"));
          ResultSet rsChecking = psChecking.executeQuery();
          if (rsChecking.next()) {
            current.setBudget(rsChecking.getBigDecimal("budget"));
            current.setPeriod(rsChecking.getString("period"));
          }

          l.add(current);
        }

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public List<Card> listCardsRequests() {
    String query = "select c.cardnumber, pin, creationdate,\n"
            + "                        expirationdate, cardstate,\n"
            + "                        withdrawlimit,\n"
            + "                        holdername, cvv, id, c.idaccount,\n"
            + "                        to_char(expirationdate,'yy') as year, to_char(expirationdate,'mm') as month ,  \n"
            + "           (\n"
            + "           select count(idaccount) from card\n"
            + "            where \n"
            + "           cardstate='active' and idaccount = c.idaccount\n"
            + "            group by idaccount, c.cardnumber\n"
            + "            ) as cardInAccount\n"
            + "           from card c where cardstate='new';";

    List<Card> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Card card = new Card();
        card.setCardNumber(rs.getString("cardnumber"));
        card.setCardState(rs.getString("cardstate"));
        card.setCreationDate(rs.getString("creationdate"));
        String date = rs.getString("month") + " / " + rs.getString("year");
        card.setExpirationDate(date);
        card.setHolderName(rs.getString("holdername"));
        card.setPIN(rs.getString("pin"));
        card.setWithdrawLimit(rs.getBigDecimal("withdrawlimit"));
        Account account = getAccountById(Integer.parseInt(rs.getString("idaccount")));
        card.setAccount(account);
        ClientDao clientdao = new ClientDaoImpl();
        Client client = clientdao.getClientById(rs.getInt("id"));
        card.setClient(client);
        card.setCVV(rs.getInt("cvv"));
        card.setCardsInAccount(rs.getInt("cardInAccount"));
        l.add(card);

      }
    } catch (SQLException e) {
      Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

      try {
        if (connexion != null) {

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public Currency getCurrency(String code) {
    String query = "SELECT code, name, symbol"
            + " FROM public.currency where code=?;";

    Currency currency = new Currency();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setString(1, code);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        currency.setCode(rs.getString("code"));
        currency.setName(rs.getString("name"));
        currency.setSymbol(rs.getString("symbol"));

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return currency;
  }

  @Override
  public List<Currency> getCurrencies() {
    String query = "SELECT code, name, symbol"
            + " FROM public.currency";

    List<Currency> l = new LinkedList<>();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Currency currency = new Currency();
        currency.setCode(rs.getString("code"));
        currency.setName(rs.getString("name"));
        currency.setSymbol(rs.getString("symbol"));
        l.add(currency);
      }

    } catch (SQLException e) {
      try {
        if (connexion != null) {
          Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e);

          connexion.rollback();
        }
      } catch (SQLException e2) {
        Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, e2);

      }
    }
    return l;
  }

  @Override
  public Account getAccountById(int id) {
    String query = "SELECT id, accountname, balance,"
            + " ownerid, currency, accountstate,"
            + " transferlimit, accounttype,"
            + " creationdate, rib\n"
            + " FROM public.account where id=?;";

    Account compte = new Account();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setLong(1, id);

      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        compte.setId(rs.getInt("id"));
        compte.setName(rs.getString("accountname"));
        compte.setBalance(rs.getBigDecimal("balance"));
        compte.setOwnerID(rs.getInt("ownerid"));
        compte.setCurrency(getCurrency(rs.getString("currency")));
        compte.setAccountState(rs.getString("accountstate"));
        compte.setTransferLimit(rs.getBigDecimal("transferlimit"));
        compte.setAccountType(rs.getString("accounttype"));
        compte.setCreationDate(rs.getString("creationdate"));
        compte.setRIB(rs.getString("rib"));

      }
    } catch (SQLException e) {
      try {
        if (connexion != null) {
          connexion.rollback();
        }
      } catch (SQLException e2) {
      }
    }
    return compte;
  }

}
