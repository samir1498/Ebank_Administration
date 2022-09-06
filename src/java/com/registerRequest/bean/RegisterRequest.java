/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registerRequest.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Samir
 */
public class RegisterRequest implements Serializable {

  private int id;
  private String Date;
  private String Name;
  private String Email;
  private String PhoneNumber;
  private String Nationality;
  private String BirthDate;
  private String Adress;
  private String Username;
  private String ClientType;
  private String DocumentsPath;
  private List<String> files;

  public String getBirthDate() {
    return BirthDate;
  }

  public void setBirthDate(String BirthDate) {
    this.BirthDate = BirthDate;
  }

  public String getNationality() {
    return Nationality;
  }

  public void setNationality(String Nationality) {
    this.Nationality = Nationality;
  }

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String Date) {
    this.Date = Date;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String Email) {
    this.Email = Email;
  }

  public String getPhoneNumber() {
    return PhoneNumber;
  }

  public void setPhoneNumber(String PhoneNumber) {
    this.PhoneNumber = PhoneNumber;
  }

  public String getAdress() {
    return Adress;
  }

  public void setAdress(String Adress) {
    this.Adress = Adress;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String Username) {
    this.Username = Username;
  }

  public String getClientType() {
    return ClientType;
  }

  public void setClientType(String ClientType) {
    this.ClientType = ClientType;
  }

  public String getDocumentsPath() {
    return DocumentsPath;
  }

  public void setDocumentsPath(String DocumentsPath) {
    this.DocumentsPath = DocumentsPath;
  }

  @Override
  public String toString() {
    return "RegisterRequest{" + "id=" + id + ", Date=" + Date + ", Name=" + Name + ", Email=" + Email + ", PhoneNumber=" + PhoneNumber + ", Adress=" + Adress + ", Username=" + Username + ", ClientType=" + ClientType + ", DocumentsPath=" + DocumentsPath + '}';
  }

  public static RegisterRequest findById(Collection<RegisterRequest> listCarnet, int id) {
    System.out.println("id = " + id);
    return listCarnet.stream().filter(RegisterRequest -> id == (RegisterRequest.getId()))
            .findFirst().orElse(null);
  }
}
