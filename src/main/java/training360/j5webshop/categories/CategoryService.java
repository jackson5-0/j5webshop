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
        sortCategoriesByPriority(categories, category);
        updateCategories(categories);
        return "";
    }

    private void sortCategoriesByPriority(List<Category> categories, Category inserted) {
        for (Category category: categories) {
            if (category.getPriority() >= inserted.getPriority()) {
                category.setPriority(category.getPriority() + 1);
            }
        }
    }

    public void updateCategories(List<Category> categories) {
        categoryDao.updateCategories(categories);
    }
}
