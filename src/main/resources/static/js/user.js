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
      <div class="logo-div">
          <a href="/index.html"><img src="/img/j5logo.png" alt="logo" class="logo"></a>
      </div>
      <div class="login-out-div">
          <a href="/logout">kijelentkezés</a>
      </div>
  </div>
  <div class="navigation">
      <div class="nav-div">
         <ul>
            <li><a href="adminproducts.html">termékek</a></li>
            <li><a href="adminusers.html">felhasználók</a></li>
            <li><a href="orders.html">megrendelések</a></li>
            <li><a href="categories.html">kategóriák</a></li>
            <li><a href="reports.html">riportok</a></li>
            <li><a href="dashboard.html">dashboard</a></li>
         </ul>
     </div>
  </div>
  `;
}

function createDefaultHeader(user) {
  var nav = document.querySelector(".nav");
  nav.innerHTML =
    `<div id="header-top">
        <div class="logo-div">
            <a href="/index.html"><img src="/img/j5logo.png" alt="logo" class="logo"></a>
        </div>
        <div class="login-out-div">
            <a href="register.html">regisztráció</a>
            <a href="/login.html">bejelentkezés</a>
        </div>
    </div>
    <div class="navigation">
        <div class="nav-div">
            <ul>
            
            </ul>
        </div>
     </div>`;
}

function createUserHeader(user) {
  var nav = document.querySelector(".nav");
  nav.innerHTML =
    `<div id="header-top">
        <div class="logo-div">
            <a href="/index.html"><img src="/img/j5logo.png" alt="logo" class="logo"></a>
        </div>
        <div class="login-out-div">
            <span style="margin-right:20px"><a href="userprofile.html">Szia ${user.userName}!</a></span>
            <a href="/logout">kijelentkezés</a>
        </div>
    </div>
    <br>
    <div class="navigation">
      <div class="nav-div">
         <ul>
            <li><a href="userprofile.html">személyes adataim</a></li>
            <li><a href="useraddresses.html">szállítási címeim</a></li>
            <li><a href="myorders.html">rendeléseim</a></li>
         </ul>
      </div>
     <a href="/basket.html"><img src="/img/cart.png" alt="basket" class="cart-img"></a>
  </div>`;
}