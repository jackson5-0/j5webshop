window.onload = fetchList;
var flush = document.getElementById('flush');
flush.onclick = flushBasket;

function fetchList() {
  var basketId = (new URL(document.location)).searchParams.get('basket');
  fetch(`/basket?basketId=${basketId}`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function flushBasket() {
  var basketId = (new URL(document.location)).searchParams.get('basket');
  fetch(`/basket?basketId=${basketId}`, {
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
    nameTd.innerHTML = jsonData[i].name;

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData[i].price;
    sum += jsonData[i].price;

    var qtyTd = document.createElement('td');
    qtyTd.innerHTML = 1;

    var delTd = document.createElement('td');

    var delBut = document.createElement('button')
    delBut.innerHTML = "Törlés";
    delBut.onclick = deleteBasketItem;
    delBut["raw-data"] = jsonData[i];

    tr.appendChild(nameTd);
    tr.appendChild(priceTd);
    tr.appendChild(qtyTd);
    delTd.appendChild(delBut);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
  var tr2 = document.createElement('tr');
  var totalTd = document.createElement('td');
  var sumTd = document.createElement('td');

  sumTd.innerHTML = "Teljes összeg";
  totalTd.innerHTML = sum;

  tr2.appendChild(sumTd);
  tr2.appendChild(document.createElement('td')); //üres cella
  tr2.appendChild(totalTd);

  tbody.appendChild(document.createElement('br'));
  tbody.appendChild(tr2);
}
function orderBasket() {
  var basketId = (new URL(document.location)).searchParams.get('basket');
  fetch(`/myorders?basketId=${basketId}`, {
      method: "POST"
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      document.getElementById("message-div").setAttribute("class", "alert alert-success");
      document.getElementById("message-div").innerHTML = jsonData.messages;
      return jsonData;
      })
    .then(function (jsonData) {
          showList(jsonData);
    })
    .then(function(){
        window.open("/myorders.html","_self");})
    ;
}

function deleteBasketItem() {
    var productId = this["raw-data"].id;
    var basketId = (new URL(document.location)).searchParams.get('basket');
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
