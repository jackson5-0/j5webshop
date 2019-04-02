fetchUsers();

function fetchUsers() {
  fetch(`/admin/users`)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showUsers(jsonData);
    });
}

function showUsers(jsonData) {
  var tableBody = document.getElementById('users-tablebody');
  tableBody.innerHTML = "";
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    var firstNameTd = document.createElement('td');
    firstNameTd.contentEditable = "true";
    firstNameTd.innerHTML = jsonData[i].firstName;
    firstNameTd.setAttribute('id', 'firstName-Td' + jsonData[i].id)

    var lastNameTd = document.createElement('td');
    lastNameTd.contentEditable = "true";
    lastNameTd.innerHTML = jsonData[i].lastName;
    lastNameTd.setAttribute('id', 'lastName-Td' + jsonData[i].id)

    var userNameTd = document.createElement('td');
    userNameTd.contentEditable = "true";
    userNameTd.innerHTML = jsonData[i].userName;
    userNameTd.setAttribute('id', 'userName-Td' + jsonData[i].id)

    var passwordTd = document.createElement('td');
    passwordTd.contentEditable = "true";
    passwordTd.innerHTML = "**********"
    passwordTd["raw-data"] = jsonData[i].password;
    passwordTd.setAttribute('id', 'password-Td' + jsonData[i].id)

    var buttonTd = document.createElement('td');
    var deleteButton = document.createElement('button');
    deleteButton.innerHTML = "Törlés";
    deleteButton.onclick = deleteUser;
    deleteButton["raw-data"] = jsonData[i].id;

    var modifyButton = document.createElement('button');
    modifyButton.innerHTML = "Mentés";
    modifyButton.onclick = updateUser;
    modifyButton["raw-data"] = jsonData[i];

    buttonTd.appendChild(deleteButton);
    buttonTd.appendChild(modifyButton);

    tr.appendChild(firstNameTd);
    tr.appendChild(lastNameTd);
    tr.appendChild(userNameTd);
    tr.appendChild(passwordTd);
    tr.appendChild(buttonTd);
    tableBody.appendChild(tr);
  }
}

function deleteUser() {
  var id = this["raw-data"];
  if (!confirm("Biztosan törölni szeretnéd ezt a felhasználót?")) {
    return;
  }
  fetch(`/admin/users?id=${id}`, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.status == 'SUCCESS') {
        document.getElementById("message-div").setAttribute("class", "alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchUsers();
      } else {
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
      }
    });
}

function updateUser() {
  var id = this["raw-data"].id;
  var firstName = document.getElementById('firstName-Td' + id).innerHTML;
  var lastName = document.getElementById('lastName-Td' + id).innerHTML;
  var username = document.getElementById('userName-Td' + id).innerHTML;
  var password;
  if (document.getElementById('password-Td' + id).innerHTML.indexOf("*") > -1) {
        password = document.getElementById('password-Td' + id)["raw-data"];
  } else {
        password = document.getElementById('password-Td' + id).innerHTML;
  }
  var request = {
    "id": id,
    "firstName": firstName,
    "lastName": lastName,
    "userName": username,
    "password": password
  };
  fetch(`/admin/users?id=${id}`, {
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
        document.getElementById("message-div").setAttribute("class", "alert-success");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchUsers();
      }
      if (jsonData.status == 'FAIL') {
        document.getElementById("message-div").setAttribute("class", "alert-danger");
        document.getElementById("message-div").innerHTML = jsonData.messages[0];
        fetchUsers();
      }
    });
  return false;
}