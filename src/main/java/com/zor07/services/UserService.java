package com.zor07.services;

import com.zor07.domain.User;
import com.zor07.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        for (User user : list()){
            if (user.getUsername().equals(username))
                return user;
        }
        throw new UsernameNotFoundException(username + " not found!");
    }

    public List<User> list() {
        return repository.findAll();
    }

    public User getById(Integer id) {
        return repository.getOne(id);
    }

    public User save(User user) {
        return repository.save(user);
    }
    public void delete(Integer id) {
        repository.delete(id);
    }

    public User getCurrentLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        User user = (User) loadUserByUsername(name);
        return user;
    }
}
