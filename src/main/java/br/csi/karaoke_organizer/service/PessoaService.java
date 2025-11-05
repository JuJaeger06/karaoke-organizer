package br.csi.karaoke_organizer.service;

import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import br.csi.karaoke_organizer.model.pessoa.PessoasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PessoaService {
    private final PessoasRepository repository;

    public PessoaService(PessoasRepository repository) {
        this.repository = repository;
    }

    public void createPessoa(Pessoas pessoa) {
        Pessoas existente = this.repository.findByCpfPessoa(pessoa.getCpfPessoa());

        if (existente != null) {
            throw new IllegalArgumentException("Pessoa com cpf já cadastrado!");
        }

        this.repository.save(pessoa);
    }

    public List<Pessoas> getAllPessoas() {
        return this.repository.findAll();
    }

    public Pessoas getOnePessoa(int id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa não encontrada com ID: " + id));
    }

    public void deletePessoa(int id) {
        this.repository.deleteById(id);
    }
}
