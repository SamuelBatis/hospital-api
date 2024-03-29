package med.voll.api.controller;

import med.voll.api.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repositories.PacienteRepository;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

  @Autowired
  private PacienteService service;

  @PostMapping
  @Transactional
  public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
    var paciente = service.cadastrarPaciente(dados);
    var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente).toUri();
    return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
  }

  @GetMapping
  public ResponseEntity<Page<DadosListagemPaciente>> listar(
      @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
    var page = service.listarPacientes(paginacao);
    return ResponseEntity.ok(page);
  }

  @PutMapping
  @Transactional
  public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
    var paciente = service.atualizarPaciente(dados);
    return ResponseEntity.ok(paciente);
  }


  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity excluir(@PathVariable Long id) {
    service.excluirPaciente(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity detalhar(@PathVariable Long id) {
    var paciente = service.detalharPaciente(id);
    return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
  }

}
