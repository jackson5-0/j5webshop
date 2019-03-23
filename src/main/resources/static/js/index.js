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

//function getPortraits(arrayOfCharacters) {
//  var tableRow = '';
//  for (var i = 0; i < arrayOfCharacters.length; i++) {
//    if (arrayOfCharacters.hasOwnProperty(i) && !arrayOfCharacters[i].dead) {
//      tableRow += `
//            <div class="portraits">
//              <img class="pics" id="${i}" src="/${
//  arrayOfCharacters[i].portrait
//}" alt=""><br>
//            <label for="${i}" class="names">${arrayOfCharacters[i].name}</label>
//              </div>
//  `;
//    }
//  }
//  document.querySelector('#pics').innerHTML = tableRow;
//}
function showProducts(jsonData) {
  var allProducts = document.getElementById('products');
  allProducts.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var product = document.createElement('div');
    product.setAttribute('class', 'product');
    product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[i].address}"`);

    var img = document.createElement('img');
    img.setAttribute('class', 'picture');
    img.setAttribute('src', '/img/fantasy_game_dice.jpg');
    img.setAttribute('alt', 'picture of the game');
    var name = document.createElement('span');
    name.setAttribute('class', 'name');
    var price = document.createElement('span');
    price.setAttribute('class', 'price');

    name.innerHTML = jsonData[i].name;
    price.innerHTML = jsonData[i].price + ' Ft';

    product.appendChild(img);
    product.appendChild(name);
    product.appendChild(price);

    allProducts.appendChild(product);
  }
}
