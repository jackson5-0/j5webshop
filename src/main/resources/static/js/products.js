var user = fetchUser();
fetchProduct();

function fetchProduct() {
    var address = (new URL(document.location)).searchParams.get('address');
    fetch(`/products/${address}`)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            if (jsonData.product) {
                document.getElementById("code").innerHTML = jsonData.product.code;
                document.getElementById("name").innerHTML = jsonData.product.name;
                document.getElementById("address").innerHTML = jsonData.product.address;
                document.getElementById("publisher").innerHTML = jsonData.product.publisher;
                document.getElementById("price").innerHTML = jsonData.product.price + " Ft";
            } else {
                document.querySelector(".product-info-div").innerHTML = jsonData.message;
            }
            if (user.userRole == "ROLE_USER") {
                var addBasketTd = document.getElementById('add-to-basket');
                var addBasketButton = document.createElement('button');
                addBasketButton.setAttribute('class', 'button');
                addBasketButton.innerHTML = 'Kosárba';
                addBasketButton.onclick = addBasket;
                addBasketButton["raw-data"] = jsonData.product.id;
                addBasketTd.appendChild(addBasketButton);
            }
            var imgDiv = document.getElementById("product-image-div");
            var img = document.createElement('img');
            img.setAttribute('class', 'product-image');
            img.setAttribute('src', '/img/fantasy_game_dice.jpg');
            img.setAttribute('alt', 'picture of the game');
            imgDiv.appendChild(img);
        });
}
function addBasket(){
   var request = {
           "basket_id" : user.basketId,
           "id": this["raw-data"]
           }
   fetch(`/basket?basketId=${user.basketId}`, {
            method: "POST",
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
                     document.getElementById("message-div").setAttribute("class", "alert-success");
                     document.getElementById("message-div").innerHTML = jsonData.messages;
                   } else {
                     document.getElementById("message-div").setAttribute("class", "alert-danger");
                     document.getElementById("message-div").innerHTML = jsonData.messages;
                     }
                 });
               return false;
}
