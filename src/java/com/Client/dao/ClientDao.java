/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Client.dao;

import com.Client.bean.Client;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface ClientDao {

  public Client getClientById(int id);

  public Client getClientByUserName(Client client);

  public Client getClientByAccountId(int id);
  
  public List<Client> getClients();
  
  public String ChangeClientState(String state, int id);

}
