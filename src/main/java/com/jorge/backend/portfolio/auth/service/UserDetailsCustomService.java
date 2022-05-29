package com.jorge.backend.portfolio.auth.service;

import java.util.Collections;
import java.util.Optional;

import com.jorge.backend.portfolio.auth.dto.UserDTO;
import com.jorge.backend.portfolio.auth.entity.UserEntity;
import com.jorge.backend.portfolio.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("Username or password not found");
        }
        return new User(userEntity.get().getUsername(), userEntity.get().getPassword(), Collections.emptyList());
    }

    public boolean save(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity = this.userRepository.save(userEntity);

        if(userEntity != null){
            // mandar mail
            // emailService.sendWelcomeEmailTo(userEntity.getUername());
        }
        return userEntity != null;
    }
}
