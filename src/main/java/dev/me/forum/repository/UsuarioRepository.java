package dev.me.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.me.forum.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByEmail(String email);

}
