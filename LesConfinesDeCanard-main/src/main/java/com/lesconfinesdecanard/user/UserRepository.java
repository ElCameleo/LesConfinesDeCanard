package com.lesconfinesdecanard.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Integer> {

    User findByPseudoAndPassword (String pseudo, String password);
    User findByPseudo (String pseudo);

}
