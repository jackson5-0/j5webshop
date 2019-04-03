fetchProducts();
document.getElementById("save-new-product").addEventListener('click', handleCreateForm);
document.getElementById("new-product-categories").addEventListener('click', function () {
  showCategoryOfItem('new-product-categories');
  saveProductIdToRawDataSaveButton('new-product-categories');
});


function fetchProducts() {
  fetchPageNavigators();
  var pageNum = (new URL(document.location)).searchParams.get('page');
  var start = (parseInt(pageNum, 10) - 1) * 10;
  var url = `/admin/products?start=${start || 0}&size=${10}`;
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
  pages.innerHTML = "";
  var numberOfPages = Math.ceil(jsonData / 10);
  for (var i = 1; i <= numberOfPages && i < 10; i++) {
    pages.innerHTML += `<a href="adminproducts.html?page=${i}">${i}</a>`;
  }
}

function showProducts(jsonData) {
  var tableBody = document.getElementById('products-tablebody');
  tableBody.innerHTML = "";
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    tr.setAttribute('id', 'product-row-' + jsonData[i].id);

    var codeTd = document.createElement('td');
    codeTd.innerHTML = jsonData[i].code;
    codeTd.setAttribute('id', 'code-Td' + jsonData[i].id)

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData[i].name;
    nameTd.setAttribute('id', 'name-Td' + jsonData[i].id)

    var addressTd = document.createElement('td');
    addressTd.innerHTML = jsonData[i].address;
    addressTd.setAttribute('id', 'address-Td' + jsonData[i].id)

    var publisherTd = document.createElement('td');
    publisherTd.innerHTML = jsonData[i].publisher;
    publisherTd.setAttribute('id', 'publisher-Td' + jsonData[i].id)

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData[i].price + " Ft";
    priceTd.setAttribute('id', 'price-Td' + jsonData[i].id)

    var statusTd = document.createElement('td');
    statusTd.innerHTML = jsonData[i].status;
    statusTd.setAttribute('id', 'status-Td' + jsonData[i].id)

    var buttonTd = document.createElement('td');
    var deleteButton = document.createElement('button');
    deleteButton.innerHTML = "Törlés";
    deleteButton.onclick = handleDelete;
    deleteButton.setAttribute('id', `delete-${jsonData[i].id}`)
    deleteButton["raw-data"] = jsonData[i].id;

    var modifyButton = document.createElement('button');
    modifyButton.innerHTML = "Szerkesztés";
    modifyButton.onclick = handleEdit;
    modifyButton.setAttribute('id', `modify-${jsonData[i].id}`);
    modifyButton["raw-data"] = jsonData[i];

    var category = document.createElement('button');
    category.innerHTML = 'Kategóriák';
    category['raw-data'] = jsonData[i].categories;
    category.setAttribute('id', jsonData[i].code);
    category.setAttribute('class', 'td-button');
    category.style.display = "none";

    addEventListenerToCategoryButton(category, jsonData[i]);

    buttonTd.appendChild(deleteButton);
    buttonTd.appendChild(modifyButton);
    buttonTd.appendChild(category);

    tr.appendChild(codeTd);
    tr.appendChild(nameTd);
    tr.appendChild(addressTd);
    tr.appendChild(publisherTd);
    tr.appendChild(priceTd);
    tr.appendChild(statusTd);
    tr.appendChild(buttonTd);
    tableBody.appendChild(tr);
  }
}

function addEventListenerToCategoryButton(category, product) {
  category.addEventListener('click', function () {
    showCategoryOfItem(product.code);
    saveProductIdToRawDataSaveButton(product.code);
    var title = document.getElementById('product-name');
    title.innerHTML = product.name;
  });
}

function handleCreateForm() {
  var name = document.getElementById("name-input").value;
  var publisher = document.getElementById("publisher-input").value;
  var price = document.getElementById("price-input").value;
  var categories = document.getElementById("new-product-categories")['raw-data'];
  var request = {
    "name": name,
    "publisher": publisher,
    "price": price,
    "categories": categories
  };
  fetch('/admin/products', {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-type": "application/json"
      }
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      var messageDiv = document.getElementById("message-div");
      messageDiv.innerHTML = "";
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("name-input").value = "";
        document.getElementById("publisher-input").value = "";
        document.getElementById("price-input").value = "";
        fetchProducts();
        messageDiv.setAttribute("class", "alert alert-success");
      } else {
        messageDiv.setAttribute("class", "alert alert-danger");
      }
      for (var i = 0; i < jsonData.messages.length; i++) {
        document.getElementById("message-div").innerHTML += jsonData.messages[i] + "<br>";
      }

    });
  return false;
}

function deleteProduct(id) {
  if (!confirm("Biztosan törölni szeretnéd ezt a terméket?")) {
    return;
  }
  console.log('cica');
  fetch(`/admin/products?id=${id}`, {
      method: "DELETE" //vagy mégis DELETE? Akkor nem kell a köv 4 sor!
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("message-div").setAttribute("class", "alert alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchProducts();
      }
    });
}

function updateProduct(data) {
  var id = data.id;
  var code = document.getElementById('code-Td' + id).innerHTML;
  var name = document.getElementById('name-Td' + id).innerHTML;
  var address = document.getElementById('address-Td' + id).innerHTML;
  var publisher = document.getElementById('publisher-Td' + id).innerHTML;
  var price = document.getElementById('price-Td' + id).innerHTML.replace(' Ft', '');
  var categories = document.getElementById(code)['raw-data'];
  var request = {
    "id": id,
    "code": code,
    "name": name,
    "address": address,
    "publisher": publisher,
    "price": price,
    "categories": categories
  };
  fetch(`/admin/products`, {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        "Content-type": "application/json"
      }
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("message-div").setAttribute("class", "alert alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchProducts();
      }
      if (jsonData.status == 'FAIL') {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchProducts();
      }
    });
  return false;
}

function handleEdit() {
  var row = document.getElementById(`product-row-${this['raw-data'].id}`);
  if (row.getAttribute('class')) {
    row.classList.remove('under-edit');
    updateProduct(this['raw-data']);
  } else {
    var editButton = document.getElementById(`modify-${this['raw-data'].id}`);
    var cancelButton = document.getElementById(`delete-${this['raw-data'].id}`)
    var categoryButton = document.getElementById(this['raw-data'].code);
    row.setAttribute('class', 'under-edit');
    setCalssNameForEdit(row);
    removeEditableFromOtherRows(row);
    categoryButton.style.display = "inline-block";
    editButton.innerHTML = 'Mentés';
    cancelButton.innerHTML = 'Mégse';
  }
}


function handleDelete() {
  var row = document.getElementById(`product-row-${this['raw-data']}`);
  if (row.getAttribute('class')) {
    row.classList.remove('under-edit');
    fetchProducts();
  } else {
    deleteProduct(this['raw-data']);
  }
}


function setCalssNameForEdit(row) {
  var elements = row.getElementsByTagName('td');
  for (var i = 0; i < 6; i++) {
    elements[i].setAttribute('class', 'under-edit');
    if (i < 5) {
      elements[i].contentEditable = "true";
    }
  }
}

function removeEditableFromOtherRows(actualRow) {
  var table = document.getElementById('products-table');
  var rows = table.getElementsByTagName('tr');
  for (var i = 0; i < rows.length; i++) {
    if (rows[i] != actualRow && rows[i].getAttribute('class') == 'under-edit') {
      var editButton = rows[i].getElementsByTagName('td')[6].lastElementChild;
      var cancelButton = rows[i].getElementsByTagName('td')[6].firstElementChild;
      editButton.innerHTML = 'Szerkesztés';
      cancelButton.innerHTML = 'Törlés';
      var children = rows[i].getElementsByTagName('td');
      rows[i].classList.remove('under-edit');
      for (var k = 0; k < 6; k++) {
        children[k].classList.remove('under-edit');
        children[k].contentEditable = "false";
      }
    }
  }
}