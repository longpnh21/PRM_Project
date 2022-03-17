package com.example.projectrestaurant.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projectrestaurant.daos.CategoryDAO;
import com.example.projectrestaurant.dtos.Category;

import java.util.List;

public class ShowCategoryListActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Category>> listOfCategory;
    private CategoryDAO dao;

    public ShowCategoryListActivityViewModel(Application application) {
        super(application);
        listOfCategory = new MutableLiveData<>();
        dao = new CategoryDAO();
    }

    public MutableLiveData<List<Category>> getCategoryListObserver() {
        return listOfCategory;
    }

    public void getAllCategoryList() {
        try {
            List<Category> categoryList = dao.getAllCategories();
            if (categoryList.size() > 0) {
                listOfCategory.postValue(categoryList);
            } else {
                listOfCategory.postValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertCategory(String catName) {
        try {
            Category category = new Category();
            category.setName(catName);
            dao.insertCategory(category);
            getAllCategoryList();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        try {
            dao.updateCategory(category);
            getAllCategoryList();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategory(Category category) {
        try {
            dao.deleteCategory(category);
            getAllCategoryList();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}