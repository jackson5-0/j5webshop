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
            if (typeof user !== 'undefined' && user.userRole == "ROLE_USER") {
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
                var goToBasketA=document.createElement('a');
                goToBasketA.setAttribute('href', '/basket.html')
                 var goToBasketButton = document.createElement('button');
                 goToBasketButton.setAttribute('class', 'button');
                 goToBasketButton.innerHTML = 'Tovább a kosaramba';
                 goToBasketA.appendChild(goToBasketButton);
                 addBasketTd.appendChild(goToBasketA);
            }
            var imgDiv = document.getElementById("product-image-div");
            var img = document.createElement('img');
            img.setAttribute('class', 'product-image');
            img.setAttribute('src', '/img/fantasy_game_dice.jpg');
            img.setAttribute('alt', 'picture of the game');
            imgDiv.appendChild(img);
            var product = jsonData.product;
            return product;
        })
        .then(function (product) {
            createReviewDiv(product);
        });
        return false;
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
function createReviewDiv(product) {
    if (typeof user !== 'undefined' && user.userRole == 'ROLE_USER') {
        fetch(`/checkifuserhasdeliveredproduct?productid=${product.id}`,
            {method: "GET"})
                 .then(function(response) {
                       return response.json();
                  })
                 .then(function (jsonData) {
                       if (jsonData.hasDeliveredProduct) {
                         var writeReviewDiv = document.createElement('div');
                         writeReviewDiv.setAttribute('id', 'rev-div');
                         var ratingDiv  = document.createElement('div');
                         ratingDiv.setAttribute('class', 'rate');

                         var ratingLegend = document.createElement('legend');
                         ratingLegend.innerHTML = 'Értékelés:'
                         ratingDiv.appendChild(ratingLegend);

                         var radio5 = document.createElement('input');
                         radio5.setAttribute('type', 'radio');
                         radio5.setAttribute('name', 'rate');
                         radio5.setAttribute('value', '5');
                         radio5.setAttribute('id', '5');
                         var radio5label = document.createElement('label');
                         radio5label.setAttribute('for', '5')
                         ratingDiv.appendChild(radio5);
                         ratingDiv.appendChild(radio5label);

                         var radio4 = document.createElement('input');
                         radio4.setAttribute('type', 'radio');
                         radio4.setAttribute('name', 'rate');
                         radio4.setAttribute('value', '4');
                         radio4.setAttribute('id', '4');
                         var radio4label = document.createElement('label');
                         radio4label.setAttribute('for', '4')
                         ratingDiv.appendChild(radio4);
                         ratingDiv.appendChild(radio4label);

                         var radio3 = document.createElement('input');
                         radio3.setAttribute('type', 'radio');
                         radio3.setAttribute('name', 'rate');
                         radio3.setAttribute('value', '3');
                         radio3.setAttribute('id', '3');
                         var radio3label = document.createElement('label');
                         radio3label.setAttribute('for', '3');
                         ratingDiv.appendChild(radio3);
                         ratingDiv.appendChild(radio3label);

                         var radio2 = document.createElement('input');
                         radio2.setAttribute('type', 'radio');
                         radio2.setAttribute('name', 'rate');
                         radio2.setAttribute('value', '2');
                         radio2.setAttribute('id', '2');
                         var radio2label = document.createElement('label');
                         radio2label.setAttribute('for', '2');
                         ratingDiv.appendChild(radio2);
                         ratingDiv.appendChild(radio2label);

                         var radio1 = document.createElement('input');
                         radio1.setAttribute('type', 'radio');
                         radio1.setAttribute('name', 'rate');
                         radio1.setAttribute('value', '1');
                         radio1.setAttribute('id', '1');
                         var radio1label = document.createElement('label');
                         radio1label.setAttribute('for', '1');
                         ratingDiv.appendChild(radio1);
                         ratingDiv.appendChild(radio1label);

                         writeReviewDiv.appendChild(ratingDiv);

                         br = document.createElement('br');
                         ratingDiv.appendChild(br);

                         var addReviewButton = document.createElement('button');
                         addReviewButton['raw-data'] = product;
                         addReviewButton.setAttribute('class', 'button');
                         addReviewButton.setAttribute('id', 'butt');
                         var reviewInputField = document.createElement('textarea');
                         reviewInputField.setAttribute('maxlength', '255');
                         reviewInputField.setAttribute('id', 'rev-input');
                         reviewInputField.style.width = "800px";
                         reviewInputField.style.height = "70px";
                         writeReviewDiv.appendChild(reviewInputField);
                         writeReviewDiv.appendChild(addReviewButton);
                         var productDiv = document.getElementsByClassName("product-div")[0];
                         productDiv.appendChild(writeReviewDiv);
                         if (jsonData.userRating != 0) {
                            addReviewButton.innerHTML = 'Értékelés módosítása';
                            addReviewButton.onclick = modifyReview;
                            document.getElementById(jsonData.userRating).checked = true;
                            createDeleteButton(product);
                         } else {
                            addReviewButton.innerHTML = 'Értékelés elküldése';
                            addReviewButton.onclick = addReview;
                         }
                         if (jsonData.userReview != null) {
                            reviewInputField.innerHTML = jsonData.userReview;
                         }
                         createReviewListDiv(product);
                       } else {
                         var productDiv = document.getElementsByClassName("product-div")[0];
                         var writeReviewDiv = document.createElement('div');
                         writeReviewDiv.setAttribute('id', 'rev-div');
                         writeReviewDiv.innerHTML = 'Csak akkor írhat értékelést, ha már rendelt ebből a termékből!<br><br>';
                         productDiv.appendChild(writeReviewDiv);
                         createReviewListDiv(product);
                       }
                       return product;
                 });
                 return false;
    } else {
       var productDiv = document.getElementsByClassName("product-div")[0];
       var writeReviewDiv = document.createElement('div');
       writeReviewDiv.setAttribute('id', 'rev-div');
       writeReviewDiv.innerHTML = 'Értékelést csak az a bejelentkezett felhasználó írhat, aki már rendelt ebből a termékből!<br><br>';
       productDiv.appendChild(writeReviewDiv);
       createReviewListDiv(product);
    }
}
function createReviewListDiv(product) {
    fetch(`/listreview?productid=${product.id}`,
        {method: "GET"})
            .then(function(response) {
                  return response.json();
            })
            .then(function (jsonData) {
                if (jsonData.length != 0) {
                    if (document.getElementById('rev-list') != null) {
                        document.getElementById('rev-list').remove();
                    }
                    var reviewListDiv = document.createElement('div');
                    reviewListDiv.setAttribute('id', 'rev-list');
                    var productDiv = document.getElementsByClassName("product-div")[0];
                    var avg = 0;
                    for (var i = 0; i < jsonData.length; i++) {
                        avg += jsonData[i].rating;
                    }
                    reviewListDiv.innerHTML = 'Értékelések átlaga: ' + Number((avg / jsonData.length).toFixed(1)) + '<br/><br/><br/><br/>';
                    for (var i = 0; i < jsonData.length; i++) {
                        var singleReviewDiv = document.createElement('div');
                        singleReviewDiv.style.padding = "25px";
                        star = `<font color="#c59b08">&starf;</font>`;
                        singleReviewDiv.innerHTML = jsonData[i].reviewDate.replace(/-/g, '.').replace(/T/g, ' ').substring(0, 16) +
                        '<br/><br/>' + jsonData[i].user.userName + '<br/><br/>Értékelés: ' + star.repeat(jsonData[i].rating);
                        '<br/><br/>' + jsonData[i].message + '<br/><br/><br/>';
                        reviewListDiv.appendChild(singleReviewDiv);
                    }
                    productDiv.appendChild(reviewListDiv);
                } else {
                    if (document.getElementById('rev-list') != null) {
                        document.getElementById('rev-list').remove();
                    }
                    var reviewListDiv = document.createElement('div');
                    var productDiv = document.getElementsByClassName("product-div")[0];
                    reviewListDiv.setAttribute('id', 'rev-list');
                    productDiv.appendChild(reviewListDiv);
                    reviewListDiv.innerHTML = 'Erre a termékre még nem érkezet értékelés';
                }
            });
            return false;
}
function addReview() {
        var product = this['raw-data'];
        var message = document.getElementById('rev-input').value;
        var radios = document.getElementsByName('rate');
        var valueOfRadio;
        for(i = 0; i < radios.length; i++){
            if(radios[i].checked){
                valueOfRadio = radios[i].value;
            }
        }
        if (typeof valueOfRadio !== 'undefined') {
            var addReviewButton = document.getElementById('butt');
            addReviewButton.innerHTML = 'Értékelés módosítása';
            addReviewButton.onclick = modifyReview;
            createDeleteButton(product);
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
              return product;
            })
            .then(function (product) {
                createReviewListDiv(product);
            });
        return false;
}
function createDeleteButton(product) {
        var deleteButton = document.createElement('button');
        deleteButton['raw-data'] = product;
        deleteButton.setAttribute('class', 'button');
        deleteButton.setAttribute('id', 'del-butt');
        deleteButton.innerHTML = "Értékelés törlése";
        deleteButton.onclick = deleteReview;
        var writeReviewDiv = document.getElementById('rev-div');
        writeReviewDiv.appendChild(deleteButton);
}
function modifyReview() {
    var product = this['raw-data'];
    var message = document.getElementById('rev-input').value;
    var radios = document.getElementsByName('rate');
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
    fetch(`/updatereview`, {
        method: "PUT",
        body: JSON.stringify(request),
        headers: {
          "Content-type": "application/json"
        }
      })
      .then(function (response) {
        return response.json();
      }).then(function (jsonData) {
          document.getElementById("message-div").setAttribute("class", "alert alert-success");
          document.getElementById("message-div").innerHTML = jsonData.messages[0];
          return product;
      })
      .then(function (product) {
          createReviewListDiv(product);
      });
    return false;
}
function deleteReview() {
    document.getElementById('rev-input').value = "";
    document.getElementById('1').checked = false;
    document.getElementById('2').checked = false;
    document.getElementById('3').checked = false;
    document.getElementById('4').checked = false;
    document.getElementById('5').checked = false;
    document.getElementById('del-butt').remove();
    var addReviewButton = document.getElementById('butt');
    addReviewButton.innerHTML = 'Értékelés elküldése';
    addReviewButton.onclick = addReview;
    var product = this['raw-data'];
       var request = {
                     	"product": {
                     		"id": product.id,
                     		"code": product.code,
                     		"name": product.name,
                     		"address": product.address,
                     		"publisher": product.publisher,
                     		"price": product.price,
                     		"status": product.status
                     	}
                     };
    fetch(`/deletereview`, {
        method: "DELETE",
        body: JSON.stringify(request),
        headers: {
          "Content-type": "application/json"
        }
      })
      .then(function (response) {
        return response.json();
      }).then(function (jsonData) {
          document.getElementById("message-div").setAttribute("class", "alert alert-success");
          document.getElementById("message-div").innerHTML = jsonData.messages[0];
          return product;
      })
      .then(function (product) {
          createReviewListDiv(product);
          console.log("del");
      });
    return false;
}
