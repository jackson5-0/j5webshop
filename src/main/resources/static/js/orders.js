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
  var tbody = document.getElementById('orders-tablebody');
  tbody.innerHTML = '';
  var sum = 0;
  for (var i = 0; i < jsonData.length; i++) {

    var tr = document.createElement('tr');

    var userNameTd = document.createElement('td');
    userNameTd.innerHTML = jsonData[i].userName;


    var purchaseDateTd = document.createElement('td');
    purchaseDateTd.innerHTML = jsonData[i].purchaseDate.replace(/-/g, '.').replace(/T/g, ' ').substring(0, 16);;


    var orderStatusTd = document.createElement('td');
    if (jsonData[i].totalPrice===0 && jsonData[i].orderStatus!=="DELETED"){
        fetch(`/orders/delete/${jsonData[i].id}`, {
                    method: "DELETE"
               })
               .then(function(){
               fetchList();
               });
    }

    if (jsonData[i].orderStatus == 'DELIVERED') {
        orderStatusTd.innerHTML = 'Kiszállítva';
    }
    if (jsonData[i].orderStatus == 'DELETED') {
        orderStatusTd.innerHTML = 'Törölt';
    }
    if (jsonData[i].orderStatus == 'ACTIVE') {
        orderStatusTd.innerHTML = 'Aktív';
    }

    var totalPriceTd = document.createElement('td');
    totalPriceTd.innerHTML = jsonData[i].totalPrice;

    var delTd = document.createElement('td');

    if (jsonData[i].orderStatus == "ACTIVE") {
        totalPriceTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
        orderStatusTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
        purchaseDateTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
        userNameTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
        var delBut = document.createElement('button');
        delBut.innerHTML = "TÖRLÉS";
        delBut.onclick = deleteOrderItem;
        delBut["raw-data"] = jsonData[i];
        delTd.appendChild(delBut);
        var changeStatusButton = document.createElement('button');
        changeStatusButton.innerHTML = 'KISZÁLLÍTÁS';
        changeStatusButton.onclick = changeStatusToDelivered;
        changeStatusButton["raw-data"] = jsonData[i];
        delTd.appendChild(changeStatusButton)
    }

    tr.appendChild(userNameTd);
    tr.appendChild(purchaseDateTd);
    tr.appendChild(orderStatusTd);
    tr.appendChild(totalPriceTd);
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
    fetch(`/orders/${orderId}/status`, {
            method: "POST"
       }).then( function() {
                      fetchList();
                      document.getElementById("message-div").setAttribute("class", "alert alert-success");
                      document.getElementById("message-div").innerHTML = "A termék státuszát sikeresen kiszállítva(delivered) értékre állította!";
                   });
}