/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registerRequest.dao;

import com.login.dao.LoginDaoImpl;
import com.registerRequest.bean.RegisterRequest;
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
public class RegisterRequestDaoImpl implements RegisterRequestDao {

  static Connection connexion = SingletonConnection.getConnection();

  @Override
  public List<RegisterRequest> getRegisterRequests() {
    String query = "SELECT id, username,"
            + " registrationdate::date, phonenumber, email,"
            + " adresse, clienttype, fullname, documentspath, nationality, birthdate"
            + " FROM public.client where clientstate='new' order by id asc;";

    List<RegisterRequest> rq = new LinkedList();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setId(rs.getInt("id"));
        RegisterRequest.setUsername(rs.getString("username"));
        RegisterRequest.setDate(rs.getString("registrationdate"));
        RegisterRequest.setPhoneNumber(rs.getString("phonenumber"));

        RegisterRequest.setEmail(rs.getString("email"));
        RegisterRequest.setAdress(rs.getString("adresse"));
        RegisterRequest.setClientType(rs.getString("clienttype"));
        RegisterRequest.setDocumentsPath(rs.getString("documentspath"));
        RegisterRequest.setName(rs.getString("fullname"));

        RegisterRequest.setNationality(rs.getString("nationality"));
        RegisterRequest.setBirthDate(rs.getString("birthdate"));

        rq.add(RegisterRequest);
      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rq;
  }

  @Override
  public String ApprouveRegisterRequest(int id) {
    String query = "update client set clientstate='active' where id =?";

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(RegisterRequestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Failed";
    }

    return "SUCCES";
  }

  @Override
  public String RemoveRegisterRequest(int id) {
    String query = "delete from client where id =?";

    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      Logger.getLogger(RegisterRequestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "Failed";
    }

    return "SUCCES";
  }

  @Override
  public int RegisterRequestsCounter() {
    String query = "select count(*) as newrequests from client where clientstate='new'";
    int counter = -1;
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(query);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        counter = rs.getInt("newrequests");
      }

    } catch (SQLException ex) {
      Logger.getLogger(RegisterRequestDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return counter;
    }

    return counter;
  }

  @Override
  public RegisterRequest getRegisterRequest(int id) {
    String query = "SELECT id, username,"
            + " registrationdate::date, phonenumber, email,"
            + " adresse, clienttype, fullname, documentspath, nationality, birthdate"
            + " FROM public.client where clientstate='new' and id=?;";

    RegisterRequest RegisterRequest = new RegisterRequest();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        RegisterRequest.setId(rs.getInt("id"));
        RegisterRequest.setUsername(rs.getString("username"));
        RegisterRequest.setDate(rs.getString("registrationdate"));
        RegisterRequest.setPhoneNumber(rs.getString("phonenumber"));

        RegisterRequest.setEmail(rs.getString("email"));
        RegisterRequest.setAdress(rs.getString("adresse"));
        RegisterRequest.setClientType(rs.getString("clienttype"));
        RegisterRequest.setDocumentsPath(rs.getString("documentspath"));
        RegisterRequest.setName(rs.getString("fullname"));

        RegisterRequest.setNationality(rs.getString("nationality"));
        RegisterRequest.setBirthDate(rs.getString("birthdate"));

      }

    } catch (SQLException ex) {
      Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return RegisterRequest;
  }

}
