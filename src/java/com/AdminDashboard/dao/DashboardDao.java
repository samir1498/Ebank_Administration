/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.AdminDashboard.dao;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Samir
 */
public interface DashboardDao {
  public Map<String, Integer> NewClientsCount();
  public BigDecimal getOutcome();
  public BigDecimal getIncome();
  public Map<String, BigDecimal> TransactionsSatas();
}
