package com.apiUser.Entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "as_user")
public class UserEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_user;

   private String name;
   @Column(name = "last_name")
   private String lastName;
   private String email;
   private String password;
   private String city;
   private String country;

   @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinTable(
           name = "as_user_role",
           joinColumns = @JoinColumn(name = "id_user"),
           inverseJoinColumns = @JoinColumn(name = "id_role")
   )
   private Set <RoleEntity> roles;

}
