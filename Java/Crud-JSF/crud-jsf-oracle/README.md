# CRUD Java + JSF (Jakarta Faces 4) + Oracle

## Requisitos
- JDK 17+
- Maven 3.9+
- Servidor Jakarta EE 10: Payara 6, WildFly 27+ o GlassFish 7
  (en Tomcat 10.1 necesitas añadir las libs de Mojarra y Weld)
- Oracle Database 12c+ (XE sirve)

## Pasos
1. Crea el usuario y ejecuta `sql/schema.sql` con ese usuario.
2. Edita `src/main/resources/db.properties` (url, user, password).
3. `mvn clean package`
4. Despliega `target/crud-jsf-oracle.war`
5. Abre http://localhost:8080/crud-jsf-oracle/

## Estructura
- model/Cliente         -> entidad
- dao/ClienteDao        -> JDBC (CRUD con PreparedStatement)
- bean/ClienteBean      -> managed bean @ViewScoped
- webapp/clientes.xhtml -> vista
