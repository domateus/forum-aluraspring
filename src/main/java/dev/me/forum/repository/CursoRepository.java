package dev.me.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.me.forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

  public Curso findByNome(String nome);
}
