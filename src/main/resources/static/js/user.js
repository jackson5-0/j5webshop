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
    <a href="/logout">logout</a>
  </div>
  <div>
    <ul>
      <li><a href="adminproducts.html">Products</a></li>
      <li><a href="manageusers.html">Users</a></li>
    </ul>
  </div>
  `;
}

function createDefaultHeader(user) {
  var nav = document.querySelector(".nav");
  nav.innerHTML =
    `<div id="header-top">
    <a href="register.html">register</a>
    <a href="/login">login</a>
  </div>
  <div>
    <ul>
      <li><a href="">Menu1</a></li>
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
    <span>Hello ${user.userName}</span>
    <a href="/logout">logout</a>
    <a href="/basket.html?basket=${user.basketId}"><img src="/img/cart.png"></a>
  </div>
  <div>
    <ul>
      <li><a href="">Menu1</a></li>
      <li><a href="">Menu2</a></li>
      <li><a href="">Menu3</a></li>
    </ul>
  </div>`;
}