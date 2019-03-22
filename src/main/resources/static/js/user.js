window.onload = function() {
    fetchUser();
};

function fetchUser() {
    var userName = '';
    var role = '';
    fetch('/user')
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            userName = jsonData.username;
            role = jsonData.role;

            //itt hívjuk majd meg a userekkel végzendő metódusokat!

        });
}
