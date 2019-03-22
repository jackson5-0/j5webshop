fetchProducts();
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
  var allProducts = document.getElementById('products');
  allProducts.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var product = document.createElement('div');

    product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[i].address}"`);

    var code = document.createElement('span');
    var name = document.createElement('span');
    var address = document.createElement('span');
    var publisher = document.createElement('span');
    var price = document.createElement('span');


    code.innerHTML = jsonData[i].code;
    name.innerHTML = jsonData[i].name;
    address.innerHTML = jsonData[i].address;
    publisher.innerHTML = jsonData[i].publisher;
    price.innerHTML = jsonData[i].price;

    product.appendChild(code);
    product.appendChild(name);
    product.appendChild(address);
    product.appendChild(publisher);
    product.appendChild(price);


    allProducts.appendChild(product);
  }
}
