package com.lesconfinesdecanard.like;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "likes", schema = "LesConfinesDeCanard")
public class Likes {
    private int id;
    private Integer client;
    private Integer recette;

    public Likes(){}

    public Likes(Integer u_id, Integer r_id){
        this.client = u_id;
        this.recette = r_id;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "user_id=" + client +
                ", recipe_id=" + recette +
                '}';
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUser_id() {
        return client;
    }

    public void setUser_id(Integer user_id) {
        this.client = user_id;
    }

    @Basic
    @Column(name = "recipe_id")
    public Integer getRecipe_id() {
        return recette;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recette = recipe_id;
    }
    @Id
    @Column(name = "id")
    @GeneratedValue

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likes that = (Likes) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
