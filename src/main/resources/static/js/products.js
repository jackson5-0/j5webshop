window.onload = function () {
    fetchProduct();
    var user = fetchUser();
};

function fetchProduct() {
    var address = (new URL(document.location)).searchParams.get('address');
    fetch(`/products/${address}`)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            document.getElementById("code").innerHTML = jsonData.code;
            document.getElementById("name").innerHTML = jsonData.name;
            document.getElementById("address").innerHTML = jsonData.address;
            document.getElementById("publisher").innerHTML = jsonData.publisher;
            document.getElementById("price").innerHTML = jsonData.price;

            var addToBasket = document.getElementById('addBasket');
            addToBasket.innerHTML = 'Kosárba';
            addToBasket.onclick = addBasket;
            addToBasket["raw-data"] = jsonData.id;
        });
}
function addBasket(){
   var request = {
           "basket_id" : user.basketId,
           "id": this["raw-data"]
           }
   fetch(`/addtobasket/${user.basketId}`, {
            method: "PUT",
            body: JSON.stringify(request),
            headers: {
                 "Content-type" : "application/json"
                 }
             })
             .then(function(response) {
                         return response.json();
              })
             .then(function (jsonData) {
                   if (jsonData.status=='SUCCESS') {
                     document.getElementById("message-div").setAttribute("class", "alert alert-success");
                     document.getElementById("message-div").innerHTML = jsonData.messages;
                   } else {
                     document.getElementById("message-div").setAttribute("class", "alert alert-danger");
                     document.getElementById("message-div").innerHTML = jsonData.messages;
                     }
                 });
               return false;
}