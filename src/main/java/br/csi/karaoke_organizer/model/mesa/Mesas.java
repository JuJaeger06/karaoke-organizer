package br.csi.karaoke_organizer.model.mesa;

import br.csi.karaoke_organizer.model.musica.Musicas;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "mesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mesas {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mesaId;
    private String nomeMesa;

    @OneToMany(mappedBy = "mesa")
    @JsonManagedReference // Esta anotação diz para serializar este campo
    private List<Pessoas> participantesMesa;

    @ManyToMany
    @JoinTable(
        name = "mesas_musicas",
        joinColumns = @JoinColumn(name = "mesa_id"),
        inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private List<Musicas> lstMusicas;

    public String getNomeMesa() {
        return nomeMesa;
    }

    public void setNomeMesa(String nomeMesa) {
        this.nomeMesa = nomeMesa;
    }

    public Integer getMesaId() {
        return mesaId;
    }

    public List<Pessoas> getParticipantesMesa() {
        return participantesMesa;
    }

    public void setParticipantesMesa(List<Pessoas> participantesMesa) {
        this.participantesMesa = participantesMesa;
    }

    public List<Musicas> getLstMusicas() {
        return lstMusicas;
    }

    public void setLstMusicas(List<Musicas> lstMusicas) {
        this.lstMusicas = lstMusicas;
    }
}