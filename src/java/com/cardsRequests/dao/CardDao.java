/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cardsRequests.dao;

import com.cardsRequests.bean.Card;

/**
 *
 * @author Samir
 */
public interface CardDao {

  public String ApprouveCard(String cardNumber);

  public String RejectCard(String cardNumber);

  public Card getCardRequest(String cardnumber);
}
