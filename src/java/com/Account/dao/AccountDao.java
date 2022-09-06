/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Account.dao;

import com.Account.bean.Account;
import com.cardsRequests.bean.Card;
import com.currency.bean.Currency;
import java.util.List;

/**
 *
 * @author Samir
 */
public interface AccountDao {

  public Account getAccountById(int id);

  public List<Account> listAccountsRequests();

  public List<Card> listCardsRequests();

  public Currency getCurrency(String code);

  public List<Currency> getCurrencies();
}
