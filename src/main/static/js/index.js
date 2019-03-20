window.onload=function(){
    fetchList();
}

function fetchList(){
    var url = "/api/index";
    fetch(url)
        .then(function(response){
            return response.json();
            })
        .then(function(jsonData){
            showTable(jsonData);
        });
}
function showTable(jsonData){
    var table = document.getElementById("index-table");
    table.innerHTML = "";

    for (var i = 0; i<jsonData.length; i++){
        var tr = document.createElement("tr");

        var codeTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].code;
        tr.appendChild(codeTd);

        var nameTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].name;
        tr.appendChild(nameTd);

        var addressTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].address;
        tr.appendChild(addressTd);

        var publisherTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].publisher;
        tr.appendChild(publisherTd);

        var priceTd = document.createElement("td");
        codeTd.innerHTML = jsonData[i].price;
        tr.appendChild(priceTd);

//      var statusTd = document.createElement("td");
//      codeTd.innerHTML = jsonData[i].code;
//      tr.appendChild(codeTd);

        table.appendChild(tr);
     }
}
