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
  var tableRow = '';
  console.log(jsonData[0].orderedProduct);
  for (var i = 0; i < jsonData.length; i++) {
    tableRow += `<tr class="tableLine${i}" class="orderlist" tr>
                      <td class="orderlist__table__td">${jsonData[i].userId}</td>
                      <td class="orderlist__table__td">${jsonData[i].purchaseDate}</td>
                      <td class="orderlist__table__td">${jsonData[i].orderStatus}</td>
                    </tr>
                    `;

    for(var j = 0; j < jsonData[i].orderedProduct.length; j++){
        tableRow += `<tr class="orderedItemList">
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].name}</td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].quantity} db </td>
                                              <td class="orderedItemList__table__td">${jsonData[i].orderedProduct[j].priceAtPurchase} Ft</td>
                                           </tr>
                                         `;
    }

  } tbody.innerHTML = tableRow;
}