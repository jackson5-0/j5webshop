var user;

function fetchUser() {
    fetch('/user')
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            user = jsonData;
        });
}