package br.csi.karaoke_organizer.model.musica;

import br.csi.karaoke_organizer.model.mesa.Mesas;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "musicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Musicas {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer musicaId;
    private String nomeMusica;
    private String cantorMusica;
    @ManyToMany
    @JoinTable(
            name = "musica_cantores",
            joinColumns = @JoinColumn(name = "musica_id"),
            inverseJoinColumns = @JoinColumn(name = "pessoa_id")
    )
    private List<Pessoas> cantores;

    public Integer getMusicaId() {
        return musicaId;
    }

    public void setMusicaId(Integer musicaId) {
        this.musicaId = musicaId;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }

    public void setNomeMusica(String nomeMusica) {
        this.nomeMusica = nomeMusica;
    }

    public String getCantorMusica() {
        return cantorMusica;
    }

    public void setCantorMusica(String cantorMusica) {
        this.cantorMusica = cantorMusica;
    }

    public List<Pessoas> getCantores() {
        return cantores;
    }

    public void setCantores(List<Pessoas> cantores) {
        this.cantores = cantores;
    }
}
