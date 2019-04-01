fetchCategories();
document.getElementById('category-registration-form').onsubmit = addCategory;
document.getElementById('save-category').addEventListener('click', function() {
  saveCategoryModifications();
});

function saveCategoryModifications() {
  var categories = [];
  var elements = document.getElementsByClassName('ui-state-default');
  for (var i = 0; i < elements.length; i++) {
    var spans = elements[i].getElementsByTagName('span');
    var category = elements[i]['raw-data'];
    category.priority = i + 1;
    category.name = spans[2].innerHTML;
    categories.push(category);
  }
  console.log(JSON.stringify(categories));
  fetch('/categories', {
    method: 'PUT',
    body: JSON.stringify(categories),
    headers: { 'Content-type': 'application/json' }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(responseStatus) {
      console.log(responseStatus);
      handleMessages(responseStatus);
    });
}

function addCategory() {
  var name = document.getElementById('category-name-input').value;
  var priority = document.getElementById('category-priority-input').value;
  var request = {
    name: name,
    priority: priority
  };
  fetch('/categories', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      handleMessages(jsonData);
    });
  return false;
}

function handleMessages(responseStatus) {
  var messageDiv = document.getElementById('message-div');
  messageDiv.innerHTML = '';
  if (responseStatus.status == 'SUCCESS') {
    document.getElementById('category-name-input').value = '';
    document.getElementById('category-priority-input').value = '';
    messageDiv.setAttribute('class', 'alert alert-success');
    messageDiv.innerHTML = responseStatus.messages[0];
    fetchCategories();
  } else {
    for (var i = 0; i < responseStatus.messages.length; i++) {
      messageDiv.innerHTML += responseStatus.messages[i] + '<br>';
    }
    messageDiv.setAttribute('class', 'alert alert-danger');
  }
}

function fetchCategories() {
  fetch('/categories')
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      createDragAndDropElements(jsonData);
    });
}

function createDragAndDropElements(categories) {
  var list = document.getElementById('sortable');
  list.innerHTML = '';
  for (var i = 0; i < categories.length; i++) {
    var li = document.createElement('li');
    var arrow = document.createElement('span');
    var priority = document.createElement('span');
    var name = document.createElement('span');
    var deleteCategory = document.createElement('span');

    li['raw-data'] = categories[i];

    li.setAttribute('class', 'ui-state-default');
    arrow.setAttribute('class', 'ui-icon ui-icon-arrowthick-2-n-s');
    priority.innerHTML = categories[i].priority + '. ';
    priority.setAttribute('class', 'category-priority');
    name.setAttribute('class', 'category-name');
    name.innerHTML = categories[i].name;
    deleteCategory.setAttribute('class', 'delete-category');
    deleteCategory.innerHTML = '&times;';
    deleteCategory['raw-data'] = categories[i];

    addEventListenerCategories(deleteCategory, categories[i]);

    li.appendChild(arrow);
    li.appendChild(priority);
    li.appendChild(name);
    li.appendChild(deleteCategory);

    list.appendChild(li);
  }
}

function handlePriorityChange() {
  var priorities = document.getElementsByClassName('category-priority');
  for (var i = 0; i < priorities.length; i++) {
    priorities[i].innerHTML = i + 1 + '. ';
  }
}

function addEventListenerCategories(element, data) {
  element.addEventListener('click', function() {
    deleteCategory(data);
  });
}

function deleteCategory(category) {
  if (!confirm('Biztosan törölni szeretnéd a kategóriát?')) {
    return;
  }
  var request = {
    id: category.id,
    name: category.name,
    priority: category.priority
  };
  fetch('/categories', {
    method: 'DELETE',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      handleMessages(jsonData);
    });
}
