document.forms["user-information-form"].onsubmit = updateUserDatas;
document.forms["password-modification-form"].onsubmit = changePassword;
fetchUser();

function fetchUser() {
  fetch(`/userprofile`)
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
        var firstNameInput = document.getElementById('firstname-input');
        firstNameInput.value = jsonData.firstName;
        var lastNameInput = document.getElementById('lastname-input');
        lastNameInput.value = jsonData.lastName;
        var userNameInput = document.getElementById('username-input');
        userNameInput.value = jsonData.userName;
        var submitInput = document.getElementById('submit-button');
        submitInput["raw-data"] = jsonData;
        var submitInput2 = document.getElementById('submit2-button');
        submitInput2["raw-data"] = jsonData;
    });
}

function updateUserDatas() {
     var id = document.getElementById('submit-button')["raw-data"].id;
     var firstName = document.getElementById('firstname-input').value;
     var lastName = document.getElementById('lastname-input').value;
     var userName = document.getElementById('username-input').value;
     var password = document.getElementById('password-input').value;
     if (password.length == 0){
        document.getElementById('message-div').setAttribute('class', 'alert-danger');
        document.getElementById('message-div').innerHTML = 'Kérlek, add meg a jelszavad!';
     } else {
        var request = {
            "id" : id,
            "firstName" : firstName,
            "lastName" : lastName,
            "userName" : userName,
            "password" : password,
        };
        fetch(`/userprofile?id=${id}`, {
             method: "POST",
             body: JSON.stringify(request),
             headers: {
                "Content-type" : "application/json"
             }
        }).then(function(response) {
             return response.json();
        }).then(function(jsonData) {
             if (jsonData.status == 'SUCCESS') {
                document.getElementById("message-div").setAttribute("class", "alert-success");
             } else {
                document.getElementById("message-div").setAttribute("class", "alert-danger");
             }
             document.getElementById("message-div").innerHTML = jsonData.messages[0];
             document.getElementById('password-input').value = '';
             createUserHeader(request);
        });
        return false;
     }
     return false;
}

function changePassword() {
    var id = document.getElementById('submit-button')["raw-data"].id;
    var firstName = document.getElementById('firstname-input').firstName;
    var lastName = document.getElementById('lastname-input').lastName;
    var userName = document.getElementById('username-input').userName;
    var oldpassword = document.getElementById('old-password').value;
    var newpassword = document.getElementById('new-password1').value;
    var newpassword2 = document.getElementById('new-password2').value;
    if (newpassword.length == 0){
        document.getElementById('message-div').setAttribute('class', 'alert-danger');
        document.getElementById('message-div').innerHTML = 'Az új jelszó nem lehet üres';
    } else if (newpassword !== newpassword2){
        document.getElementById('message-div').setAttribute('class', 'alert-danger');
        document.getElementById('message-div').innerHTML = 'A két jelszó nem egyezik!';
    } else {
        var request = {
                      	"user" : {
                              	"id" : id,
                              	"firstName" : firstName,
                              	"lastName" : lastName,
                              	"userName" : userName,
                              	"password" : oldpassword
                      	}, "newPassword" : newpassword
                      };
        fetch(`/userprofile?id=${id}`, {
             method: "PUT",
             body: JSON.stringify(request),
             headers: {
                  "Content-type" : "application/json"
             }
        }).then(function(response) {
             return response.json();
        }).then(function(jsonData) {
             if (jsonData.status == 'SUCCESS') {
                  document.getElementById("message-div").setAttribute("class", "alert-success");
             } else {
                  document.getElementById("message-div").setAttribute("class", "alert-danger");
             }
             document.getElementById("message-div").innerHTML = jsonData.messages[0];
             document.getElementById('old-password').value = '';
             document.getElementById('new-password1').value = '';
             document.getElementById('new-password2').value = '';
             fetchUser;
        });
        return false;
    }
    return false;
}