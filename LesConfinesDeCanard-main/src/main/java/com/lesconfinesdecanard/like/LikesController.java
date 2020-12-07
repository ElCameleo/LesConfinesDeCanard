package com.lesconfinesdecanard.like;

import com.lesconfinesdecanard.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
public class LikesController {

    private final LikesRepository likesRepository;

    public LikesController (LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    @GetMapping("/like")
    public String testLike(){
        return "like";
    }

    @PostMapping("/like")
    @ResponseBody
    public String like (@RequestBody String body, HttpSession session){
        User user = (User) session.getAttribute("user");
        Likes like = new Likes(user.getId(),Integer.parseInt(body));
        String res = "none";
        List<Likes > likeExists = likesRepository.findAllByClientAndRecette(like.getUser_id(), like.getRecipe_id());
        System.out.println(likeExists.isEmpty());
      if( ! likeExists.isEmpty()){
          Likes l = likeExists.get(0);
          likesRepository.deleteById(l.getId());
          res = "unliked";
      }
      else{
          likesRepository.save(like);
          res = "liked";

      }

        return res;
    }


}
