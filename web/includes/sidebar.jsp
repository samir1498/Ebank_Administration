<%-- 
    Document   : sidebar
    Created on : 4 juil. 2022, 18:32:32
    Author     : Samir
--%>
<%@ include file="taglibs.jsp"%>

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
<div class="sidebar">
  <div class="logo_content">

    <div class="logo">
      <i class="fa-solid fa-building-columns" style="display: none"></i>
      <i class="fa-solid fa-bars"></i>
      <span>${BankName}</span>
      <i class="fa-solid fa-xmark" id="sidebar-btn"></i>
    </div>
  </div>

  <ul class="nav_list">
    <li>
      <a href="Dashboard" class="dashboard">
        <i class="fa-solid fa-gauge"></i>
        <span class="links_name">Dashboard</span>
      </a>
    </li>
    <li>
      <a href="RegisterRequests" class="requests">
        <i class="fa-solid fa-user-clock"></i>
        <span class="badge">0</span>
        <span class="links_name">Requests</span>
      </a>
    </li>
    
        <li>
      <a href="AccountsRequests" class="requests">
        <i class="fa-solid fa-user-clock"></i>
        <span class="links_name">Accounts Requests</span>
      </a>
    </li>

    <c:if test="${checkListeClients == 'true'}">
      <li>
        <a href="ClientsList" class="Users">
          <i class="fa-solid fa-users"></i>
          <span class="links_name">Clients</span>
        </a>
      </li>
    </c:if>
    <c:if test="${checkCard == 'true'}">
      <li>
        <a href="CardsRequests" class="CardsRequests">
          <i class="fa-solid fa-credit-card"></i>
          <span class="links_name">Cards Requests</span>
        </a>
      </li>
    </c:if>
  </ul>

</div>
<div class="profile_content">

  <div class="profile">
    <div class="profile_details">

      <img src="images/default_profile_picture.png" alt="profile picture" class="profile_pic" style="filter:none; box-shadow:  none;object-fit: cover; object-position:0 100%;">

      <div class="name_username">
        <div class="name">${admin.getUsername()}</div>
        <div class="username">@${admin.getUsername()}</div>
      </div>
    </div>

    <a href="Logout" id="log_out"><i  class="fa-solid fa-arrow-right-from-bracket"></i></a>

  </div>
</div>
<div class="popup_div"></div>


