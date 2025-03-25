CREATE TABLE IF NOT EXISTS cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    limite INTEGER NOT NULL,
    saldo INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transacao (
    id SERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    valor INTEGER NOT NULL,
    tipo CHAR(1) NOT NULL,
    descricao VARCHAR(10) NOT NULL,
    realizada_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

INSERT INTO cliente (id, nome, limite, saldo) 
VALUES
(1, 'o barato sai caro', 100000, 0),
(2, 'zan corp ltda', 80000, 0),
(3, 'les cruders', 1000000, 0),
(4, 'padaria joia de cocaia', 10000000, 0),
(5, 'kid mais', 500000, 0)
ON CONFLICT (id) DO NOTHING;
