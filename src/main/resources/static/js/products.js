window.onload=function(){
    fetchProduct();
}

function fetchProduct(){
    var address = (new URL(document.location)).searchParams.get('address');
    fetch(`/products/${address}`)
        .then(function(response){
            return response.json();
            })
        .then(function(jsonData){
           document.getElementById("code").innerHTML = jsonData.code;
           document.getElementById("name").innerHTML = jsonData.name;
           document.getElementById("address").innerHTML = jsonData.address;
           document.getElementById("publisher").innerHTML = jsonData.publisher;
           document.getElementById("price").innerHTML = jsonData.price;
           })
}