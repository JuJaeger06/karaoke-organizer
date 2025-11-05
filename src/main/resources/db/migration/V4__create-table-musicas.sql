CREATE TABLE musicas (
    musica_id SERIAL PRIMARY KEY,
    nome_musica VARCHAR(150) NOT NULL,
    cantor_musica VARCHAR(150)
);


CREATE TABLE musica_cantores (
    musica_id INTEGER REFERENCES musicas(musica_id) ON DELETE CASCADE,
    pessoa_id INTEGER REFERENCES pessoas(pessoa_id) ON DELETE CASCADE,
    PRIMARY KEY (musica_id, pessoa_id)
);

CREATE TABLE mesas_musicas (
    mesa_id INTEGER REFERENCES mesas(mesa_id) ON DELETE CASCADE,
    musica_id INTEGER REFERENCES musicas(musica_id) ON DELETE CASCADE,
    PRIMARY KEY (mesa_id, musica_id)
);