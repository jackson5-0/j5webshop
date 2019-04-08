fetchOrdersReport();
fetchProductsReport();

function fetchOrdersReport(){
    fetch('/reports/orders')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
        if (jsonData.length === 0) {
            var messageDiv = document.getElementById('message-div');
            messageDiv.setAttribute("class", "alert-danger");
            messageDiv.innerHTML = "Rendszerünkben még nem rögzítettünk rendelést!";
        } else {
            showOrdersReport(jsonData);
        }
    });
}

function fetchProductsReport(){
    fetch('/reports/products')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showProductsReport(jsonData);
    });
}

function showOrdersReport(jsonData) {
     var tableBody = document.getElementById('order-report-tablebody');
     tableBody.innerHTML = "";

     for (var i = 0; i < jsonData.length; i++) {
          var tr = document.createElement('tr');

          var yearTd = document.createElement('td');
          yearTd.innerHTML = jsonData[i].year;
          var monthTd = document.createElement('td');
          monthTd.innerHTML = jsonData[i].month;
          var statusTd = document.createElement('td');
          statusTd.innerHTML = jsonData[i].status;
          var numberOfOrdersTd = document.createElement('td');
          numberOfOrdersTd.innerHTML = jsonData[i].numberOfOrders;
          var valueOfOrdersTd = document.createElement('td');
          valueOfOrdersTd.innerHTML = jsonData[i].valueOfOrders;

          tr.appendChild(yearTd);
          tr.appendChild(monthTd);
          tr.appendChild(statusTd);
          tr.appendChild(numberOfOrdersTd);
          tr.appendChild(valueOfOrdersTd);
          tableBody.appendChild(tr);
     }
}

function showProductsReport(jsonData) {
  var tableBody = document.getElementById('product-report-tablebody');
  tableBody.innerHTML = "";

  for (var i = 0; i < jsonData.length; i++) {
       var tr = document.createElement('tr');

       var yearTd = document.createElement('td');
       yearTd.innerHTML = jsonData[i].year;
       var monthTd = document.createElement('td');
       monthTd.innerHTML = jsonData[i].month;
       var codeTd = document.createElement('td');
       codeTd.innerHTML = jsonData[i].code;
       var nameTd = document.createElement('td');
       nameTd.innerHTML = jsonData[i].name;
       var priceTd = document.createElement('td');
       priceTd.innerHTML = jsonData[i].price;
       var quantityTd = document.createElement('td');
       quantityTd.innerHTML = jsonData[i].quantity;
       var sumTd = document.createElement('td');
       sumTd.innerHTML = jsonData[i].sum;

       tr.appendChild(yearTd);
       tr.appendChild(monthTd);
       tr.appendChild(codeTd);
       tr.appendChild(nameTd);
       tr.appendChild(priceTd);
       tr.appendChild(quantityTd);
       tr.appendChild(sumTd);
       tableBody.appendChild(tr);
  }
}