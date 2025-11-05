package br.csi.karaoke_organizer.controller;

import br.csi.karaoke_organizer.model.mesa.Mesas;
import br.csi.karaoke_organizer.model.musica.Musicas;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import br.csi.karaoke_organizer.service.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/musica")
@Tag(name = "Músicas", description = "Path relacionado a operações das músicas!")
public class MusicaController {
    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }



    @GetMapping("/listar")
    @Operation(summary = "Listar músicas", description = "Lista todas as músicas")
    public List<Musicas> listar() {
        return this.musicaService.getAllMusicas();
    }



    @GetMapping("/{id}")
    @Operation(summary = "Listar música específica", description = "Lista os dados de uma música específica através do id")
    public Musicas findById(@PathVariable Integer id) {
        return this.musicaService.getOneMusica(id);
    }



    @PostMapping
    @Transactional
    @Operation(summary = "Criar uma nova música", description = "Criar uma nova música e adicionar à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Música criada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Musicas.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity salvar(@RequestBody @Valid Musicas musica, UriComponentsBuilder uriBuilder) {
        this.musicaService.createMusica(musica);
        URI uri = uriBuilder.path("/musicas/{id}").buildAndExpand(musica.getMusicaId()).toUri();
        return ResponseEntity.created(uri).body(musica); // Retorno 201
    }




    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar música", description = "Atualiza os dados de uma música existente")
    public ResponseEntity atualizar(@RequestBody @Valid Musicas musica) {
        this.musicaService.updateMusica(musica);
        return ResponseEntity.ok().build();
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar música", description = "Deleta uma música específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Música deletada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "403", description = "Não foi possível encontrar a música!", content = @Content)
    })
    public ResponseEntity deletar(@PathVariable int id) {
        this.musicaService.deleteMusica(id);
        return ResponseEntity.noContent().build(); // Retorno 204
    }



    @GetMapping("/cantores/{id}")
    @Operation(summary = "Listar cantores", description = "Lista todas as pessoas que são cantores desta música")
    public List<Pessoas> getCantoresDaMusica(@PathVariable int id) {
        return this.musicaService.getPessoasByMusica(id);
    }




    @PostMapping("/adicionar/{musicaId}/cantor/{pessoaId}")
    @Transactional
    @Operation(summary = "Adicionar Cantor", description = "Adiciona uma pessoa como cantora em uma música específica")
    public ResponseEntity adicionarCantor(@PathVariable int musicaId, @PathVariable int pessoaId) {
        this.musicaService.adicionarPessoa(musicaId, pessoaId);
        return ResponseEntity.ok().build();
    }




    @DeleteMapping("/remover/{musicaId}/cantor/{pessoaId}")
    @Transactional
    @Operation(summary = "Remover Cantor", description = "Remove uma pessoa como cantora de uma música específica")
    public ResponseEntity removerCantor(@PathVariable int musicaId, @PathVariable int pessoaId) {
        this.musicaService.removePessoa(musicaId, pessoaId);
        return ResponseEntity.ok().build();
    }
}