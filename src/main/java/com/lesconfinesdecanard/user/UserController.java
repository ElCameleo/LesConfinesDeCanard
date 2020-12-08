package com.lesconfinesdecanard.user;


import com.lesconfinesdecanard.like.LikesRepository;
import com.lesconfinesdecanard.recipe.RecipeRepository;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.DatatypeConverter;



import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final LikesRepository likesRepository;


    public UserController (UserRepository userRepository, RecipeRepository recipeRepository, LikesRepository likesRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.likesRepository = likesRepository;
    }

    @GetMapping ("/connexion")
    public String GetConnexion (Model model,HttpSession session) {
        session.removeAttribute("user");
        model.addAttribute ("user", new User ());
        model.addAttribute ("errors", session.getAttribute("errors"));
        session.removeAttribute("errors");
        return "connexion.html";
    }

    @PostMapping("/register")
    public String PostRegister (@ModelAttribute User user, HttpSession session) throws NoSuchAlgorithmException {
        /* PASSWORD ENCRYPTION : MD5 */
        if (userRepository.findByPseudo(user.getPseudo()) != null) {
            session.setAttribute("errors", Arrays.asList("Ce pseudo n\'est pas disponible"));
            return "redirect:/connexion";
        }
        if (user.getPassword().length() < 8) {
            session.setAttribute("errors", Arrays.asList("Le mot de passe doit faire au moins 8 caractères"));
            return "redirect:/connexion";
        }
        if (user.getPseudo().length() < 3) {
            session.setAttribute("errors", Arrays.asList("Le pseudo doit faire au moins 3 caractères"));
            return "redirect:/connexion";
        }

        user.setPassword(getMd5(user.getPassword()));
        /* END PASSWORD ENCRYPTION*/
        userRepository.save(user);
        session.setAttribute("user", user);
        return "redirect:/app";
    }

    @PostMapping("/connexion")
    public String PostConnexion (@ModelAttribute User user, HttpSession session) throws NoSuchAlgorithmException {
        String passHash = getMd5(user.getPassword());
        User newUser = userRepository.findByPseudoAndPassword(user.getPseudo(), passHash);
        if (newUser == null) {
            session.setAttribute ("errors", Arrays.asList("Pseudo ou mot de passe inconnu"));
            return "redirect:/connexion";
        }
        session.setAttribute("user", newUser);
        return "redirect:/app";
    }

    @GetMapping ("/profil")
    public String GetProfil (Model model, HttpSession session, ModelMap map) {
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute ("user", session.getAttribute("user"));
        User currentUser = (User) session.getAttribute("user");
        map.addAttribute("myRecipies", recipeRepository.findAllByUserId(currentUser.getId()));
        return "profil.html";
    }

    @GetMapping ("/favorite")
    public String GetFav (Model model, HttpSession session, ModelMap map) {
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute ("user", session.getAttribute("user"));
        User currentUser = (User) session.getAttribute("user");
        map.addAttribute("recipiesLiked", likesRepository.findByUser_id(currentUser.getId()));
        return "favorite.html";
    }

    public String getMd5(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    @GetMapping("/modif")
    public String initUpdateUserForm(Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute ("user", session.getAttribute("user"));
        User currentUser = (User) session.getAttribute("user");
        return "modif.html";
    }

    @PostMapping("/validFormModif")
    public String validUpdateUserForm(@ModelAttribute User user, HttpSession session) throws NoSuchAlgorithmException {
        User mYuser = (User) session.getAttribute("user");
        mYuser.setPassword(getMd5(user.getPassword()));
        mYuser.setPseudo(user.getPseudo());
        userRepository.save(mYuser);
        return "redirect:/profil";
    }




}
