package dev.me.forum.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.me.forum.Controller.dto.TokenDto;
import dev.me.forum.Controller.form.LoginForm;
import dev.me.forum.config.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {

    UsernamePasswordAuthenticationToken loginData = form.convert();
    try {

      Authentication authentication = authManager.authenticate(loginData);
      String token = tokenService.generateToken(authentication);

      return ResponseEntity.ok(new TokenDto(token, "Bearer"));

    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
