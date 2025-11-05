package br.csi.karaoke_organizer.controller;

import br.csi.karaoke_organizer.infra.TokenServiceJWT;
import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Path relacionado a operações de login")
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoController.class);

    public AutenticacaoController(AuthenticationManager manager, TokenServiceJWT tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Fazer login", description = "Default")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem sucedido",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DadosToken.class))}),
            @ApiResponse(responseCode = "401", description = "Usuário ou senha incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro interno de processamento do login")
    })
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DadosAutenticacao dados) {
        try {

            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            Authentication authentication = manager.authenticate(authenticationToken);

            var funcionarios = (Funcionarios) authentication.getPrincipal();
            String token = tokenService.gerarToken(funcionarios);

            return ResponseEntity.ok().body(new DadosToken(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorretos");
        } catch (Exception e) {
            logger.error("Erro interno ao processar login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno de processamento do login");
        }
    }

    private record DadosAutenticacao(String login, String senha) {}
    private record DadosToken(String token) {}

}
