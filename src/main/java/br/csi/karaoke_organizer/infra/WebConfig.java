package br.csi.karaoke_organizer.infra;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@Configuration
public class WebConfig {

    private final TratadorDeErros tratadorDeErros;

    public WebConfig(TratadorDeErros tratadorDeErros) {
        this.tratadorDeErros = tratadorDeErros;
    }

    @Bean
    public HandlerExceptionResolver customExceptionResolver() {
        return new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler,
                    Exception ex) {

                if (ex instanceof NoSuchElementException) {
                    ResponseEntity responseEntity = tratadorDeErros.tratarErro404();

                    response.setStatus(responseEntity.getStatusCode().value());
                    return new ModelAndView();
                }
                return null;
            }
        };
    }
}