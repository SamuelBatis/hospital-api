package med.voll.api.services;

import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

  @Autowired
  private MedicoRepository repository;

  public Medico cadastrarMedico(DadosCadastroMedico dados) {
    var medico = new Medico(dados);
    repository.save(medico);
    return medico;
  }

  public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
    return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
  }

  public Medico atualizarMedico(DadosAtualizacaoMedico dados) {
    var medico = repository.getReferenceById(dados.id());
    medico.atualizarInformacoe(dados);
    return medico;
  }

  public void excluirMedico(Long id) {
    var medico = repository.getReferenceById(id);
    medico.excluir();
  }

  public Medico detalharMedico(Long id) {
    return repository.getReferenceById(id);
  }


}
