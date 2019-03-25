window.onload = fetchList;

function fetchList() {
  var order = (new URL(document.location)).searchParams.get('order');
  fetch(`/order/${order}`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
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

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData[i].price;

    var guantityTd = document.createElement('td');
    guantityTd.innerHTML = 1;

    var delTd = document.createElement('td');

    var delBut = document.createElement('button');
    delBut.innerHTML = "Törlés";
    delBut.onclick = deleteOrderItem;
    delBut["raw-data"] = jsonData[i];

    tr.appendChild(userNameTd);
    tr.appendChild(purchaseDateTd);
    tr.appendChild(orderStatusTd);
    delTd.appendChild(delBut);
    tr.appendChild(delTd);

    tbody.appendChild(tr);
  }
}

function deleteOrderItem() {
  var product = this["raw-data"].id;
  if (!confirm("Biztosan törölni szeretné a tételt?")) {
          return;
      }
   fetch(`/order/${order}/${product}`, {
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
