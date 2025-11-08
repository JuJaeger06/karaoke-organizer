package br.csi.karaoke_organizer.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TratadorDeErros {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErroDadosInvalidos(MethodArgumentNotValidException ex){
        List<FieldError> errors = ex.getFieldErrors();
        List<DadosErroValidacao> dados = new ArrayList<>();

        for(FieldError fe : errors){
            dados.add(new DadosErroValidacao(fe.getField(), fe.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(dados);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErroConflito(DataIntegrityViolationException ex) {
        return ResponseEntity.status(409).body("Conflito de Dados: A operação falhou devido a uma violação de unicidade.");
    }

    private record DadosErroValidacao(String field, String message){}

}