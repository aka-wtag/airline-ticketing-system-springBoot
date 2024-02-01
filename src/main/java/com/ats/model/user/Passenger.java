package com.ats.model.user;

import com.ats.model.booking.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Passenger extends User implements Serializable {
    @Column
    private String passengerPassport;

    @OneToMany(mappedBy = "passenger")
    @JsonIgnore
    private List<Booking> bookings;

//    @Builder
//    public Passenger(int userId, String userFullName, String userPassword, String userEmail, String userContact, String passengerPassport, List<Booking> bookings) {
//        super(userId, userFullName, userPassword, userEmail, userContact);
//        this.passengerPassport = passengerPassport;
//        this.bookings = bookings;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        SimpleGrantedAuthority role1 = new SimpleGrantedAuthority("ROLE_Passenger");
        roles.add(role1);
        return roles;
    }

    @Override
    public String getPassword() {
        return getUserPassword();
    }

    @Override
    public String getUsername() {
        return getUserEmail();
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
