package br.csi.karaoke_organizer.controller;

import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import br.csi.karaoke_organizer.model.mesa.Mesas;
import br.csi.karaoke_organizer.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
@Tag(name = "Funcionários", description = "Path relacionado a operações dos funcionários!")
public class FuncionarioController {
    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo Funcionário", description = "Criar um novo funcionário e adicionar à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Funcionarios.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity criar(@RequestBody @Valid Funcionarios funcionarios, UriComponentsBuilder uriBuilder) {
        this.funcionarioService.cadastrar(funcionarios);
        URI uri = uriBuilder.path("/funcionarios/{id}").buildAndExpand(funcionarios.getId()).toUri();
        return ResponseEntity.created(uri).body(funcionarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista Funcionário específico", description = "Lista os dados de um Funcionário específico através do id")
    public Funcionarios findById(@PathVariable Integer id) {
        return this.funcionarioService.findFuncionario(id);
    }

    @GetMapping
    @Operation(summary = "Listar Funcionários", description = "Lista todas os funcionários que já foram criados")
    public List<Funcionarios> findAll() {
        return this.funcionarioService.findAllFuncionarios();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Funcionário", description = "Deleta um Funcionário específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário deletado com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mesas.class))),
            @ApiResponse(responseCode = "403", description = "Não foi possível encontrar o Funcionário!", content = @Content)
    })
    public ResponseEntity deletar(@PathVariable int id) {
        this.funcionarioService.deleteFuncionario(id);
        return ResponseEntity.noContent().build(); // Retorno 204
    }
}
