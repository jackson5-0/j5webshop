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

    var ct1 = document.getElementById('chart1');

    var data = {
        labels: [`Aktív termékek: ${numberOfActiveProducts}`, `Törölt termékek: ${numberOfAllProducts-numberOfActiveProducts}`],
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
    var options = {responsive: true,
                   maintainAspectRatio:false,

                   };

    var myPieChart = new Chart(ct1, {
        type: 'pie',
        data: data,
        options: options
    });

    var ct2 = document.getElementById('chart2');

    var data2 = {
            labels: [`Aktív rendelések: ${numberOfActiveOrders}`, `Nem aktív rendelések: ${numberOfAllOrders-numberOfActiveOrders}`],
            datasets: [{
                data: [numberOfActiveOrders, numberOfAllOrders-numberOfActiveOrders],
                backgroundColor: [
                                 'rgba(255, 99, 132, 0.2)',
                                 'rgba(54, 162, 235, 0.2)'
                                 ],
                borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)'
                             ],
                             borderWidth:1
            }],
         };


        var myPieChart = new Chart(ct2, {
            type: 'pie',
            data: data2,
            options: options
        });
}