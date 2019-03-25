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
  var tbody = document.querySelector('#orderlist');
  var thead = `<th class="tablehead" class="orderlist">
                    <td class="orderlist__table__th">Rendelés száma</td>
                    <td class="orderlist__table__th">Rendelés dátuma</td>
                    <td class="orderlist__table__th">Rendelés státusza</td>
                    </th>`
  var orderedProductHead = `<th class="tablehead" class="orderlist">
                                           <td class="orderlist__table__th">Termék neve</td>
                                           <td class="orderlist__table__th">Darabszám</td>
                                           <td class="orderlist__table__th">Termék ára</td>
                                           </th>`
  var tableRow = '';



  for (var i = 0; i < jsonData.length; i++) {
    tableRow += `<tr class="tableLine${i}" class="orderlist">
                      <td class="orderlist__table__td">#${jsonData[i].id}</td>
                      <td class="orderlist__table__td">${jsonData[i].purchaseDate}</td>
                      <td class="orderlist__table__td">${jsonData[i].orderStatus}</td>
                    </tr>
                    ` + orderedProductHead;

    for(var j = 0; j < jsonData[i].orderedProduct.length; j++){
        tableRow += `<tr class="orderedItemList">
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].name}</td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].quantity} db </td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].priceAtPurchase} Ft</td>
                                           </tr>
                                         `;
    }

  } tbody.innerHTML = thead+tableRow;
}