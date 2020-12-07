package com.lesconfinesdecanard.recipe;

import com.lesconfinesdecanard.like.LikesRepository;
import com.lesconfinesdecanard.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final LikesRepository likesRepository;

    public RecipeController (RecipeRepository recipeRepository,LikesRepository likesRepository) {
        this.recipeRepository = recipeRepository;
        this.likesRepository = likesRepository;

    }

    @GetMapping("/create_recette")
    public String ajouterRecette(Model model, HttpSession session){
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute("errors", session.getAttribute("errors"));
        session.removeAttribute("errors");
        Recipe recette = new Recipe();
        model.addAttribute("recette", recette);
        return "nouvelleRecette.html";
    }

    @PostMapping("/store_recette")
    public String saveRecette(@ModelAttribute Recipe recette, HttpSession session){

        if (recette.getTitle().trim() == "") {
            session.setAttribute("errors", Arrays.asList("Veuillez ajouter un titre a votre recette"));
            return "redirect:/create_recette";
        }
        if (recette.getContent().trim() == "") {
            session.setAttribute("errors", Arrays.asList("Veuillez ajouter un contenu a votre recette"));
            return "redirect:/create_recette";
        }
        User user = (User) session.getAttribute("user");
        recette.setUserId(user.getId());
        recette.setRecipe_creator(user.getPseudo());
        recipeRepository.save(recette);
        return("redirect:/app");
    }


    @GetMapping ("/app")
    public String GetHome (ModelMap map, Model model , HttpSession session) {
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute ("user", session.getAttribute("user"));
        map.addAttribute("recettes",  recipeRepository.findAll());
        return "app.html";
    }

    @GetMapping(value = "/delete_recette/{id}")
    public String DeleteRecette(@PathVariable String id){
        likesRepository.deleteByRecipe_id(Integer.parseInt(id));
        recipeRepository.deleteById(Integer.parseInt(id));
        return "redirect:/profil";
    }

    @PostMapping("/modifierRecette/{id}")
    public String modifierRecette(@RequestParam("content") String content, @PathVariable String id){
        Recipe rec = recipeRepository.findById(Integer.parseInt(id));
        rec.setContent(content);
        recipeRepository.save(rec);
        return("redirect:/profil");
    }
}
