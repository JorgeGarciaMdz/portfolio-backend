package com.jorge.backend.portfolio.auth.service;

import java.util.Collections;

import com.jorge.backend.portfolio.auth.dto.UserDTO;
import com.jorge.backend.portfolio.auth.entity.UserEntity;
import com.jorge.backend.portfolio.auth.repository.UserRepository;
import com.jorge.backend.portfolio.entity.UserProfile;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username or password not found");
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    public boolean save(UserDTO userDTO) throws DataIntegrityViolationException, ConstraintViolationException{
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userDTO.getName());
        userProfile.setLastname(userDTO.getLastname());
        userProfile.setBirthday(userDTO.getBirthday());
        userProfile.setNationality(userDTO.getNationality());
        userProfile.setAboutMe(userDTO.getAboutMe());
        userProfile.setOccupation(userDTO.getOccupation());
        userProfile.setUrlImage(userDTO.getImageUrl());
        userEntity.setUserProfile(userProfile);

        userEntity = this.userRepository.save(userEntity);

        if (userEntity != null) {
            // mandar mail
            // emailService.sendWelcomeEmailTo(userEntity.getUername());
        }
        return userEntity != null;
    }
}
