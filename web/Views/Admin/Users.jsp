<%-- 
    Document   : RegisterRequests
    Created on : 19 juil. 2022, 19:12:46
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.registerRequest.bean.RegisterRequest" %>

<%@ include file="../../includes/taglibs.jsp"%>

<fmt:bundle basename="configuration.application">
  <fmt:message key="Valider-Demande-Carte" var="checkCard" />
  <fmt:message key="Recherche" var="checkSearch" />
  <fmt:message key="BankName" var="BankName" scope="session" />
  <fmt:message key="Infos-Client" var="checkInfoClient" />
  <fmt:message key="Liste-Clients" var="checkListeClients" />
  <fmt:message key="Changer-Etat-Client" var="checkChangerEtatClient" />
  <fmt:message key="Bloquer" var="checkBloquer" />
  <fmt:message key="Debloquer" var="checkDebloquer" />
</fmt:bundle>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <fmt:bundle basename="i18n.Bundle"><title>Clients</title></fmt:bundle>

      <link rel="stylesheet" href="css/transactions.css">
      <link rel="stylesheet" href="css/sidebar-style.css">

      <link rel="stylesheet" href="fontawsome/css/all.css">

      <link rel="stylesheet" href="css/buttons.dataTables.min.css" />

      <link rel="stylesheet" href="css/responsive.dataTables.min.css">
      <link rel="stylesheet" href="css/jquery.dataTables.min.css">
      <link rel="stylesheet" href="css/dataTables.dateTime.min.css">
      <link rel="stylesheet" href="css/searchBuilder.dataTables.min.css">


      <script src="js/jquery-3.6.0.min.js"></script>

      <script src="js/jquery.validate.min.js"></script>

      <script src="js/jquery.cookie.js"></script>

      <script src="js/jquery.dataTables.min.js"></script>
      <script src="js/dataTables.dateTime.min.js"></script>
      <script src="js/dataTables.searchBuilder.min.js"></script>

      <link  rel="stylesheet" href="css/jquery-ui-1.10.0.custom.min.css" />
      <script src="js/jquery-ui.js"></script>

      <link  href="viewerJs/viewer.css" rel="stylesheet">
      <script src="viewerJs/viewer.js"></script>

    </head>

    <body>
    <fmt:bundle basename="i18n.Bundle">
      <div class="main">

        <div class="Request_details popup_center">

          <c:forEach  items="${clients}" var="client" >
            
              <div class="request ${client.getId()}">
                <form action="Clients" method="post">
                <h2>Client Details</h2>

                <form action="" method="post" class="details_form">
                  <table class="request_details_table">
                    <tbody>
                      <tr>
                        <td>FullName</td>
                        <td><span>${client.getFullName()}</span></td>
                      </tr>
                      <tr>
                        <td>
                          <c:if test="${client.getClientType() == 'Individual'}">Birthdate</c:if>
                          <c:if test="${client.getClientType() == 'Organisation'}">Creation date</c:if>

                          </td>
                          <td><span>${client.getBirthDate()}</span></td>
                      </tr>
                      <tr>
                        <td>Username</td>
                        <td><span>${client.getUsername()}</span></td>
                      </tr>
                      <tr>
                        <td>Email</td>
                        <td><span>${client.getEmail()}</span></td>
                      </tr>
                      <tr>
                        <td>Phone number</td>
                        <td><span>${client.getPhoneNumber()}</span></td>
                      </tr>
                      <tr>
                        <td>Nationality</td>
                        <td><span>${client.getNationality()}</span></td>
                      </tr>
                      <tr>
                        <td>Address</td>
                        <td><p>${client.getAdress()}</p></td>
                      </tr>

                      <tr>
                        <td>Client type</td>
                        <td><span>${client.getClientType()}</span></td>
                      </tr>
                      <c:if test="${checkChangerEtatClient == 'true'}">

                        <tr>
                          <td colspan="2">
                            <c:if test="${client.getClientState() != 'active' and checkDebloquer == 'true'}">
                              <input type="submit" name="unblock" value="unblock" class="approuve" style="width:35%">
                              <input type="hidden" name="id" value="${client.getId()}">
                               
                            </c:if>
                            <c:if test="${client.getClientState() == 'active' and checkBloquer=='true'}">
                              <input type="submit" name="block" value="block" class="reject" style="width:35%">
                              <input type="hidden" name="id" value="${client.getId()}">
                            </c:if>

                            <input type="hidden" name="id" value="" class="hidden_input_id">
                          </td>  
                        </tr>
                      </c:if>
                    </tbody>
                  </table>

                </form>

              </div>

          </c:forEach>
          <button class="close_btn" onclick="closeRegisterRequest()">x</button>
        </div>

        <%@ include file="../../includes/sidebar.jsp"%>

        <div class="home_content">

          <div class="transactions">
            <h2> Clients</h2>
            <div class="filter">

              <c:if test = "${checkSearch == 'true'}">
                <div class="search">
                  <i class="fa-solid fa-magnifying-glass"></i>
                  <input type="text" placeholder="Search..." id="search_account">
                </div>
              </c:if>

            </div>
            <div id="table-scroll" class="table-scroll">

              <table id="main-table" class="main-table compact stripe order-column responsive">
                <thead>
                  <tr>
                    <th id="th1">Date</th>
                    <th id="th2">Name</th>
                    <th id="th3">Email</th>
                    <th id="th4">Phone Number</th>
                    <th id="th5">Username</th>
                    <th id="th6">Client Type</th>
                    <th id="th7">Client State</th>
                      <c:if test = "${checkInfoClient == 'true'}">
                      <th id="th8"></th>
                      </c:if>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach  items="${clients}" var="client" >

                    <tr>
                      <!-- date -->
                      <td headers="th1" id="date_col">
                        <h4>${client.getDate()}</h4>
                      </td>
                      <!-- client from -->
                      <td headers="th2">
                        <span><c:out value="${client.getFullName()}"/></span>
                      </td>
                      <!-- client to -->
                      <td headers="th3" align="center" >
                        <span><c:out value="${client.getEmail()}"/></span>
                      </td>
                      <!-- account from -->
                      <td headers="th4">
                        <span><c:out value="${client.getPhoneNumber()}"/></span>
                      </td>
                      <!-- account to -->
                      <td headers="th5">
                        <span><c:out value="${client.getUsername()}"/></span>
                      </td>
                      <!-- Type -->
                      <td headers="th6"><span><c:out value="${client.getClientType()}"/></span></td>

                      <!-- State -->
                      <td headers="th7"><span><c:out value="${client.getClientState()}"/></span></td>
                          <c:if test = "${checkInfoClient == 'true'}">
                        <!-- Options -->
                        <td headers="th8">
                          <button id="${client.getId()}" onclick="showClientDetails(this)"><i class="fa-solid fa-ellipsis"></i>
                          </button>
                        </td>
                      </c:if>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>

            </div>
          </div>
        </div>
      </div>     
      <script src="js/clients.js"></script>
      <script src="js/sidebar-script.js"></script>

    </fmt:bundle>
  </body>

</html>
