package dev.me.forum.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.me.forum.Controller.dto.DetailedTopicoDto;
import dev.me.forum.Controller.dto.TopicoDto;
import dev.me.forum.Controller.form.TopicoForm;
import dev.me.forum.Controller.form.UpdateTopicoForm;
import dev.me.forum.modelo.Topico;
import dev.me.forum.repository.CursoRepository;
import dev.me.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping
  public List<TopicoDto> lista(String nomeCurso) {
    if (nomeCurso == null) {

      List<Topico> topicos = topicoRepository.findAll();
      return TopicoDto.convert(topicos);
    } else {

      List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
      return TopicoDto.convert(topicos);
    }
  }

  @PostMapping
  @Transactional
  public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {

    Topico topico = form.convert(cursoRepository);
    topicoRepository.save(topico);

    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    return ResponseEntity.created(uri).body(new TopicoDto(topico));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailedTopicoDto> detail(@PathVariable Long id) {

    Optional<Topico> topico = topicoRepository.findById(id);
    if (topico.isPresent())
      return ResponseEntity.ok(new DetailedTopicoDto(topico.get()));

    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicoForm form) {
    Optional<Topico> optional = topicoRepository.findById(id);
    if (optional.isPresent()) {
      Topico topico = form.update(id, topicoRepository);
      return ResponseEntity.ok(new TopicoDto(topico));
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> remove(@PathVariable Long id) {
    Optional<Topico> optional = topicoRepository.findById(id);
    if (optional.isPresent()) {
      topicoRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

}
