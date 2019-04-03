window.onload = fetchList;
initPopup();

function fetchList() {
  if (document.getElementById('all').checked) {
    fetchAll();
  } else {
    fetchActive();
  }
}

function doConfirm(msg, yesFn) {
  var confirmBox = $(".confirm");
  confirmBox.find("#product-name").text(msg);
  confirmBox.find(".save").click(yesFn);
}

function initPopup() {
  var confirm = document.getElementById('delete-confirm');
  var span = document.getElementsByClassName('close');
  var save = document.getElementsByClassName('save');

  save.onclick = function () {
    confirm.style.display = "none";
  }
  for (var i = 0; i < span.length; i++) {
    save[i].onclick = function () {
      confirm.style.display = "none";
    }
  }
  for (var i = 0; i < span.length; i++) {
    span[i].onclick = function () {
      confirm.style.display = "none";
    }
  }
  window.onclick = function (event) {
    if (event.target == confirm) {
      confirm.style.display = "none";
    }
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
    if (jsonData[i].totalPrice === 0 && jsonData[i].orderStatus !== "DELETED") {
      fetch(`/orders/delete/${jsonData[i].id}`, {
          method: "DELETE"
        })
        .then(function () {
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
    totalPriceTd.innerHTML = jsonData[i].totalPrice + " Ft";

    var shippingAddressTd = document.createElement('td');
    shippingAddressTd.innerHTML = jsonData[i].shippingAddress;

    var delTd = document.createElement('td');

    if (jsonData[i].orderStatus == "ACTIVE") {
      shippingAddressTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
      totalPriceTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
      orderStatusTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
      purchaseDateTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
      userNameTd.setAttribute('onclick', `window.location="/order.html?order=${jsonData[i].id}"`);
      var delBut = document.createElement('button');
      delBut.innerHTML = "TÖRLÉS";
      addEventListenerOnButton(delBut, jsonData[i]);
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
    tr.appendChild(shippingAddressTd);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
}

function addEventListenerOnButton(element, data) {
  element.addEventListener('click', function () {
    var confirm = document.getElementById('delete-confirm');
    confirm.style.display = "block";
    doConfirm("Biztosan törölni szeretné a rendelést?", function yes() {
      deleteOrderItem(data);
    });
  });
}

function deleteOrderItem(item) {
  var id = item.id;
  fetch(`/orders/delete/${id}`, {
    method: "DELETE"
  }).then(function () {
    fetchList();
    document.getElementById("message-div").setAttribute("class", "alert alert-success");
    document.getElementById("message-div").innerHTML = "A terméket sikeresen töröltük a listából";
  });
}

function changeStatusToDelivered() {
  var orderId = this["raw-data"].id;
  fetch(`/orders/${orderId}/status`, {
    method: "POST"
  }).then(function () {
    fetchList();
    document.getElementById("message-div").setAttribute("class", "alert alert-success");
    document.getElementById("message-div").innerHTML = "A termék státuszát sikeresen kiszállítva(delivered) értékre állította!";
  });
}