CREATE TABLE historial_llamadas (
    id BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP DEFAULT now() not	 NULL,
    endpoint TEXT NOT NULL,
    parametros JSONB,
    respuesta JSONB,
    error TEXT,
    status_code INT NOT NULL CHECK (status_code BETWEEN 100 AND 599)
);