window.onload=fetchStatistics;

function fetchStatistics(){
    fetch("/dashboard")
        .then(function(response){
            return response.json();
        })
        .then(function(jsonData){
            return showStatistics(jsonData)
        });
}

function showStatistics(jsonData){

    document.getElementById('users').innerHTML = jsonData.numberOfActiveUsers;
    var numberOfActiveProducts = jsonData.numberOfActiveProducts;
    var numberOfAllProducts = jsonData.numberOfAllProducts;
    var numberOfActiveOrders = jsonData.numberOfActiveOrders;
    var numberOfAllOrders = jsonData.numberOfAllOrders;
    var options = {responsive: true,
                   maintainAspectRatio:false,
                   animation: {
                                duration: 3000,
                              }
                   };
    if(numberOfAllProducts !==0){
    var ct1 = document.getElementById('chart1').getContext("2d");
    var data = {
        labels: [`Aktív termékek`, `Törölt termékek`],
        datasets: [{
            data: [numberOfActiveProducts, numberOfAllProducts-numberOfActiveProducts],
            backgroundColor: [
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 99, 132, 0.2)'
                            ],
            borderColor: [
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 99, 132, 1)'
                         ],
                         borderWidth:1
        }]
     };


    var myPieChart = new Chart(ct1, {
        type: 'pie',
        data: data,
        options: options
    });
    } else {

        document.getElementById('message-product').innerHTML = "Nincsenek termékek a rendszerben.";
    }

    if(numberOfAllOrders !==0){
    var ct2 = document.getElementById('chart2').getContext("2d");

    var data2 = {
            labels: [`Aktív rendelések`, `Nem aktív rendelések`],
            datasets: [{
                data: [numberOfActiveOrders, numberOfAllOrders-numberOfActiveOrders],
                backgroundColor: [
                                 'rgba(54, 162, 235, 0.2)',
                                 'rgba(255, 99, 132, 0.2)'
                                 ],
                borderColor: [
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 99, 132, 1)'

                             ],
                             borderWidth:1
            }],
         };


        var myPieChart = new Chart(ct2, {
            type: 'pie',
            data: data2,
            options: options
        });
        } else {
            document.getElementById('message-order').innerHTML="Nincsenek rendelések a rendszerben.";
        }
}