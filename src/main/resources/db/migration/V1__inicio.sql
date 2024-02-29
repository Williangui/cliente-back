CREATE TABLE IF NOT EXISTS cliente
(
    id       SERIAL PRIMARY KEY,
    nome     varchar(255) NOT NULL UNIQUE,
    endereco varchar(255),
    bairro   varchar(255)
);

CREATE TABLE IF NOT EXISTS contato
(
    id         SERIAL PRIMARY KEY,
    telefone   varchar(20) NOT NULL UNIQUE,
    cliente_id bigint      NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);