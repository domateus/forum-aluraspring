package dev.me.forum.Controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import dev.me.forum.modelo.Topico;
import dev.me.forum.repository.TopicoRepository;

/**
 * UpdateTopicoForm
 */
public class UpdateTopicoForm {

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String titulo;

  @NotNull
  @NotEmpty
  @Length(min = 10)
  private String mensagem;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Topico update(Long id, TopicoRepository topicoRepository) {
    Topico topico = topicoRepository.getOne(id);

    topico.setTitulo(this.titulo);
    topico.setMensagem(this.mensagem);

    return topico;
  }

}
