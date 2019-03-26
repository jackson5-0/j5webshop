document.forms["user-registration-form"].onsubmit = handleCreateForm;

function handleCreateForm() {
    var lastName = document.getElementById("lastname-input").value;
    var firstName = document.getElementById("firstname-input").value;
    var username = document.getElementById("username-input").value;
    var password1 = document.getElementById("password-input").value;
    var password2 = document.getElementById("password-again-input").value;
    if (password1 != password2){
        document.getElementById("message-div").setAttribute("class", "alert alert-danger");
        document.getElementById("message-div").innerHTML = "A két jelszó nem egyezik!";
    } else {
        var request = {
            "firstName" : firstName,
            "lastName" : lastName,
            "userName" : username,
            "password" : password1,
        };
        fetch('/users', {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                "Content-type" : "application/json"
            }
        }).then(function(response) {
            return response.json();
        })
        .then(function(jsonData) {
            if (jsonData.status == 'SUCCESS') {
                document.getElementById("firstname-input").value = "";
                document.getElementById("lastname-input").value = "";
                document.getElementById("username-input").value = "";
                document.getElementById("password-input").value = "";
                document.getElementById("password-again-input").value = "";
                document.getElementById("message-div").setAttribute("class", "alert alert-success");
            } else {
                document.getElementById("message-div").setAttribute("class", "alert alert-danger");
            }
            document.getElementById("message-div").innerHTML = jsonData.messages[0];
        });
        return false;
    }
    return false;
}