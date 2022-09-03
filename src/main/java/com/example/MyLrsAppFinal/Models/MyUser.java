package com.example.MyLrsAppFinal.Models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.GenerationType.AUTO;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    private String username;
    private String sex;
    private String cin;
    private String email;
    private String image;
    private String firstName;
    private String lastName;
    private String userPhone;
    private String userCodeNumber;
    private Date lastConnect;
    private Date birthday;
    private String password ;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.MERGE,fetch= FetchType.EAGER)
//    @JoinColumn(name = "province_id", referencedColumnName = "id")
//    private Province province;

    @ManyToOne(fetch= FetchType.EAGER)
    Profil profil;
}

