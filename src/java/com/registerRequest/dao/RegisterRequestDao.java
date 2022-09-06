/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registerRequest.dao;

import com.registerRequest.bean.RegisterRequest;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface RegisterRequestDao {

  public List<RegisterRequest> getRegisterRequests();

  public RegisterRequest getRegisterRequest(int id);

  public String ApprouveRegisterRequest(int id);

  public String RemoveRegisterRequest(int id);

  public int RegisterRequestsCounter();
}
