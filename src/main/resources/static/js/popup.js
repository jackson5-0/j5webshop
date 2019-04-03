var categoryList;
fetchCategories();
initPopup();

function fetchCategories() {
    fetch('/categories')
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
            categoryList = jsonData;
        })
}

function initPopup() {
    var modal = document.getElementById('myModal');
    var span = document.getElementsByClassName('close');
    var save = document.getElementsByClassName('save')[0];

    save.onclick = function () {
        saveCategoryModificationToRawDataSaveButton();
        modal.style.display = "none";
    }

    for (var i = 0; i < span.length; i++) {
        span[i].onclick = function () {
            modal.style.display = "none";
        }
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}

function handleCategoriesOnChange(checkBox) {
    var collectedCategories = document.getElementById('chosen-categories');
    if (checkBox.checked) {
        collectedCategories.innerHTML += " " + checkBox.value + " ";
    } else {
        var categoriesString = collectedCategories.innerText;
        collectedCategories.innerHTML = categoriesString.replace(`${checkBox.value}`, '');
    }
}

function showCategoryOfItem(rowIdentifier) {
    var categories = document.getElementById(rowIdentifier);
    createDefaultStateOfSelectedCategoryList(categories['raw-data']);
    var modal = document.getElementById('myModal');
    var checkBoxList = document.getElementById('category-checkbox-form');
    checkBoxList.innerHTML = '';
    for (var i = 0; i < categoryList.length; i++) {
        var checked = checkIfHasCategory(categoryList[i], categories['raw-data']);
        checkBoxList.innerHTML +=
            `<input type="checkbox" class="category-checkbox" id="category-${categoryList[i].id}"
                    value="${categoryList[i].name}" onchange=handleCategoriesOnChange(this) ${checked}>
            <label for="category-${categoryList[i].id}">${categoryList[i].name}</label>`
    }
    modal.style.display = "block";
};

function createDefaultStateOfSelectedCategoryList(categories) {
    var collectedCategories = document.getElementById('chosen-categories');
    collectedCategories.innerHTML = '';
    if (!categories) {
        var header = document.getElementById('product-name');
        header.innerHTML = 'Új termék';
        return;
    }
    for (var i = 0; i < categories.length; i++) {
        collectedCategories.innerHTML += " " + categories[i].name + " ";
    }
}

function checkIfHasCategory(category, categories) {
    if (!categories) return "";
    for (var i = 0; i < categories.length; i++) {
        if (categories[i].name === category.name) {
            return "checked"
        }
    }
    return "";
}

function saveProductIdToRawDataSaveButton(productCode) {
    document.querySelector('.save')['raw-data'] = productCode;
}

function saveCategoryModificationToRawDataSaveButton() {
    var productCode = document.querySelector('.save')['raw-data'];
    document.getElementById(`${productCode}`)['raw-data'] = saveCheckBoxValues();
}

function saveCheckBoxValues() {
    var finalValue = [];
    var categories = document.getElementsByClassName('category-checkbox');
    for (var i = 0; i < categories.length; i++) {
        if (categories[i].checked) {
            for (var k = 0; k < categoryList.length; k++) {
                if (categoryList[k].name === categories[i].value) {
                    finalValue.push(categoryList[k]);
                }
            }
        }
    }
    return finalValue;
}