fetchList();
fetchAddresses();
initPopup();
var flush = document.getElementById('flush');
flush.addEventListener('click', function fn() {
  var confirm = document.getElementById('delete-confirm');
  confirm.style.display = "block";
  doConfirm("Biztosan ki szeretné üríteni a kosarát?", function yes() {
    flushBasket();
  });
})

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

function fetchList() {
  fetch(`/basket`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function flushBasket() {
  fetch(`/basket`, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("message-div").setAttribute("class", "alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages;
        return jsonData;
      } else {
        document.getElementById("message-div").setAttribute("class", "alert-danger");
        document.getElementById("message-div").innerHTML = jsonData.messages;
        return jsonData;
      }
    })
    .then(function (jsonData) {
      showList(jsonData); // hibát ír a consolra (jsonData is not defined)
    });
}

function showList(jsonData) {
  var tbody = document.getElementById('basket-tablebody');
  tbody.innerHTML = '';
  var sum = 0;
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData[i].product.name;

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData[i].product.price + " Ft";
    sum += jsonData[i].product.price * jsonData[i].quantity;

    var qtyTd = document.createElement('td');
    qtyTd.innerHTML = jsonData[i].quantity;

    var sumItemPriceTd = document.createElement('td');
    sumItemPriceTd.innerHTML = jsonData[i].product.price * jsonData[i].quantity + " Ft";

    var increaseAmountTd = document.createElement('td');
    var increaseAmountBut = document.createElement('button');
    increaseAmountBut.innerHTML = "+";
    increaseAmountBut.onclick = increaseAmount;
    increaseAmountBut["raw-data"] = jsonData[i];
    var decreaseAmountTd = document.createElement('td');
    var decreaseAmountBut = document.createElement('button');
    decreaseAmountBut.innerHTML = "-";
    decreaseAmountBut.onclick = decreaseAmount;
    decreaseAmountBut["raw-data"] = jsonData[i];

    var delTd = document.createElement('td');

    var delBut = document.createElement('button');
    delBut.innerHTML = "Törlés";
    delBut.onclick = deleteBasketItem;
    delBut["raw-data"] = jsonData[i];

    tr.appendChild(nameTd);
    tr.appendChild(priceTd);
    tr.appendChild(qtyTd);
    tr.appendChild(sumItemPriceTd);
    increaseAmountTd.appendChild(increaseAmountBut);
    tr.appendChild(increaseAmountTd);
    decreaseAmountTd.appendChild(decreaseAmountBut);
    tr.appendChild(decreaseAmountTd);
    delTd.appendChild(delBut);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
  var tr2 = document.createElement('tr');
  var totalTd = document.createElement('td');
  var sumTd = document.createElement('td');

  sumTd.innerHTML = "TELJES ÖSSZEG";
  totalTd.innerHTML = sum + " Ft";

  tr2.appendChild(sumTd);
  tr2.appendChild(document.createElement('td')); //üres cella
  tr2.appendChild(totalTd);

  tbody.appendChild(document.createElement('br'));
  tbody.appendChild(tr2);
}

function orderBasket() {
  var basketId = user.basketId;
  var address;
  var addressList = document.getElementsByTagName('input');
  for (var i = 0; i < addressList.length; i++) {
    if (addressList[i].checked) {
      address = addressList[i].value;
    }
  }
  if (address === 'new address') {
    address = 'new' + document.getElementById('new-address').value;
  }
  fetch(`/myorders?basketId=${basketId}`, {
      method: "POST",
      body: JSON.stringify(address),
      headers: {
        "Content-type": "application/json"
      }
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("message-div").setAttribute("class", "alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages;
        return jsonData;
      } else {
        fetchList();
        document.getElementById("message-div").setAttribute("class", "alert-danger");
        document.getElementById("message-div").innerHTML = jsonData.messages;
        return jsonData;
      }
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function deleteBasketItem() {
  var productId = this["raw-data"].product.id;
  var basketId = user.basketId;
  fetch(`/basket/${basketId}?productId=${productId}`, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      document.getElementById("message-div").setAttribute("class", "alert-success");
      document.getElementById("message-div").innerHTML = jsonData.messages;
    })
    .then(function (jsonData) {
      fetchList();
    });

}

function increaseAmount() {
  var productId = this["raw-data"].product.id;
  var basketId = user.basketId;
  var quantity = 1;
  fetch(`/basket?quantity=${quantity}&productId=${productId}`, {
      method: "POST"
    })
    .then(function (response) {
      return response.json();

    })
    .then(function (jsonData) {
      fetchList();
    });
}

function decreaseAmount() {
  var productId = this["raw-data"].product.id;
  var basketId = user.basketId;
  var quantity = 1;
  fetch(`/basket/${basketId}/${productId}?quantity=${quantity}`, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      fetchList();
    });
}

function fetchAddresses() {
  fetch(`/basket/addresses`)
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      loadAddresses(jsonData);
    });
}

function loadAddresses(jsonData) {
  var addressInfoForm = document.getElementById('address-information-form');
  addressInfoForm.innerHTML = '';
  if (jsonData.length == 0) {
    document.getElementById('message-div').setAttribute('class', 'alert-danger');
    document.getElementById('message-div').innerHTML = 'Kérlek, adj meg egy szállítási címet!';
  } else {
    for (var i = 0; i < jsonData.length; i++) {
      var address = jsonData[i];
      addressInfoForm.innerHTML += `<input type="radio" name="address" class="radio" value="${address}" onclick="selectAddress()">${address}<br>`;
    }
  }
  addressInfoForm.innerHTML += `<input type="radio" name="address" class="radio" value="new address" id="new-address-radio" onclick="selectAddress()" checked>
                                  <input type="text" id="new-address" maxlength="200" placeholder="új cím megadása"><br>`;
}

function selectAddress() {
  var newAddressRadio = document.getElementById('new-address-radio');
  if (!newAddressRadio.checked) {
    document.getElementById('new-address').disabled = true;
    return false;
  } else {
    document.getElementById('new-address').disabled = false;
    return false;
  }
}