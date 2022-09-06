/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Client.dao;

import com.Client.bean.Client;
import com.login.dao.LoginDaoImpl;
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
public class ClientDaoImpl implements ClientDao {

  static Connection connexion = SingletonConnection.getConnection();

  String getClientByUsernameQuery = "SELECT id, username,"
          + " registrationdate, phonenumber, email,"
          + " adresse, clientstate, clienttype, profile_pic_path, fullname"
          + " FROM public.client where username=?;";

  String getClientByIdQuery = "SELECT id, username,"
          + " registrationdate, phonenumber, email,"
          + " adresse, clientstate, clienttype, documentspath, profile_pic_path, fullname"
          + " FROM public.client where id=?;";

  String getClientByAccountIdQuery = "SELECT c.id, username,"
          + " registrationdate, phonenumber, email,"
          + " adresse, clientstate, clienttype, documentspath, fullname, profile_pic_path"
          + " FROM public.client c join account a on c.id = a.ownerid where a.id = ?;";

  String getClientsQuery = "SELECT id, username,"
          + " registrationdate::date, phonenumber, email,"
          + " adresse, clienttype, fullname, documentspath, nationality, birthdate, clientstate, profile_pic_path"
          + " FROM public.client where clientstate<>'new' order by id asc;";

  String ChangeStateQuery = "Update client set clientstate = ? where id = ?";

  @Override
  public Client getClientByUserName(Client client) {
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(getClientByUsernameQuery);
      preparedStatement.setString(1, client.getUsername());

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));

        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
        client.setFullName(rs.getString("fullname"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public Client getClientById(int id) {

    Client client = new Client();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(getClientByIdQuery);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {

        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));
        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
        client.setFullName(rs.getString("fullname"));
        client.setDocumentsPath(rs.getString("documentspath"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public Client getClientByAccountId(int id) {

    Client client = new Client();

    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(getClientByAccountIdQuery);
      preparedStatement.setInt(1, id);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        client.setId(rs.getInt("id"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));

        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setDocumentsPath(rs.getString("documentspath"));
        client.setFullName(rs.getString("fullname"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
      }

    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
    return client;
  }

  @Override
  public List<Client> getClients() {

    List<Client> l = new LinkedList();
    try {

      PreparedStatement preparedStatement = connexion.prepareStatement(getClientsQuery);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setDate(rs.getString("registrationdate"));
        client.setUsername(rs.getString("username"));
        client.setRegistrationDate(rs.getString("registrationdate"));
        client.setPhoneNumber(rs.getString("phonenumber"));

        client.setEmail(rs.getString("email"));
        client.setAdress(rs.getString("adresse"));
        client.setClientState(rs.getString("clientstate"));
        client.setClientType(rs.getString("clienttype"));
        client.setProfilePicturePath(rs.getString("profile_pic_path"));
        client.setFullName(rs.getString("fullname"));
        client.setBirthDate(rs.getString("birthdate"));
        client.setNationality(rs.getString("nationality"));
        l.add(client);
      }
    } catch (SQLException e) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, e);

    }
    return l;
  }

  @Override
  public String ChangeClientState(String state, int id) {
    
    try {
      PreparedStatement preparedStatement = connexion.prepareStatement(ChangeStateQuery);
      preparedStatement.setString(1, state);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(ClientDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
      return "FAILED";
    }

    return "SUCCES";
  }

}
