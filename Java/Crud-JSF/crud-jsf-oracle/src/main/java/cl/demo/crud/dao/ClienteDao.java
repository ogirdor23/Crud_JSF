package cl.demo.crud.dao;

import cl.demo.crud.model.Cliente;
import cl.demo.crud.util.Db;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClienteDao {

    private static final String SELECT_ALL =
            "SELECT id, nombre, email, telefono FROM clientes ORDER BY id";
    private static final String INSERT =
            "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";
    private static final String UPDATE =
            "UPDATE clientes SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM clientes WHERE id = ?";

    // READ
    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        try (Connection cn = Db.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")));
            }
        }
        return lista;
    }

    // CREATE
    public void crear(Cliente c) throws SQLException {
        try (Connection cn = Db.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            ps.executeUpdate();
        }
    }

    // UPDATE
    public void actualizar(Cliente c) throws SQLException {
        try (Connection cn = Db.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            ps.setLong(4, c.getId());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void eliminar(Long id) throws SQLException {
        try (Connection cn = Db.getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
