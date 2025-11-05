package br.csi.karaoke_organizer.controller;

import br.csi.karaoke_organizer.model.mesa.Mesas;
import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import br.csi.karaoke_organizer.service.MesaService;
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
@RequestMapping("/mesa")
@Tag(name = "Mesas", description = "Path relacionado a operações das mesas!")
public class MesaController {
    private MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }



    @GetMapping("/listar")
    @Operation(summary = "Listar mesas", description = "Lista todas as mesas")
    public List<Mesas> listar() {
        return this.mesaService.getAllMesas();
    }



    @GetMapping("/{id}")
    @Operation(summary = "Listar mesa específica", description = "Lista os dados de uma mesa específica através do id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa encontrada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "403", description = "Não foi possível encontrar a mesa!", content = @Content)
    })
    public Mesas mesas(@PathVariable int id) {
        return this.mesaService.getOneMesa(id);
    }



    @PostMapping()
    @Transactional
    @Operation(summary = "Criar uma nova mesa", description = "Criar uma nova mesa e adicionar à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa criada com sucesso!",
                content = @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity salvar(@RequestBody Mesas mesas, UriComponentsBuilder uriBuilder) {
        this.mesaService.createMesa(mesas);
        URI uri = uriBuilder.path("/mesas/{id}").buildAndExpand(mesas.getMesaId()).toUri();
        return ResponseEntity.created(uri).body(mesas); // Retorno 201
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar mesa", description = "Deleta uma mesa específica pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa deletada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "403", description = "Não foi possível encontrar a mesa!", content = @Content)
    })
    public ResponseEntity deletar(@PathVariable int id) {
        this.mesaService.deleteMesa(id);
        return ResponseEntity.noContent().build(); // Retorno 204
    }



    @GetMapping("/{id}/pessoas")
    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas presentes na mesa")
    public List<Pessoas> getPessoasNaMesa(@PathVariable int id) {
        return this.mesaService.getPessoasByMesas(id);
    }



    @PostMapping("/adicionar/{mesaId}/musica/{musicaId}")
    @Transactional
    @Operation(summary = "Adicionar música", description = "Adicionar uma música para uma mesa específica pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Música adicionada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "400", description = "Falha ao adicionar uma música!", content = @Content)
    })
    public ResponseEntity adicionarMusica(@PathVariable int mesaId, @PathVariable int musicaId) {
        this.mesaService.adicionarMusica(mesaId, musicaId);
        return ResponseEntity.ok().build(); // Retorno 200/204 para OK/Sucesso com corpo
    }
}
