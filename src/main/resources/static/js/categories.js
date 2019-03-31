fetchCategories();
document.getElementById('category-registration-form').onsubmit = addCategory;

function addCategory() {
    var name = document.getElementById('category-name-input').value;
    var priority = document.getElementById('category-priority-input').value;
    var request = {'name': name, 'priority': priority}
    fetch('/categories', {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {"Content-type": "application/json"}
    })
    .then(function (response) {
        return response.json();
    })
    .then(function (jsonData) {
        handleMessages(jsonData);
    })
    return false;
}

function handleMessages(responseStatus) {
    var messageDiv = document.getElementById('message-div');
    messageDiv.innerHTML = "";
    if (responseStatus.status == 'SUCCESS') {
      document.getElementById("category-name-input").value = "";
      document.getElementById("category-priority-input").value = "";
      messageDiv.setAttribute("class", "alert alert-success");
      messageDiv.innerHTML = responseStatus.messages[0];
      fetchCategories();
    } else {
        for (var i = 0; i < responseStatus.messages.length; i++) {
            messageDiv.innerHTML += responseStatus.messages[i] + '<br>';
        }
        messageDiv.setAttribute("class", "alert alert-danger");
    }
}

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
                          ${categories[i].name}
                      </li>`;
  }
}
