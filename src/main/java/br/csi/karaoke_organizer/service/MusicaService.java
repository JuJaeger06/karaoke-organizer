package br.csi.karaoke_organizer.service;

import br.csi.karaoke_organizer.model.musica.Musicas;
import br.csi.karaoke_organizer.model.musica.MusicasRepository;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MusicaService {
    private final MusicasRepository repository;
    private final PessoaService pessoasService;

    public MusicaService(MusicasRepository repository, PessoaService pessoasService) {
        this.repository = repository;
        this.pessoasService = pessoasService;
    }

    public void createMusica(Musicas musica) {
        this.repository.save(musica);
    }

    public List<Musicas> getAllMusicas() {
        return this.repository.findAll();
    }

    public Musicas getOneMusica(int id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Música não encontrada com ID: " + id));
    }

    public void updateMusica(Musicas musica) {
        Musicas song = this.repository.getReferenceById(musica.getMusicaId());
        song.setNomeMusica(musica.getNomeMusica());
        song.setCantorMusica(musica.getCantorMusica());
        song.setCantores(musica.getCantores());

        this.repository.save(song);
    }

    public void deleteMusica(int id) {
        this.repository.deleteById(id);
    }

    public void adicionarPessoa(int musicaId, int pessoaId) {
        Musicas musica = this.repository.findById(musicaId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada com ID: " + musicaId));

        Pessoas pessoa = this.pessoasService.getOnePessoa(pessoaId);

        musica.getCantores().add(pessoa);

        this.repository.save(musica);
    }

    public List<Pessoas> getPessoasByMusica(int id) {
        Musicas musica = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada com ID: " + id));

        return musica.getCantores();
    }


    public void removePessoa(int musicaId, int pessoaId) {
        Musicas song = this.repository.findById(musicaId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada com ID: " + musicaId));

        boolean removed = song.getCantores()
                .removeIf(p -> p.getPessoaId().equals(pessoaId));

        if (removed) {
            this.repository.save(song);
        } else {
            throw new RuntimeException("Pessoa com ID " + pessoaId + " não encontrada na Música " + musicaId);
        }
    }
}
