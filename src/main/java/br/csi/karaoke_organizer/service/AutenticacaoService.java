package br.csi.karaoke_organizer.service;

import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import br.csi.karaoke_organizer.model.funcionarios.FuncionariosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final FuncionariosRepository repository;

    public AutenticacaoService(FuncionariosRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Funcionarios funcionarios = repository.findByEmail(login);

        if (funcionarios == null) {
            throw new UsernameNotFoundException("Funcionário não encontrado");
        }
        return funcionarios;
    }
}
