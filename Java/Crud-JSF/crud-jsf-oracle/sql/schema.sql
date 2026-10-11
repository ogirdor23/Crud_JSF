-- Oracle 12c+ (columna IDENTITY)
CREATE TABLE clientes (
  id        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nombre    VARCHAR2(100) NOT NULL,
  email     VARCHAR2(150) NOT NULL,
  telefono  VARCHAR2(30),
  CONSTRAINT uq_clientes_email UNIQUE (email)
);

INSERT INTO clientes (nombre, email, telefono) VALUES ('Ana Pérez', 'ana@ejemplo.cl', '+56911111111');
COMMIT;
