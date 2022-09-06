<%-- 
    Document   : adminDashboard
    Created on : 21 août 2022, 12:31:40
    Author     : Samir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<%@page import="java.math.RoundingMode"%>
<c:if test="${langue !=null}">
  <fmt:setLocale value="${langue}"></fmt:setLocale>
</c:if>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/sidebar-style.css">
    <link rel="stylesheet" href="fontawsome/css/all.css">

    <script src="js/jquery-3.6.0.min.js"></script>

    <script src="js/jquery.validate.min.js"></script>

    <script src="js/jquery.cookie.js"></script>

    <script defer src="js/sidebar-script.js"></script>


    <script src="js/chartjs/dist/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns/dist/chartjs-adapter-date-fns.bundle.min.js"></script>

    <script defer src="js/dashboard.js"></script>


    <title>Dashboard</title>
  </head>
  <body>
    <div class="main">
      <fmt:bundle basename="i18n.Bundle">

        <%@ include file="../../includes/sidebar.jsp"%>

        <div class="popup_div"></div>

        <div class="home_content">

          <div class="statistiques">

            <div class="outcome" style="grid-area: outcome">
              <h3>${outcome.setScale(2, RoundingMode.FLOOR)} ${lc}</h3>
              <h5>Outcome</h5>
              <div class="arrow">
                <i class="fa-solid fa-circle-arrow-up"></i></div>
            </div>
            <div class="income" style="grid-area: income"> 
              <h3>${income.setScale(2, RoundingMode.FLOOR)} ${lc}</h3>
              <h5>Income</h5>
              <div class="arrow">
                <i class="fa-solid fa-circle-arrow-down"></i>
              </div>
            </div>

            <div class="foo" style="grid-area: foo">

              <div class="pie-chart">
                <canvas id="last_month_expenses"></canvas>

                <span class="percentage">${income_per}%</span>
              </div >
            </div>

            <div class="total_savings">

              <h2 style="width: 100%">New Clients</h2>
              <div class="charts">

                <div class="bar">
                  <canvas id="savings_percentage"></canvas>
                </div>
              </div>

            </div>

            <div class="total_budget">

              <h2 style="width: 100%">Transactions Stats</h2>
              <div class="charts">
                <div class="bar">
                  <canvas id="budget_percentage"></canvas>
                </div>
              </div>


            </div>
          </div>
        </div>

      </div>
      <script>
        $(document).ready(function () {
          if ($('.badge').text() === '0') {
            $('.badge').hide();
          }
        });
        var counter = 0;
        function updateCounter() {
          $.ajax({
            url: "RegisterRequestCounter",
            async: false,
            success: function (result) {
              $('.requests .badge').text(result);
              $('.requests .badge').show();
            }});
          if ($('.badge').text() === '0') {
            $('.badge').hide();
          } else {
            $('a.requests span.badge').show();
          }
        }
        setInterval(
                updateCounter
                , 12000);


        var arr = [];

        <c:forEach var="entry" items="${clientsCount}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        arr.push({
          x: Date.parse(key),
          y: value
        });

        </c:forEach>

        arr = [
          {x: Date.parse('2022-01-01 00:00:00 GMT+0100'), y: 1970},
          {x: Date.parse('2022-02-01 00:00:00 GMT+0100'), y: 1300},
          {x: Date.parse('2022-03-01 00:00:00 GMT+0100'), y: 900},
          {x: Date.parse('2022-04-01 00:00:00 GMT+0100'), y: 700},
          {x: Date.parse('2022-05-01 00:00:00 GMT+0100'), y: 800},
          {x: Date.parse('2022-06-01 00:00:00 GMT+0100'), y: 953},
          {x: Date.parse('2022-07-01 00:00:00 GMT+0100'), y: 536},
          {x: Date.parse('2022-08-01 00:00:00 GMT+0100'), y: 1930},
          {x: Date.parse('2022-09-01 00:00:00 GMT+0100'), y: 480},
          {x: Date.parse('2022-10-01 00:00:00 GMT+0100'), y: 661},
          {x: Date.parse('2022-11-01 00:00:00 GMT+0100'), y: 443},
          {x: Date.parse('2022-12-01 00:00:00 GMT+0100'), y: 1793}
        ];

        const data = {
          datasets: [{
              label: 'new Clients',
              data: arr,
              backgroundColor: [
                'rgba(255, 99, 10, 0.5)',
                'rgba(10, 99, 255, 0.5)'

              ],
              borderWidth: 0
            }]
        };

        var bar = document.getElementById("savings_percentage");
        var barchart = new Chart(bar, {
          type: 'bar',
          data: data,
          options: {
            scales: {
              x: {
                type: 'time',
                time: {
                  unit: 'month'
                }
              },
              y: {
                ticks: {
                  stepSize: 100
                }
              }

            },
            plugins: {
              legend: {
                display: false, position: 'bottom'
              },
              title: {
                display: false
              }

            },
            maintainAspectRatio: false,
            responsive: true,
            ////events: [],
            // Elements options apply to all of the options unless overridden in a dataset
            // In this case, we are setting the border of each horizontal bar to be 2px wide
            elements: {
              bar: {
                borderWidth: 2
              }
            }

          }
        });



        var Transactionsarr = [];

        <c:forEach var="entry" items="${TransactionsStats}" varStatus="i">
        var key = '${entry.key}';
        var value = '${entry.value}';
        Transactionsarr.push({
          x: Date.parse(key),
          y: value
        });
        </c:forEach>

        const transactionsdata = {
          datasets: [{
              //label: 'Transactions (دج)',
              data: Transactionsarr,
              backgroundColor: [
                'rgba(10, 99, 255, 0.5)',
                'rgba(255, 99, 10, 0.5)'

              ],
              borderWidth: 0
            }]
        };


        var bar1 = document.getElementById("budget_percentage");
        var barchart1 = new Chart(bar1, {
          type: 'bar',
          data: transactionsdata,
          options: {
            scales: {
              x: {
                type: 'time',
                time: {
                  unit: 'month'
                }
              }

            },
            plugins: {
              legend: {
                display: false, position: 'bottom'
              },
              title: {
                display: false
              }

            },
            maintainAspectRatio: false,
            responsive: true,
            ////events: [],
            indexAxis: 'x',
            // Elements options apply to all of the options unless overridden in a dataset
            // In this case, we are setting the border of each horizontal bar to be 2px wide
            elements: {
              bar: {
                borderWidth: 2
              }
            }

          }
        });


        var ctx = document.getElementById("last_month_expenses");
        var LastMonth = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ['Income', 'Outcome'],
            datasets: [
              {
                label: '',
                data: [],
                backgroundColor: [
                  'rgba(10, 99, 255, 0.5)',
                  '#888'
                ], borderWidth: 0 //this will hide border
              }]
          },
          options: {
            cutout: 160,
            plugins: {
              legend: {
                display: true, position: 'bottom'
              },
              title: {
                display: true,
                text: 'Income/Outcome'
              }
            },
            maintainAspectRatio: false,
            responsive: true,
            layout: {
              padding: 15
            }
          }
        });
        var income = ${income_per};
        LastMonth.data.datasets[0].data = [income, 100 - income];
        LastMonth.update();
      </script>
    </fmt:bundle>
  </body>

</html>
