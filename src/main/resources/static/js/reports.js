fetchReport();

function fetchReport(){
    fetch('/reports/orders')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showReport(jsonData);
    });
}

function showReport(jsonData) {
 var tableBody = document.getElementById('order-report-tablebody');
  tableBody.innerHTML = "";
  var itemsPerMonth = 0;
  var tr = document.createElement('tr');
  var monthTd = document.createElement('td');
  monthTd.innerHTML = jsonData[0].month;
  for (var i = 0; i < jsonData.length; i++) {
       if (jsonData[i].month === monthTd.innerHTML){
           var statusTd = document.createElement('td');
           statusTd.innerHTML = jsonData[i].status;

           var numberOfOrdersTd = document.createElement('td');
           numberOfOrdersTd.innerHTML = jsonData[i].numberOfOrders;

           var valueOfOrdersTd = document.createElement('td');
           valueOfOrdersTd.innerHTML = jsonData[i].valueOfOrders;

           itemsPerMonth++;

           tr.appendChild(monthTd);
           tr.appendChild(statusTd);
           tr.appendChild(numberOfOrdersTd);
           tr.appendChild(valueOfOrdersTd);

           tr = document.createElement('tr');
       } else {
           monthTd.setAttribute('rowspan', itemsPerMonth);
           monthTd = document.createElement('td');
           monthTd = jsonData[i].month;
           itemsPerMonth = 0;

           var statusTd = document.createElement('td');
           statusTd.innerHTML = jsonData[i].status;

           var numberOfOrdersTd = document.createElement('td');
           numberOfOrdersTd.innerHTML = jsonData[i].numberOfOrders;

           var valueOfOrdersTd = document.createElement('td');
           valueOfOrdersTd.innerHTML = jsonData[i].valueOfOrders;

           itemsPerMonth++;

           tr.appendChild(monthTd);
           tr.appendChild(statusTd);
           tr.appendChild(numberOfOrdersTd);
           tr.appendChild(valueOfOrdersTd);

           tr = document.createElement('tr');
       }
}


     }
}
