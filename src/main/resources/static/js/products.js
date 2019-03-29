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
                var quantityInputTd = document.querySelector('#quantity');
                var quantityInput = document.createElement('input');
                quantityInput.setAttribute('id', 'quantityInput');
                quantityInput.setAttribute('type', 'text');
                quantityInput.setAttribute('value', 1);
                quantityInputTd.appendChild(quantityInput);
            }
            var imgDiv = document.getElementById("product-image-div");
            var img = document.createElement('img');
            img.setAttribute('class', 'product-image');
            img.setAttribute('src', '/img/fantasy_game_dice.jpg');
            img.setAttribute('alt', 'picture of the game');
            imgDiv.appendChild(img);
            var username = user.userName;
            var product = jsonData.product;
            createReviewDiv(username, product);
        })
//        .then(function (username, productid) {
//            createReviewDiv(username, productid);
//        })
        ;
}
function addBasket(){
    var quantity = document.querySelector('#quantityInput').value;
    fetch(`/basket?quantity=${quantity}&productId=${this["raw-data"]}`,
        {method: "POST"})
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
function createReviewDiv(username, product) {
        fetch(`/checkifuserhasdeliveredproduct?username=${username}&productid=${product.id}`,
            {method: "GET"})
                 .then(function(response) {
                       return response.json();
                  })
                 .then(function (jsonData) {
                       console.log(jsonData);
                       if (jsonData[0]) {
                         var writeReviewDiv = document.createElement('div');
                         var ratingFieldset  = document.createElement('fieldset');

                         var ratingLegend = document.createElement('legend');
                         ratingLegend.innerHTML = 'Értékelés:'
                         ratingFieldset.appendChild(ratingLegend);

                         var radio1 = document.createElement('input');
                         radio1.setAttribute('type', 'radio');
                         radio1.setAttribute('name', 'rating');
                         radio1.setAttribute('value', '1');
                         ratingFieldset.appendChild(radio1);

                         var radio2 = document.createElement('input');
                         radio2.setAttribute('type', 'radio');
                         radio2.setAttribute('name', 'rating');
                         radio2.setAttribute('value', '2');
                         ratingFieldset.appendChild(radio2);

                         var radio3 = document.createElement('input');
                         radio3.setAttribute('type', 'radio');
                         radio3.setAttribute('name', 'rating');
                         radio3.setAttribute('value', '3');
                         ratingFieldset.appendChild(radio3);

                         var radio4 = document.createElement('input');
                         radio4.setAttribute('type', 'radio');
                         radio4.setAttribute('name', 'rating');
                         radio4.setAttribute('value', '4');
                         ratingFieldset.appendChild(radio4);

                         var radio5 = document.createElement('input');
                         radio5.setAttribute('type', 'radio');
                         radio5.setAttribute('name', 'rating');
                         radio5.setAttribute('value', '5');
                         ratingFieldset.appendChild(radio5);

                         writeReviewDiv.appendChild(ratingFieldset);
                         var addReviewButton = document.createElement('button');
                         addReviewButton['raw-data'] = product;
                         addReviewButton.onclick = addReview;
                         addReviewButton.setAttribute('class', 'button');
                         addReviewButton.innerHTML = 'Értékelés elküldése';
                         var reviewInputField = document.createElement('textarea');
                         reviewInputField.setAttribute('maxlength', '255');
                         reviewInputField.setAttribute('id', 'rev-input');
                         reviewInputField.style.width = "1000px";
                         reviewInputField.style.height = "70px";
                         writeReviewDiv.appendChild(reviewInputField);
                         writeReviewDiv.appendChild(addReviewButton);
                         var productDiv = document.getElementsByClassName("product-div")[0];
                         productDiv.appendChild(writeReviewDiv);
                       }
                   });
                   return false;
}

function addReview() {
        var product = this['raw-data'];
        var message = document.getElementById('rev-input').value;
        var radios = document.getElementsByName('rating');
        var valueOfRadio;
        for(i = 0; i < radios.length; i++){
            if(radios[i].checked){
                valueOfRadio = radios[i].value;
            }
        }
          var request = {
                        	"product": {
                        		"id": product.id,
                        		"code": product.code,
                        		"name": product.name,
                        		"address": product.address,
                        		"publisher": product.publisher,
                        		"price": product.price,
                        		"status": product.status
                        	},
                        	"message": message,
                        	"rating": valueOfRadio
                        };
          fetch(`/addreview`, {
              method: "POST",
              body: JSON.stringify(request),
              headers: {
                "Content-type": "application/json"
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
