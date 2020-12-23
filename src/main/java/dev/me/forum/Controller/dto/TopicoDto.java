package dev.me.forum.Controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.me.forum.modelo.Topico;

public class TopicoDto {

  private Long id;
  private String titulo;
  private String mensagem;
  private LocalDateTime dataCriacao;

  public TopicoDto(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mensagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public static List<TopicoDto> convert(List<Topico> topicos) {
    return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
  }

}
