package com.lesconfinesdecanard.recipe;
import com.lesconfinesdecanard.user.User;
import com.lesconfinesdecanard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Recipe {
    private String title;
    private String content;

    public Recipe() {
    }

    private Integer id;
    private Integer userId;
    private String recipe_creator;

    public String getRecipe_creator() {
        return recipe_creator;
    }

    public void setRecipe_creator(String recipe_creator) {
        this.recipe_creator = recipe_creator;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                '}';
    }
    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(title, recipe.title) &&
                Objects.equals(content, recipe.content) &&
                Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, id);
    }


}
