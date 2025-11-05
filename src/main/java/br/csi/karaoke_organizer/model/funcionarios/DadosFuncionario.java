package br.csi.karaoke_organizer.model.funcionarios;

public record DadosFuncionario(int id, String email) {
    public DadosFuncionario(Funcionarios funcionario) {
        this(funcionario.getId(), funcionario.getEmail());
    }
}
