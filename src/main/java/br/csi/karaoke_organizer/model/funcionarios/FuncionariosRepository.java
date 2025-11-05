package br.csi.karaoke_organizer.model.funcionarios;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {
    public Funcionarios findByEmail(String email);
}