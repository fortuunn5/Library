package com.example.Library.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String fio;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Builder.Default
    private String role = "ROLE_USER";

    @Column(nullable = false)
    @Builder.Default
    private Boolean isArchived = false;

    @OneToMany(mappedBy="reader")
    private Set<Logbook> logbooks;

    public Reader(@NonNull String fio, @NonNull String email, String username, String password) {
        this.fio = fio;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Reader(@NonNull String fio, @NonNull String email, String username, String password, String role) {
        this.fio = fio;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Reader(@NonNull String fio, @NonNull String email, String username, String password, String role, Boolean isArchived) {
        this.fio = fio;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isArchived = isArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(email, reader.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
