package com.lesconfinesdecanard.like;

import com.lesconfinesdecanard.recipe.Recipe;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikesRepository extends CrudRepository<Likes, Integer> {

    @Query("select t from Likes t  where t.user_id = :id1 and t.recipe_id = :id2")
    List<Likes> findAllByClientAndRecette(int id1, int id2) ;

    @Query("Select r from Recipe r where r.id in (Select t.recipe_id from Likes t where t.user_id = :id3)")
    List<Recipe> findByUser_id(int id3);

    @Modifying
    @Transactional
    @Query("Delete from Likes r where r.recipe_id = :id4")
    void deleteByRecipe_id(int id4);
}
