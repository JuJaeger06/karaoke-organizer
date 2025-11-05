package br.csi.karaoke_organizer.service;

import br.csi.karaoke_organizer.infra.TratadorDeErros;
import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import br.csi.karaoke_organizer.model.funcionarios.FuncionariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FuncionarioService {
    private final FuncionariosRepository repository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionariosRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(Funcionarios funcionario) {
        Funcionarios existente = this.repository.findByEmail(funcionario.getEmail());

        if (existente != null) {
            throw new IllegalArgumentException("Funcionário com email já cadastrado!");
        }

        String senhaCriptografada = this.passwordEncoder.encode(funcionario.getSenha());
        funcionario.setSenha(senhaCriptografada);

        this.repository.save(funcionario);
    }

    public Funcionarios findFuncionario(int id) {
        return this.repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Funcionário não encontrado com ID: " + id)
        );
    }

    public List<Funcionarios> findAllFuncionarios() {
        return this.repository.findAll();
    }

    public void deleteFuncionario(int id) {
        this.repository.deleteById(id);
    }
}