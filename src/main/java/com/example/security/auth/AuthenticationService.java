package com.example.security.auth;

import com.example.security.config.JwtService;
import com.example.security.repository.UserRepository;
import com.example.security.user.Role;
import com.example.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{

   private final UserRepository repository;
   private final PasswordEncoder passwordEncoder;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;

   public AuthenticationResponse register(RegisterRequest request)
   {
      User user = User.builder()
                      .firstname(request.getFirstname())
                      .lastname(request.getLastname())
                      .email(request.getEmail())
                      .password(passwordEncoder.encode(request.getPassword()))
                      .role(Role.USER)
                      .build();
      repository.save(user);
      String jwtToken = jwtService.generateToken(user);

      return AuthenticationResponse.builder()
                                   .token(jwtToken)
                                   .build();
   }

   public AuthenticationResponse authenticate(AuthenticationRequest request)
   {
      authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
               )
      );
      User user = repository.findByEmail(request.getEmail())
                            .orElseThrow();

      String jwtToken = jwtService.generateToken(user);
      return AuthenticationResponse.builder()
                                   .token(jwtToken)
                                   .build();
   }
}
