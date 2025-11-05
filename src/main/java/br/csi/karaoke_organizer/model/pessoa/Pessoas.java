package br.csi.karaoke_organizer.model.pessoa;

import br.csi.karaoke_organizer.model.mesa.Mesas;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoas {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pessoaId;
    private String nomePessoa;
    private String cpfPessoa;
    private Integer qtdVezCantada;
    @ManyToOne
    @JoinColumn(name = "mesa_pessoa_id")
    @JsonBackReference // Esta anotação impede o loop de Mesa -> Pessoa -> Mesa
    private Mesas mesa;

    public Number getQtdVezCantada() {
        return qtdVezCantada;
    }

    public void setQtdVezCantada(Integer qtdVezCantada) {
        this.qtdVezCantada = qtdVezCantada;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getCpfPessoa() {
        return cpfPessoa;
    }

    public void setCpfPessoa(String cpfPessoa) {
        this.cpfPessoa = cpfPessoa;
    }

    public Number qtdVezCantada() {
        return qtdVezCantada;
    }

    public void qtdVezCantada(Integer nVezCantada) {
        this.qtdVezCantada = nVezCantada;
    }

    public Mesas getMesa() {
        return mesa;
    }

    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }
}