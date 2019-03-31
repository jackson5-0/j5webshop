window.onload = fetchList;
var flush = document.getElementById('flush');
flush.onclick = flushBasket;

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
  var basketId = user.basketId;
  if (!confirm("Biztosan ki szeretné üríteni a kosarát?")) {
         return;
  }
  fetch(`/basket`, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
       if(jsonData.status == 'SUCCESS') {
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.getElementById("message-div").innerHTML = jsonData.messages;
            return jsonData;
       } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
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
    sum += jsonData[i].product.price * jsonData[i].quantity ;

    var qtyTd = document.createElement('td');
    qtyTd.innerHTML = jsonData[i].quantity;

    var sumItemPriceTd = document.createElement('td');
    sumItemPriceTd.innerHTML = jsonData[i].product.price * jsonData[i].quantity + " Ft";

    var increaseAmountTd = document.createElement('td');
    var increaseAmountBut = document.createElement('button')
        increaseAmountBut.innerHTML = "+";
        increaseAmountBut.onclick = increaseAmount;
        increaseAmountBut["raw-data"] = jsonData[i];
    var decreaseAmountTd = document.createElement('td');
    var decreaseAmountBut = document.createElement('button')
        decreaseAmountBut.innerHTML = "-";
        decreaseAmountBut.onclick = decreaseAmount;
        decreaseAmountBut["raw-data"] = jsonData[i];

    var delTd = document.createElement('td');

    var delBut = document.createElement('button')
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
  fetch(`/myorders?basketId=${basketId}`, {
      method: "POST"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
        if(jsonData.status == 'SUCCESS') {
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
            document.getElementById("message-div").innerHTML = jsonData.messages;
            return jsonData;
        } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
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
    if (!confirm("Biztosan el szeretné távolítani a terméket a kosárból?")) {
            return;
        }
     fetch(`/basket/${basketId}?productId=${productId}`, {
           method: "DELETE"
         })
         .then(function (response) {
           return response.json();
         })
         .then(function (jsonData) {
           document.getElementById("message-div").setAttribute("class", "alert alert-success");
           document.getElementById("message-div").innerHTML = jsonData.messages;
         })
         .then(function (jsonData) {
           fetchList();
         });

}
function increaseAmount(){
        var productId = this["raw-data"].product.id;
        var basketId = user.basketId;
        var quantity = 1;
            fetch(`/basket?quantity=${quantity}&productId=${productId}`,
                {method: "POST"})
                     .then(function(response) {
                                 return response.json();

                      })
                     .then(function (jsonData) {
                                fetchList();
                              });
}
function decreaseAmount(){
        var productId = this["raw-data"].product.id;
        var basketId = user.basketId;
        var quantity = 1;
            fetch(`/basket/${basketId}/${productId}?quantity=${quantity}`,
                {method: "DELETE"})
                     .then(function(response) {
                                 console.log(response);
                                 return response.json();

                      })
                     .then(function (jsonData) {
                                fetchList();
                              });
}
