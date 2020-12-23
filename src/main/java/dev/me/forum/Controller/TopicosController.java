package dev.me.forum.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.me.forum.Controller.dto.TopicoDto;
import dev.me.forum.modelo.Curso;
import dev.me.forum.modelo.Topico;

@RestController
public class TopicosController {

  @RequestMapping("/topicos")
  public List<TopicoDto> lista() {
    Topico topico = new Topico("Dúvida", "Duvida com springframework", new Curso("Spring", "Programação"));

    return TopicoDto.convert(Arrays.asList(topico, topico, topico));
  }

}
