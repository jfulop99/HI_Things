package hu.hotelinteractive.issuetracker;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String username;

    private String password;

    @Column(name = "is_enabled")
    private boolean enabled;

    @Column(name = "user_id")
    private long id;

    private String firstname;

    private String lastname;

    private String email;

    @ElementCollection
    @CollectionTable(name = "authorities",
        joinColumns = @JoinColumn(name = "username"))

    @Column(name = "authority")
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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

    public void setPassword(String password) {

        this.password = new BCryptPasswordEncoder().encode(password);

    }

}
