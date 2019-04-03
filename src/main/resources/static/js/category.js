function fetchProductsOfCategory() {
  var pageNum = (new URL(document.location)).searchParams.get('page');
  var category = (new URL(document.location).searchParams.get('category'));
  var start = (parseInt(pageNum, 10) - 1) * 12;
  var url = `/products?start=${start || 0}&size=${12}&category=${category}`;
  fetchPageNavigatorsOfCategory(category);
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showProductsOfCategory(jsonData);
    });
}

function fetchPageNavigatorsOfCategory(category) {
  fetch(`/products/count?category=${category}`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showPageNavigatorOfCategory(jsonData);
    });
}

function showPageNavigatorOfCategory(jsonData) {
  var pages = document.getElementById('page-number');
  var numberOfPages = Math.ceil(jsonData / 12);
  var category = (new URL(document.location).searchParams.get('category'));
  for (var i = 1; i <= numberOfPages; i++) {
    pages.innerHTML += `<a href="index.html?category=${category}&page=${i}">${i}</a>`;
  }
}

function showProductsOfCategory(jsonData) {
  var products = document.getElementById('products');
  products.innerHTML = `<h1>${jsonData[0].name}</h1>`;
  for (var i = 0; i < jsonData[0].products.length; i++) {
    var product = document.createElement('div');
    product.setAttribute('class', 'product');
    product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[0].products[i].address}"`);

    var img = document.createElement('img');
    img.setAttribute('class', 'picture');
    if(jsonData[0].products[i].image!==null){
    img.src =  "data:image/jpg;base64,"+ jsonData[0].products[i].image;
     }else{
          img.setAttribute('src', '/img/fantasy_game_dice.jpg');
          }
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