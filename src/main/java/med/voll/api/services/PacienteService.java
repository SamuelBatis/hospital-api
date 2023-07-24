package med.voll.api.services;

import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

  @Autowired
  private PacienteRepository repository;

  public Paciente cadastrarPaciente(DadosCadastroPaciente dados) {
    var paciente = new Paciente(dados);
    repository.save(paciente);
    return paciente;
  }

  public Page<DadosListagemPaciente> listarPacientes(Pageable paginacao) {
    var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    return page;
  }

  public Paciente atualizarPaciente(DadosAtualizacaoPaciente dados) {
    var paciente = repository.getReferenceById(dados.id());
    paciente.atualizarInformacoe(dados);
    return paciente;
  }

  public void excluirPaciente(Long id) {
    var paciente = repository.getReferenceById(id);
    paciente.excluir();
  }

  public Paciente detalharPaciente(Long id) {
    var paciente = repository.getReferenceById(id);
    return paciente;
  }

}
