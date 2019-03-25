window.onload = fetchList;

function fetchList(){
  if (document.getElementById('all').checked) {
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
  console.log(jsonData);
  var tbody = document.getElementById('orders-tablebody');
  tbody.innerHTML = '';
  var sum = 0;
  for (var i = 0; i < jsonData.length; i++) {
//  if (jsonData.totalPrice===0){
//   ?
//  }
    var tr = document.createElement('tr');

    var userNameTd = document.createElement('td');
    userNameTd.innerHTML = jsonData[i].userName;
    userNameTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);

    var purchaseDateTd = document.createElement('td');
    purchaseDateTd.innerHTML = jsonData[i].purchaseDate;
    purchaseDateTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);

    var orderStatusTd = document.createElement('td');
    orderStatusTd.innerHTML = jsonData[i].orderStatus;
    orderStatusTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);

    var totalPriceTd = document.createElement('td');
    totalPriceTd.innerHTML = jsonData[i].totalPrice;
    totalPriceTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);

    var delTd = document.createElement('td');

    tr.appendChild(userNameTd);
    tr.appendChild(purchaseDateTd);
    tr.appendChild(orderStatusTd);
    tr.appendChild(totalPriceTd);

    if (jsonData[i].orderStatus == "ACTIVE" || jsonData[i].orderStatus == "DELIVERED") {
        var delBut = document.createElement('button');
        delBut.innerHTML = "Törlés";
        delBut.onclick = deleteOrderItem;
        delBut["raw-data"] = jsonData[i];
        delTd.appendChild(delBut);
        var changeStatusButton = document.createElement('button');
        changeStatusButton.innerHTML = 'KISZÁLLÍTÁS';
        changeStatusButton.onclick = changeStatusToDelivered;
        changeStatusButton["raw-data"] = jsonData[i];
        delTd.appendChild(changeStatusButton)
    }

    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
}

function deleteOrderItem() {
    var id = this["raw-data"].id;
    if (!confirm("Biztosan törölni szeretné a rendelést?")) {
            return;
        }
     fetch(`/orders/delete/${id}`, {
           method: "DELETE"
         }).then( function() {
            fetchList();
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.getElementById("message-div").innerHTML = "A terméket sikeresen töröltük a listából";
         });
}

function changeStatusToDelivered(){
    var orderId = this["raw-data"].id;
    fetch(`/orders/${orderId}`, {
            method: "POST"
       }).then( function() {
                      fetchList();
                      document.getElementById("message-div").setAttribute("class", "alert alert-success");
                      document.getElementById("message-div").innerHTML = "A termék státuszát sikeresen kiszállítva(delivered) értékre állította!";
                   });
}
