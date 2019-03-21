fetchProducts();
document.forms["product-registration-form"].onsubmit = handleCreateForm;

function fetchProducts() {
  fetchPageNavigators();
  var pageNum = (new URL(document.location)).searchParams.get('page');
  var start = (parseInt(pageNum, 10) - 1) * 10;
  var url = `/products?start=${start || 0}&size=${10}`;
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showProducts(jsonData);
    });
}

function fetchPageNavigators() {
  fetch('products/count')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showPageNavigator(jsonData);
    });
}

function showPageNavigator(jsonData) {
  var pages = document.getElementById('page-number');
  var numberOfPages = Math.ceil(jsonData / 10);
  for (var i = 1; i <= numberOfPages && i < 10; i++) {
    pages.innerHTML += `<a href="index.html?page=${i}">${i}</a>`;
  }
}

function showProducts(jsonData) {
    var tableBody = document.getElementById('products-tablebody');
    tableBody.innerHTML = "";
    for(var i = 0; i < jsonData.length; i++) {
        var tr = document.createElement('tr');
        tr["raw-data"] = jsonData[i]; //ez eredetileg a delete-button raw-datája volt, ha így nem működne...

        var codeTd = document.createElement('td');
        codeTd.contentEditable = "true";
//        codeTd.onchange = updateProduct();
        codeTd.innerHTML = jsonData[i].code;

        var nameTd = document.createElement('td');
        nameTd.contentEditable = "true";
//        nameTd.onchange = updateProduct();
        nameTd.innerHTML = jsonData[i].name;

        var addressTd = document.createElement('td');
        addressTd.contentEditable = "true";
//        addressTd.onchange = updateProduct();
        addressTd.innerHTML = jsonData[i].address;

        var publisherTd = document.createElement('td');
        publisherTd.contentEditable = "true";
//        publisherTd.onchange = updateProduct();
        publisherTd.innerHTML = jsonData[i].publisher;

        var priceTd = document.createElement('td');
        priceTd.contentEditable = "true";
//        priceTd.onchange = updateProduct();
        priceTd.innerHTML = jsonData[i].price;

        var statusTd = document.createElement('td');
        statusTd.innerHTML = jsonData[i].status;

        var buttonTd = document.createElement('td');
        var deleteButton = document.createElement('button');
        deleteButton.innerHTML = "Termék törlése";
        deleteButton.onclick = deleteProduct;
        buttonTd["raw-data"] = jsonData[i].id;
        buttonTd.appendChild(deleteButton);

        tr.appendChild(nameTd);
        tr.appendChild(publisherTd);
        tr.appendChild(priceTd);
        tr.appendChild(statusTd);
        tr.appendChild(buttonTd);
        tableBody.appendChild(tr);
    }
}

function handleCreateForm() {
    var name = document.getElementById("name-input").value;;
    var publisher = document.getElementById("publisher-input").value;;
    var price = document.getElementById("price-input").value;;
    var request = {
        "name" : name,
        "publisher" : publisher,
        "price" : price
    };
    fetch('/api/products', {
    method: "POST",
    body: JSON.stringify(request),
    headers: {
        "Content-type" : "application/json"
    }
    })
      .then(function(response) {
        return response.json();
      })
      .then(function(jsonData) {
         if (jsonData.status == 'SUCCESS') {         //?
            document.getElementById("name-input").value = "";
            document.getElementById("publisher-input").value = "";
            document.getElementById("price-input").value = "";
            fetchProducts();
            document.getElementById("message-div").setAttribute("class", "alert alert-success");
         } else {
            document.getElementById("message-div").setAttribute("class", "alert alert-danger");
         }
           document.getElementById("message-div").innerHTML = jsonData.message;
      });
    return false;
}

function deleteProduct() {
    var id = this["raw-data"].id;
    if(!confirm("Biztosan törölni szeretnéd ezt a terméket?")) {
        return;
    }
    fetch("/api/products/" + id, {
        method: "POST",      //vagy mégis DELETE? Akkor nem kell a köv 4 sor!
        body: JSON.stringify(request),
        headers: {
           "Content-type": "application/json"
        }
    })
    .then(function(response){
            return response.json();
    }).then(function(jsonData) {
             if (jsonData.status == 'SUCCESS') {
                 document.getElementById("message-div").setAttribute("class", "alert alert-success");
                 document.getElementById("message-div").innerHTML = jsonData.message;
                 fetchProducts();
             }
    });
}

//function updateProduct() {
//    var id = this["raw-data"].id;           //vagy =(new URL(document.location)).searchParams.get("id");
//    var code = this["raw-data"].code;
//    var name = this["raw-data"].name;
//    var address = this["raw-data"].address;
//    var publisher = this["raw-data"].publisher;
//    var price = this["raw-data"].price;
//    var request = {
//            "code": code,
//            "name": name,
//            "address": address,
//            "publisher": publisher,
//            "price": price,
//    };
//
//    fetch("/api/products/" + id, {
//        method: "POST",
//        body: JSON.stringify(request),
//        headers: {
//            "Content-type": "application/json"
//        }
//    })
//    .then(function(response) {
//        document.getElementById("message-div").innerHTML = "A termék módosult!"; //vagy return response.json(); ?
//    });
//    return false;
//}