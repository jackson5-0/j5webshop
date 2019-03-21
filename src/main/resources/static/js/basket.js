window.onload=fetchList;
var flush = document.getElementById('flush');
flush.onclick=flushBasket;

function fetchList(){
    var id = (new URL(document.location)).searchParams.get('id');
    fetch(`/productofbasket/${id}`)
        .then(function (response){
            return response.json();
         })
         .then(function(jsonData){
            showList(jsonData);
         });
}

function flushBasket(){
    var id = (new URL(document.location)).searchParams.get('id');
        fetch(`/flushbasket/${id}`,{
            method: "DELETE"
            })
//            .then(function (response){
//                return response.json();
//              })
             .then(function(jsonData){
                showList(jsonData);
             });
}

function showList(jsonData){
    var tbody = document.getElementById('basket-tablebody');
    tbody.innerHTML = '';
    var sum =0;
    for (var i=0; i<jsonData.length; i++){
        var tr = document.createElement('tr');

        var nameTd = document.createElement('td');
        nameTd.innerHTML = jsonData[i].name;

        var priceTd = document.createElement('td');
        priceTd.innerHTML = jsonData[i].price;
        sum+=jsonData[i].price;

        var qtyTd = document.createElement('td');
        qtyTd.innerHTML = 1;

        tr.appendChild(nameTd);
        tr.appendChild(priceTd);
        tr.appendChild(qtyTd);

        tbody.appendChild(tr);
     }
      var tr2 = document.createElement('tr');
      var totalTd = document.createElement('td');
      var sumTd = document.createElement('td');

      sumTd.innerHTML = "Teljes összeg";
      totalTd.innerHTML = sum;

      tr2.appendChild(sumTd);
      tr2.appendChild(document.createElement('td')); //üres cella
      tr2.appendChild(totalTd);

      tbody.appendChild(document.createElement('br'));
      tbody.appendChild(tr2);
}