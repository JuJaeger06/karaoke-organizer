package br.csi.karaoke_organizer.controller;

import br.csi.karaoke_organizer.model.pessoa.Pessoas;
import br.csi.karaoke_organizer.service.PessoaService;
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
@RequestMapping("/pessoa")
@Tag(name = "Pessoas", description = "Path relacionado a operações de pessoas!")
public class PessoaController {
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }



    @GetMapping("/listar")
    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas")
    public List<Pessoas> listar() {
        return this.pessoaService.getAllPessoas();
    }



    @GetMapping("/{id}")
    @Operation(summary = "Listar pessoa específica", description = "Lista os dados de uma pessoa específica através do id")
    public Pessoas findById(@PathVariable Integer id) {
        return this.pessoaService.getOnePessoa(id);
    }



    @PostMapping
    @Transactional
    @Operation(summary = "Criar uma nova pessoa", description = "Cria uma nova pessoa e adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pessoas.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity salvar(@RequestBody @Valid Pessoas pessoa, UriComponentsBuilder uriBuilder) {
        this.pessoaService.createPessoa(pessoa);
        URI uri = uriBuilder.path("/pessoas/{id}").buildAndExpand(pessoa.getPessoaId()).toUri();
        return ResponseEntity.created(uri).body(pessoa); // Retorno 201
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pessoa", description = "Deleta uma pessoa específica pelo ID")
    public ResponseEntity deletar(@PathVariable int id) {
        this.pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build(); // Retorno 204
    }
}