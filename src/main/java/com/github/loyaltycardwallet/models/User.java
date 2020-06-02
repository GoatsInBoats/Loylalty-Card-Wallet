package com.github.loyaltycardwallet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {


    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @OneToOne
    private UserSpecifics userSpecifics;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 100, message = "password length out of range")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "password is mandatory")
    @Size(min = 5, max = 100, message = "password length out of range")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int active;

    @Column(nullable = false)
    private String roles = "";

    private String permissions = "";


    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //extract list of permissions (name)
        getPermissionList()
                .forEach(permission -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority(permission);
                    authorities.add(authority);
                });

        getRoleList()
                .forEach(role -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    authorities.add(authority);
                });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.active == 1;
    }
}
