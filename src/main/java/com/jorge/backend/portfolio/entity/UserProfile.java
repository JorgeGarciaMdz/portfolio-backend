package com.jorge.backend.portfolio.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jorge.backend.portfolio.auth.entity.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_profile")
@Entity
@Data
@NoArgsConstructor
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "lastname", length = 100)
    private String lastname;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "about_me", length = 250)
    private String aboutMe;

    @Column(name = "occupation", length = 250)
    private String occupation;

    @Column(name = "url_image", length = 250)
    private String urlImage;
    
    @OneToOne(mappedBy = "userProfile")
    private UserEntity userEntity;
}
