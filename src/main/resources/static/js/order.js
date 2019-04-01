window.onload = fetchList;

function fetchList() {
  var order = (new URL(document.location)).searchParams.get('order');
  fetch(`/myorders/${order}`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
      console.log(jsonData);
    });
}

function showList(jsonData) {
  var tbody = document.getElementById('order-tablebody');
  tbody.innerHTML = '';
  var sum = 0;
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData[i].name;

    var quantityTd = document.createElement('td');
        quantityTd.innerHTML = jsonData[i].quantity + " db"; //quantity

    var priceTd = document.createElement('td');
        priceTd.innerHTML = jsonData[i].priceAtPurchase + " Ft";

    var sumPriceTd = document.createElement('td');
    sumPriceTd.innerHTML = jsonData[i].priceAtPurchase * jsonData[i].quantity + " Ft";

    var delTd = document.createElement('td');

    var delBut = document.createElement('button');
    delBut.innerHTML = "Törlés";
    delBut.onclick = deleteOrderItem;
    delBut["raw-data"] = jsonData[i];

    tr.appendChild(nameTd);
    tr.appendChild(quantityTd);
    tr.appendChild(priceTd);
    tr.appendChild(sumPriceTd);
    delTd.appendChild(delBut);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
}

function deleteOrderItem() {
console.log(this["raw-data"]);
  var address = this["raw-data"].address;
  var orderId = (new URL(document.location)).searchParams.get('order')
  if (!confirm("Biztosan törölni szeretné a tételt?")) {
          return;
      }
   fetch(`/order/${orderId}/${address}`, {
         method: "DELETE"
       })
       .then(function (jsonData) {
         document.getElementById("message-div").setAttribute("class", "alert alert-success");
         document.getElementById("message-div").innerHTML = "Törölve!";
       })
       .then(function() {
         fetchList();
       });
}
