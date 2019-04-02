package training360.j5webshop.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public List<Category> listCategories() {
        return categoryDao.listCategories();
    }

    public String createCategory(Category category) {
        if (categoryDao.isCategoryNameTaken(category) > 0) {
            return "A megadott kategória már létezik!";
        }

        List<Category> categories = categoryDao.listCategories();
        if (categories.size() != 0 && categories.get(categories.size() - 1).getPriority() + 1 < category.getPriority()) {
            return "Túl magas sorszám: #" + category.getPriority();
        }

        long categoryId = categoryDao.createCategory(category);
        category.setId(categoryId);
        sortCategoriesByPriorityAfterCreate(categories, category);
        updateCategories(categories);
        return "";
    }

    private void sortCategoriesByPriorityAfterCreate(List<Category> categories, Category modified) {
        for (Category category: categories) {
            if (category.getPriority() >= modified.getPriority()) {
                category.setPriority(category.getPriority() + 1);
            }
        }
    }

    private void sortCategoriesByPriorityAfterDelete(List<Category> categories, Category modified) {
        for (Category category: categories) {
            if (modified.getPriority() < category.getPriority()) {
                category.setPriority(category.getPriority() - 1);
            }
        }
    }

    public void deleteCategory(Category category) {
        categoryDao.deleteCategory(category);
        List<Category> categories = categoryDao.listCategories();
        sortCategoriesByPriorityAfterDelete(categories, category);
        updateCategories(categories);
    }

    public void updateCategories(List<Category> categories) {
        categoryDao.updateCategories(categories);
    }
    public String updateCategoryNameById(Category category){
        if (categoryDao.isCategoryNameTaken(category) > 0) {
            return "A megadott kategória már létezik!";
        }
        categoryDao.updateCategoryNameById(category);
        return "";
    }
}
