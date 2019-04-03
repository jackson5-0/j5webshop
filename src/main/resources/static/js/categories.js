initPopup();
fetchCategories();
document.getElementById('category-registration-form').onsubmit = addCategory;
document.getElementById('save-category').addEventListener('click', function () {
  saveCategoryModifications();
});

function doConfirm(msg, yesFn) {
  var confirmBox = $(".confirm");
  confirmBox.find("#product-name").text(msg);
  confirmBox.find(".save").click(yesFn);
}

function namePrompt(msg, yesFn) {
  var prompt = $(".prompt");
  prompt.find("#message-text").text(msg);
  prompt.find(".save").click(yesFn);
}

function initPopup() {
  var confirm = document.getElementById('delete-confirm');
  var span = document.getElementsByClassName('close');
  var save = document.getElementsByClassName('save');
  var prompt = document.getElementById('name-prompt');

  save.onclick = function () {
    confirm.style.display = "none";
  }
  for (var i = 0; i < span.length; i++) {
    save[i].onclick = function () {
      confirm.style.display = "none";
      prompt.style.display = "none";
    }
  }
  for (var i = 0; i < span.length; i++) {
    span[i].onclick = function () {
      confirm.style.display = "none";
      prompt.style.display = "none";
    }
  }
  window.onclick = function (event) {
    if (event.target == confirm || event.target == prompt) {
      confirm.style.display = "none";
      prompt.style.display = "none";
    }
  }
}

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
      headers: {
        'Content-type': 'application/json'
      }
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (responseStatus) {
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
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
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
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
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
    var updateName = document.createElement('span');
    var deleteCategory = document.createElement('span');

    li['raw-data'] = categories[i];

    li.setAttribute('class', 'ui-state-default');
    arrow.setAttribute('class', 'ui-icon ui-icon-arrowthick-2-n-s');
    priority.innerHTML = categories[i].priority + '. ';
    priority.setAttribute('class', 'category-priority');
    name.setAttribute('class', 'category-name');
    name.innerHTML = categories[i].name;
    updateName.innerHTML = '&#9874;';
    updateName['raw-data'] = categories[i];

    updateName.setAttribute('class', 'update-category-name');
    updateName.style.cssFloat = 'right';
    deleteCategory.setAttribute('class', 'delete-category');
    deleteCategory.innerHTML = '&times;';
    deleteCategory['raw-data'] = categories[i];

    addEventListenerCategories(deleteCategory, categories[i]);
    addEventListenerOnUpdateButton(updateName, categories[i]);

    li.appendChild(arrow);
    li.appendChild(priority);
    li.appendChild(name);
    li.appendChild(deleteCategory);
    li.appendChild(updateName);

    list.appendChild(li);
  }
}

function addEventListenerOnUpdateButton(element, data) {
  element.addEventListener('click', function () {
    var prompt = document.getElementById('name-prompt');
    prompt.style.display = "block";
    namePrompt("Add meg a kategória új nevét!", function yes() {
      modifyName(data);
    });
  })
}

function modifyName(rawData) {
  var newName = document.getElementById("new-name-input").value;
  if (newName !== null) {
    var id = rawData.id;
    var priority = rawData.priority;
    var products = rawData.products;
    var request = {
      "id": id,
      "name": newName,
      "priority": priority,
      "products": products
    };
    fetch("/categories/updatename", {
        method: "PUT",
        body: JSON.stringify(request),
        headers: {
          "Content-type": "application/json"
        }
      })
      .then(function (response) {
        return response.json();
      })
      .then(function (jsonData) {
        handleMessages(jsonData);
        fetchCategories();
      });
  }
  document.getElementById('new-name-input').value = '';
}

function handlePriorityChange() {
  var priorities = document.getElementsByClassName('category-priority');
  for (var i = 0; i < priorities.length; i++) {
    priorities[i].innerHTML = i + 1 + '. ';
  }
}

function addEventListenerCategories(element, data) {
  element.addEventListener('click', function () {
    var confirm = document.getElementById('delete-confirm');
    confirm.style.display = "block";
    doConfirm("Biztosan törölni szeretnéd a kategóriát?", function yes() {
      deleteCategory(data);
    });
  });
}

function deleteCategory(category) {
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
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      handleMessages(jsonData);
    });
}