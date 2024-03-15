package com.example.security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_user")
public class User
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
   private String firstname;
   private String lastname;
   private String email;
   private String password;
}
