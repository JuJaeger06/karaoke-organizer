package br.csi.karaoke_organizer.model.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoasRepository extends JpaRepository<Pessoas, Integer> {
    public Pessoas findByCpfPessoa(String cpf);
}
