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
      console.log(jsonData);
      var userRole = jsonData.userRole;
      if (userRole == "ROLE_ADMIN") {
        console.log("Admin");
      } else if (userRole == "ROLE_USER") {
        console.log("User");
      } else {
        console.log("anyad");
      }
      user = jsonData;

    });
}
