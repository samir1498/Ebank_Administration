/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



var id = 0;
function showClientDetails(e) {
  id = e.id;

  $('.Request_details').show();
  $('.request').hide();
  $('.request.' + id).show();
  $('.hidden_input_id').val(id);
  blur();
}

function closeRegisterRequest() {
  $('.Request_details').hide();
  unblur();
}


$('.popup form').on('submit', function (e) {
//e.preventDefault();
  var criteria = $(this).find('input,select').filter(function () {
    return ((!!this.value) && (!!this.name));
  }).serializeArray();
  var formData = JSON.stringify(criteria);
  localStorage.setItem('savedValues', formData);
});
//search
function searchTransaction(value) {
  $('.main-table tr').each(function () {
    var content = $(this).find('td').text();
    if (content.toUpperCase().includes(value.trim().toUpperCase())) {
      $(this).not('thead tr').show();
    } else {
      $(this).not('thead tr').hide();
    }
  });
}
const toTimestamp = (strDate) => {
  const dt = new Date(strDate).getTime();
  return dt / 1000;
};
//filter
function FilterByFromDate(value) {
  $('.main-table tr').each(function () {
    var content = $(this).find('td:first').text();
    if (toTimestamp(content) >= (toTimestamp(value))) {
      $(this).not('thead tr').show();
    } else {
      $(this).not('thead tr').hide();
    }
  });
}

function FilterByToDate(value) {
  $('.main-table tr').each(function () {
    var content = $(this).find('td:first').text();
    if (toTimestamp(content) <= (toTimestamp(value))) {
      $(this).not('thead tr').show();
    } else {
      $(this).not('thead tr').hide();
    }
  });
}

$(document).ready(function () {
  if ($('.badge').text() === '0') {
    $('.badge').hide();
  }
});
var oTable = $('#main-table').DataTable({
  dom: 'lrtp',
  responsive: true,
  "columnDefs": [
    {"className": "dt-center", "targets": "_all"}
  ],
  scrollY: 360

}).columns.adjust();
;
$("#datepicker_from").datepicker({
  showOn: "button",
  buttonImage: "images/calendar.ico",
  buttonImageOnly: false,
  "onSelect": function (date) {
    minDateFilter = new Date(date).getTime();
    oTable.draw();
  }
}).keyup(function () {
  minDateFilter = new Date(this.value).getTime();
  oTable.draw();
});
$("#datepicker_to").datepicker({
  showOn: "button",
  buttonImage: "images/calendar.ico",
  buttonImageOnly: false,
  "onSelect": function (date) {
    maxDateFilter = new Date(date).getTime() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000;
    oTable.draw();
  }
}).keyup(function () {
  maxDateFilter = new Date(this.value).getTime() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000;
  oTable.draw();
});
// Date range filter
minDateFilter = "";
maxDateFilter = "";
$.fn.dataTableExt.afnFiltering.push(
        function (oSettings, aData, iDataIndex) {
          if (typeof aData._date === 'undefined') {
            aData._date = new Date(aData[1]).getTime();
          }

          if (minDateFilter && !isNaN(minDateFilter)) {
            if (aData._date < minDateFilter) {
              return false;
            }
          }

          if (maxDateFilter && !isNaN(maxDateFilter)) {
            if (aData._date > maxDateFilter) {
              return false;
            }
          }
          return true;
        }
);
//search bar
$('#search_account').keyup(function () {
  oTable.search($(this).val(), false, false).draw();
});
function blur() {
  $('.popup_div').show();
  $('.home_content').css('filter', 'blur(5px)');
  $('.sidebar').css('filter', 'blur(5px)');
  $('.profile_content').css('filter', 'blur(5px)');
}

function unblur() {
  $('.popup_div').hide();
  $('.home_content').css('filter', 'none');
  $('.sidebar').css('filter', 'none');
  $('.profile_content').css('filter', 'none');
}
