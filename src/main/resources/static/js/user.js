//var user;
//
//function fetchUser() {
//    fetch('/user')
//        .then(function (response) {
//            return response.json();
//        })
//        .then(function (jsonData) {
//            user = jsonData;
//
//        });
//}
var user;
fetchUser();

function fetchUser() {
  fetch('/user')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      var userRole = jsonData.userRole;
      if (userRole == "ROLE_ADMIN") {
        createAdminHeader(jsonData);
      } else if (userRole == "ROLE_USER") {
        createUserHeader(jsonData);
      } else {
        createDefaultHeader(jsonData);
      }
      user = jsonData;

    });
}

function createAdminHeader(admin) {
  var nav = document.querySelector(".nav");
  nav.innerHTML = `
  <div id="header-top">
    <a href="/logout">kijelentkezés</a>
  </div>
  <div class="navigation">
    <ul>
      <li><a href="adminproducts.html">termékek</a></li>
      <li><a href="manageusers.html">felhasználók</a></li>
    </ul>
  </div>
  `;
}

function createDefaultHeader(user) {
  var nav = document.querySelector(".nav");
  nav.innerHTML =
    `<div id="header-top">
    <a href="register.html">regisztráció</a>
    <a href="/login">bejelentkezés</a>
  </div>
  <div class="navigation">
    <ul>
      <li><a href="index.html">főoldal</a></li>
      <li><a href="">Menu2</a></li>
      <li><a href="">Menu3</a></li>
    </ul>
  </div>`;
}

function createUserHeader(user) {
  console.log(user);
  var nav = document.querySelector(".nav");
  nav.innerHTML =
    `<div id="header-top">
    <span>Szia ${user.userName}!</span>
    <a href="/logout">kijelentkezés</a>
    <a href="/basket.html?basket=${user.basketId}"><img src="/img/cart.png" alt="basket" class="cart-img"></a>
  </div>
  <div class="navigation">
    <ul>
      <li><a href="index.html">főoldal</a></li>
      <li><a href="">Menu2</a></li>
      <li><a href="">Menu3</a></li>
    </ul>
  </div>`;
}