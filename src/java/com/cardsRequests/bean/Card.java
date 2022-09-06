/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cardsRequests.bean;

import com.Account.bean.Account;
import com.Client.bean.Client;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Samir
 */
public class Card implements Serializable {

  private String CardNumber;
  private String PIN;
  private String CreationDate;
  private String ExpirationDate;
  private String CardState;
  private BigDecimal WithdrawLimit;
  private String HolderName;
  private Account Account;
  private Client Client; 
  private int cardsInAccount;
  private int CVV;

  public Client getClient() {
    return Client;
  }

  public void setClient(Client Client) {
    this.Client = Client;
  }

  public int getCardsInAccount() {
    return cardsInAccount;
  }

  public void setCardsInAccount(int cardsInAccount) {
    this.cardsInAccount = cardsInAccount;
  }
  public int getCVV() {
    return CVV;
  }

  public void setCVV(int CVV) {
    this.CVV = CVV;
  }

  public Account getAccount() {
    return Account;
  }

  public void setAccount(Account Account) {
    this.Account = Account;
  }

  public String getHolderName() {
    return HolderName;
  }

  public void setHolderName(String HolderName) {
    this.HolderName = HolderName;
  }

  public String getCardNumber() {
    return CardNumber;
  }

  public void setCardNumber(String CardNumber) {
    this.CardNumber = CardNumber;
  }

  public String getPIN() {
    return PIN;
  }

  public void setPIN(String PIN) {
    this.PIN = PIN;
  }

  public String getCreationDate() {
    return CreationDate;
  }

  public void setCreationDate(String CreationDate) {
    this.CreationDate = CreationDate;
  }

  public String getExpirationDate() {
    return ExpirationDate;
  }

  public void setExpirationDate(String ExpirationDate) {
    this.ExpirationDate = ExpirationDate;
  }

  public String getCardState() {
    return CardState;
  }

  public void setCardState(String CardState) {
    this.CardState = CardState;
  }

  public BigDecimal getWithdrawLimit() {
    return WithdrawLimit;
  }

  public void setWithdrawLimit(BigDecimal WithdrawLimit) {
    this.WithdrawLimit = WithdrawLimit;
  }

  @Override
  public String toString() {
    return "Card{" + "Client=" + Client + '}';
  }
  
  

}
