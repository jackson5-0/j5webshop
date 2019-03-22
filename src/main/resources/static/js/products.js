window.onload = function () {
    fetchProduct();
    fetchUser();
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
    console.log(user);      //!!!!!!
   id = this["raw-data"];
   var basketId = 1; //Honnan kéne jönnie?
   var request = {
           "basket_id" : basketId,
           "id":id
           }
   fetch(`/addtobasket/${basketId}`, {
            method: "POST",
            body: JSON.stringify(request),
            headers: {
                 "Content-type" : "application/json"
                 }
             });
    console.log(id);
}