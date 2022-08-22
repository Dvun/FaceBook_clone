package com.facebookclone.entity;

import com.facebookclone.utils.GenderEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private String picture;
    private String cover;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Boolean isVerified;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private UserBio details;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private Set<Friend> friends = new HashSet<>();
//    private Set<Following> followings = new HashSet<>();
//    private Set<Follower> followers = new HashSet<>();
//    private Set<Request> requests = new HashSet<>();



    public void setName(String name) {
        this.name = name.trim();
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.trim();
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase().trim();
    }
}
