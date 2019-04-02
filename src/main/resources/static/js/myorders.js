window.onload = fetchList;

function fetchList() {
  fetch(`/myorders`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showList(jsonData);
    });
}

function showList(jsonData) {
  var sumPrice = 0;
  var tbody = document.querySelector('#orderlist');
  var thead = `<tr class="tablehead" class="orderlist">
                    <th class="orderlist__table__th1">Rendelés azonosítója</th>
                    <th class="orderlist__table__th1">Rendelés dátuma</th>
                    <th class="orderlist__table__th1">Rendelés státusza</th>
                    <th class="orderlist__table__th1">Szállítási cím</th>
                    </tr>`
  var orderedProductHead = `<tr class="tablehead" class="orderlist">
                                           <th class="orderlist__table__th2">Termék neve</th>
                                           <th class="orderlist__table__th2">Darabszám</th>
                                           <th class="orderlist__table__th2">Termék ára</th>
                                           <th class="orderlist__table__th2">Részösszeg</th>
                                           </tr>`

  var tableRow = '';



  for (var i = 0; i < jsonData.length; i++) {
    tableRow += thead +`<tr class="tableLine${i}" class="orderlist">
                      <td class="orderlist__table__td">#${jsonData[i].id}</td>
                      <td class="orderlist__table__td">${jsonData[i].purchaseDate}</td>
                      <td class="orderlist__table__td">${jsonData[i].orderStatus}</td>
                      <td class="orderlist__table__td">${jsonData[i].shippingAddress}</td>
                    </tr>
                    ` + orderedProductHead;

    for(var j = 0; j < jsonData[i].orderedProduct.length; j++){
        sumPrice += jsonData[i].orderedProduct[j].priceAtPurchase * jsonData[i].orderedProduct[j].quantity;
        tableRow += `<tr class="orderedItemList">
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].name}</td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].quantity} db </td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].priceAtPurchase} Ft</td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].priceAtPurchase * jsonData[i].orderedProduct[j].quantity} Ft</td>
                                           </tr>
                                         `;
    }
    tableRow += `<tr class="tablehead" class="orderlist">
                                                 <th class="orderlist__table__th2">Megrendelés végösszege</th>
                                                 </tr>
                                                 <tr class="tablehead" class="orderlist">
                                                      <td class="orderlist__table__th2">${sumPrice}</td>
                                                 </tr>`

  } tbody.innerHTML = tableRow;

}

function showActiveOrders(){
    fetch(`/myorders/active`)
        .then(function (response) {
          return response.json();
        })
        .then(function (jsonData) {
          showList(jsonData);
        });
}
function showAllOrders(){
     fetch(`/myorders/all`)
        .then(function (response) {
          return response.json();
        })
        .then(function (jsonData) {
          showList(jsonData);
        });
}

function openIndexHtml(){
    window.open("/index.html", "_self")}