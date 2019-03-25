window.onload = fetchAll;

function fetchList(){
  var radio = document.getElementsByName('status');
  if (radio.value == 'all') {
    fetchAll();
  } else {
    fetchActive();
  }
}

function fetchAll() {
  fetch(`/orders`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function fetchActive() {
  fetch(`/activeorders`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function showList(jsonData) {
  var tbody = document.getElementById('orders-tablebody');
  tbody.innerHTML = '';
  var sum = 0;
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');
    tr.setAttribute('onclick', `window.location="/orders.html?order=${jsonData[i].orderId}"`);

    var userNameTd = document.createElement('td');
    userNameTd.innerHTML = jsonData[i].userName;

    var purchaseDateTd = document.createElement('td');
    purchaseDateTd.innerHTML = jsonData[i].purchaseDate;

    var orderStatusTd = document.createElement('td');
    orderStatusTd.innerHTML = jsonData[i].orderStatus;

    var totalPriceTd = document.createElement('td');
    totalPriceTd.innerHTML = jsonData[i].totalPrice;

    var delTd = document.createElement('td');

    var delBut = document.createElement('button');
    delBut.innerHTML = "Törlés";
    delBut.onclick = deleteOrderItem;
    delBut["raw-data"] = jsonData[i];

    tr.appendChild(userNameTd);
    tr.appendChild(purchaseDateTd);
    tr.appendChild(orderStatusTd);
    tr.appendChild(totalPriceTd);
    delTd.appendChild(delBut);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
}

function deleteOrderItem() {
    var id = this["raw-data"].id;
    console.log(id);
    if (!confirm("Biztosan törölni szeretné a rendelést?")) {
            return;
        }
     fetch(`/orders/delete/{id}`, {
           method: "DELETE"
         })
         .then(function (response) {
           return response.json();
         })
         .then(function (jsonData) {
           document.getElementById("message-div").setAttribute("class", "alert alert-success");
           document.getElementById("message-div").innerHTML = "A terméket sikeresen töröltük a listából";
         })
         .then(function (jsonData) {
           fetchList();
         });
}
