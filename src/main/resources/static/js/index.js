fetchProducts();

var slideIndex = 0;

function fetchProducts() {
  //  fetchPageNavigators();
  //  var pageNum = (new URL(document.location)).searchParams.get('page');
  //  var start = (parseInt(pageNum, 10) - 1) * 10;
  //  var url = `/products?start=${start || 0}&size=${10}`;
  var url = '/products?start=0&size=3'
  var category = (new URL(document.location)).searchParams.get('category');
  if (!category) {
    fetch(url)
      .then(function (response) {
        return response.json();
      })
      .then(function (jsonData) {
        showProducts(jsonData);
        fetchLast3();
      });
  } else {
    fetchProductsOfCategory();
  }
}

function fetchLast3() {
  fetch('myorders/top3')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showTop3(jsonData);
    });
}

function showTop3(jsonData) {
  console.log(jsonData);
  var top3Div = document.getElementById('top3');
  top3Div.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var product = document.createElement('div');
    product.setAttribute('class', 'product2 slideshow fade');
    product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[i].address}"`);

    var img = document.createElement('img');
    img.setAttribute('class', 'picture');
    if (jsonData[i].image !== null) {
      img.src = "data:image/png;base64," + jsonData[i].image;
    } else {
      img.setAttribute('src', '/img/fantasy_game_dice.jpg');
    }
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
    product.style.display = "none";
    top3Div.appendChild(product);

  }
  showDivs();
}

function showDivs() {
  var i;
  var x = document.getElementsByClassName("slideshow");

  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  slideIndex++;
  if (slideIndex > x.length) {
    slideIndex = 1
  };
  x[slideIndex - 1].style.display = "block";
  setTimeout(showDivs, 6000);
}
//function fetchPageNavigators() {
//  fetch('/categories')
//    .then(function (response) {
//      return response.json();
//    })
//    .then(function (jsonData) {
//      showPageNavigator(jsonData);
//    });
//}
//
//function showPageNavigator(jsonData) {
//  var pages = document.getElementById('page-number');
//  var numberOfPages = Math.ceil(jsonData / 10);
//  for (var i = 1; i <= numberOfPages; i++) {
//    pages.innerHTML += `<a href="index.html?page=${i}">${i}</a>`;
//  }
//}

function showProducts(jsonData) {
  var categoriesDiv = document.getElementById('products');
  categoriesDiv.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var category = document.createElement('div');
    var header = document.createElement('h1');
    header.innerHTML = jsonData[i].name;
    header.setAttribute('onclick', `window.location="/index.html?category=${jsonData[i].name}"`);
    header.setAttribute('class', 'category-header');
    category.appendChild(header);
    category.setAttribute('class', 'categories-div');
    for (var k = 0; k < jsonData[i].products.length; k++) {
      var product = document.createElement('div');

      product.setAttribute('class', 'product');
      product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[i].products[k].address}"`);

      var img = document.createElement('img');
      img.setAttribute('class', 'picture');
      if (jsonData[i].products[k].image != undefined && jsonData[i].products[k].image != "" && jsonData[i].products[k].image != null) {
        img.src = "data:image/png;base64," + jsonData[i].products[k].image;
      } else {
        img.setAttribute('src', '/img/fantasy_game_dice.jpg');
      }
      var name = document.createElement('span');
      name.setAttribute('class', 'name');
      var price = document.createElement('span');
      price.setAttribute('class', 'price');

      name.innerHTML = jsonData[i].products[k].name;
      price.innerHTML = jsonData[i].products[k].price + ' Ft';

      product.appendChild(img);
      product.appendChild(name);
      product.appendChild(price);

      category.appendChild(product);
    }
    categoriesDiv.appendChild(category);
  }
}

//function showProducts(jsonData) {
//  var allProducts = document.getElementById('products');
//  allProducts.innerHTML = '';
//  for (var i = 0; i < jsonData.length; i++) {
//    var product = document.createElement('div');
//    product.setAttribute('class', 'product');
//    product.setAttribute('onclick', `window.location="/products.html?address=${jsonData[i].address}"`);
//
//    var img = document.createElement('img');
//    img.setAttribute('class', 'picture');
//    img.setAttribute('src', '/img/fantasy_game_dice.jpg');
//    img.setAttribute('alt', 'picture of the game');
//    var name = document.createElement('span');
//    name.setAttribute('class', 'name');
//    var price = document.createElement('span');
//    price.setAttribute('class', 'price');
//
//    name.innerHTML = jsonData[i].name;
//    price.innerHTML = jsonData[i].price + ' Ft';
//
//    product.appendChild(img);
//    product.appendChild(name);
//    product.appendChild(price);
//
//    allProducts.appendChild(product);
//  }
//}