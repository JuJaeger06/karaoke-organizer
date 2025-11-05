package br.csi.karaoke_organizer.service;

import br.csi.karaoke_organizer.model.mesa.Mesas;
import br.csi.karaoke_organizer.model.mesa.MesasRepository;
import br.csi.karaoke_organizer.model.musica.Musicas;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MesaService {
    private final MesasRepository repository;
    private final PessoaService pessoasService;
    private final MusicaService musicaService;

    public MesaService(MesasRepository repository, PessoaService pessoasService, MusicaService musicaService) {
        this.repository = repository;
        this.pessoasService = pessoasService;
        this.musicaService = musicaService;
    }

    public void createMesa(Mesas mesa) {
        this.repository.save(mesa);
    }

    public List<Mesas> getAllMesas() {
        return this.repository.findAll();
    }

    public Mesas getOneMesa(int id) {
        return this.repository.findById(id).orElseThrow(() -> new NoSuchElementException("Mesa não encontrada com ID: " + id));
    }

    public void deleteMesa(int id) {
        this.repository.deleteById(id);
    }

    public void adicionarMusica(int mesaId, int musicaId) {
        Mesas mesa = this.repository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada com ID: " + mesaId));

        Musicas musica = this.musicaService.getOneMusica(musicaId);

        mesa.getLstMusicas().add(musica);

        this.repository.save(mesa);
    }

    public List<Pessoas> getPessoasByMesas(int id) {
        Mesas mesa = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada com ID: " + id));

        return mesa.getParticipantesMesa();
    }
}