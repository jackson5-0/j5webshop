document.forms["new-address-form"].onsubmit = saveNewAddress;

fetchAddresses();

function fetchAddresses() {
  fetch(`/useraddresses`)
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
        loadAddresses(jsonData);
    });
}

function loadAddresses(jsonData) {
    var submitInput1 = document.getElementById('submit1-button');
    submitInput1["raw-data"] = jsonData;

    var addressInfoForm = document.getElementById('address-information-form');
     addressInfoForm.innerHTML = '';
     for (var i = 0; i < jsonData.addressList.length; i++) {
          addressInfoForm.innerHTML += `<p>${i+1}. szállítási cím:</p>
                                        <textarea id='address-${i+1}' maxlength='200'>${jsonData.addressList[i]}></textarea><br>`;
     }
     addressInfoForm.innerHTML += `<input id="submit2-button" type="submit" value="Mentés">`;

     var submitInput2 = document.getElementById('submit2-button');
     submitInput2["raw-data"] = jsonData;
     document.forms["address-information-form"].onsubmit = modifyExistingAddresses;
 }

function saveNewAddress() {
 var id = document.getElementById('submit-button')["raw-data"].id;
 var newAddress = document.getElementById('new-address').value;

     if (newAddress.length < 20){
        document.getElementById('message-div').setAttribute('class', 'alert-danger');
        document.getElementById('message-div').innerHTML = 'A cím túl rövid!';
     } else if (newAddress.length > 200){
        document.getElementById('message-div').setAttribute('class', 'alert-danger');
        document.getElementById('message-div').innerHTML = 'A cím hossza maximum 200 karakter lehet!';
     } else {
        var request = {
            "id" : id,
            "newAddress" : newAddress,
        };
        fetch(`/useraddresses?id=${id}`, {
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
        });
//        loadAddresses(fetchUser().addressList);
        return false;
     }
     return false;
}

//function modifyExistingAddresses() {
//}
