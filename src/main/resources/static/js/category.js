fetchProducts();
function fetchProducts() {
  fetchPageNavigators();
  var pageNum = (new URL(document.location)).searchParams.get('page');
  var category = (new URL(document.location).searchParams.get('category'));
  var start = (parseInt(pageNum, 10) - 1) * 12;
  var url = `/products?start=${start || 0}&size=${12}&category=${category}`;
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showProducts(jsonData);
    });
}

function fetchPageNavigators() {
  fetch('/products/count')
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
  for (var i = 1; i <= numberOfPages; i++) {
    pages.innerHTML += `<a href="category.html?page=${i}">${i}</a>`;
  }
}

function showProducts(jsonData) {
    var products = document.getElementById('products');
    products.innerHTML = `<h1>${jsonData[0].name}</h1>`;
        for (var i = 0; i < jsonData[0].products.length; i++) {
                var product = document.createElement('div');
                product.setAttribute('class', 'product');
                product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[0].products[i].address}"`);

                var img = document.createElement('img');
                img.setAttribute('class', 'picture');
                img.setAttribute('src', '/img/fantasy_game_dice.jpg');
                img.setAttribute('alt', 'picture of the game');
                var name = document.createElement('span');
                name.setAttribute('class', 'name');
                var price = document.createElement('span');
                price.setAttribute('class', 'price');

                name.innerHTML = jsonData[0].products[i].name;
                price.innerHTML = jsonData[0].products[i].price + ' Ft';

                product.appendChild(img);
                product.appendChild(name);
                product.appendChild(price);

                products.appendChild(product);
        }
}