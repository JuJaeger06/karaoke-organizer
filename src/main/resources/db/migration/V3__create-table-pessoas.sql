CREATE TABLE pessoas (
     pessoa_id SERIAL PRIMARY KEY,
     nome_pessoa VARCHAR(255),
     cpf_pessoa VARCHAR(11) UNIQUE,
     qtd_vez_cantada INT,
     mesa_pessoa_id INTEGER NOT NULL,
     CONSTRAINT fk_mesa FOREIGN KEY (mesa_pessoa_id) REFERENCES mesas(mesa_id)
);