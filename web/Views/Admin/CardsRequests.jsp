<%-- 
    Document   : RegisterRequests
    Created on : 19 juil. 2022, 19:12:46
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.registerRequest.bean.RegisterRequest" %>
<%@ include file="../../includes/taglibs.jsp"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <fmt:bundle basename="i18n.Bundle"><title>Card Requests</title></fmt:bundle>

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

        <%@ include file="../../includes/sidebar.jsp"%>

        <div class="home_content">

          <div class="transactions">
            <h2>Card Requests</h2>
            <div class="filter">


              <div class="search">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="Search..." id="search_account">
              </div>


            </div>
            <div id="table-scroll" class="table-scroll">
              <form action="CardsRequests" method="post">
              <table id="main-table" class="main-table compact stripe order-column responsive">
                <thead>
                  <tr>
                    <th id="th1">Date</th>
                    <th id="th2">Card Number</th>
                    <th id="th3">Holder Name</th>
                    <th id="th4">Expiration Date</th>
                    <th id="th5">Account</th>
                    <th id="th6">Account Cards</th>
                    <th id="th7"></th>
                    <th id="th8"></th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach  items="${CardRq}" var="rq" >

                    <tr>
                      <!-- date -->
                      <td headers="th1" id="date_col">
                        <h4>${rq.getCreationDate()}</h4>
                      </td>
                      <!-- client from -->
                      <td headers="th2">
                        <span><c:out value="${rq.getCardNumber()}"/></span>
                        <input type="hidden" name="cardnumber" value="${rq.getCardNumber()}">
                      </td>
                      <!-- client to -->
                      <td headers="th3" align="center" >
                        <span><c:out value="${rq.getHolderName()}"/></span>
                      </td>
                      <!-- account from -->
                      <td headers="th4">
                        <span><c:out value="${rq.getExpirationDate()}"/></span>
                      </td>
                      <!-- account to -->
                      <td headers="th5">
                        <span><c:out value="${rq.getAccount().getRIB()}"/></span>
                      </td>
                      <!-- State -->
                      <td headers="th6"><span>${rq.getCardsInAccount()}</span></td>

                      <!-- Options -->
                      <td headers="th7">
                        <button name="approuve" title="Approuve" 
                                <c:if test="${rq.getCardsInAccount() > 1}">style="color: gray" disabled</c:if>
                                <c:if test="${rq.getCardsInAccount() < 2}">style="color: green"</c:if>>
                          <i class="fa-solid fa-circle-check"></i>
                        </button>
                      </td>
                      
                      <td headers="th8">
                        
                        <button name="reject" title="Reject" style="color: red">
                          <i class="fa-solid fa-circle-xmark"></i></button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
</form>
            </div>
          </div>
        </div>
      </div>     
      <script src="js/register-requests.js"></script>
      <script src="js/sidebar-script.js"></script>

    </fmt:bundle>
  </body>

</html>
