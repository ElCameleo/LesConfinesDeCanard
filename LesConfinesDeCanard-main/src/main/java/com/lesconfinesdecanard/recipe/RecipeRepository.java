package com.lesconfinesdecanard.recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    Recipe findById(int id);
    List<Recipe> findAllByUserId(Integer id);
    List<Recipe> findAll();
}
