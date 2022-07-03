package com.curso.rest.webservices.restfulwebservices.controller;

import com.curso.rest.webservices.restfulwebservices.domain.User;
import com.curso.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.curso.rest.webservices.restfulwebservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> findAll(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {

        User user =  userDaoService.findById(id);

        if(user == null){
            throw new UserNotFoundException("ID :" + id);
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> retrieveUser(@RequestBody User user){
        User newUser =  userDaoService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

       return ResponseEntity.created(location).build();
    }

}
