package com.enigma.enigmaboot.dto;

import com.enigma.enigmaboot.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsImplDTO implements UserDetails {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String status;
    private String userName;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerDetailsImplDTO build(Customer customer){
        /*
        Loping untuk mendapatkan role
        Role banyak (admin, user, dll)
        .map (mengembalikan lagi data customer)
        .colect (untuk mengembalikan ke list - sebelumnya stream)
         */
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new CustomerDetailsImplDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getStatus(),
                customer.getUserName(),
                customer.getPassword(),
                customer.getPhoneNumber(),
                authorities
        );
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDetailsImplDTO)) return false;
        CustomerDetailsImplDTO that = (CustomerDetailsImplDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getDateOfBirth(), that.getDateOfBirth()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(userName, that.userName) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) && Objects.equals(getAuthorities(), that.getAuthorities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getDateOfBirth(), getStatus(), userName, getPassword(), getPhoneNumber(), getAuthorities());
    }
}
