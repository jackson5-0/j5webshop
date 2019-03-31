fetchCategories();

function fetchCategories() {
  fetch('/categories')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      createDragAndDropElements(jsonData);
    })
}

function createDragAndDropElements(categories) {
  var list = document.getElementById('sortable');
  list.innerHTML = '';
  for (var i = 0; i < categories.length; i++) {
    list.innerHTML += `<li class="ui-state-default">
                          <span class="ui-icon ui-icon-arrowthick-2-n-s"></span>
                          <span>${i + 1}.</span> 
                          ${categories[i].name}
                      </li>`;
  }
}