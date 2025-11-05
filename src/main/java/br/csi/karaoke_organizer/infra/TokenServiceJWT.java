package br.csi.karaoke_organizer.infra;

import br.csi.karaoke_organizer.model.funcionarios.Funcionarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceJWT {
    public String gerarToken(Funcionarios func) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("karaokeToken");
            return JWT.create()
                    .withIssuer("API Karaoke Organizer")
                    .withSubject(func.getNome())
                    .withSubject(func.getEmail())
//                    .withClaim("ROLE", func.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("karaokeToken");
            return JWT.require(algorithm)
                    .withIssuer("API Karaoke Organizer")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}
