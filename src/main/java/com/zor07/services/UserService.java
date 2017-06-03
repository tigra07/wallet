package com.zor07.services;

import com.zor07.domain.User;
import com.zor07.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService implements UserDetailsService, DomainService<User> {

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


    @Override
    public List<User> list() {
        return repository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public User save(User user) {
//        String password = user.getPassword();
//        password = new BCryptPasswordEncoder().encode(password);
//        user.setPassword(password);
        return repository.save(user);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
