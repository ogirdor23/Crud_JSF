package cl.demo.crud.bean;

import cl.demo.crud.dao.ClienteDao;
import cl.demo.crud.model.Cliente;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@Named("clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int ORA_UNIQUE_VIOLATION = 1; // ORA-00001

    @Inject
    private ClienteDao dao;

    private List<Cliente> clientes;
    private Cliente cliente = new Cliente();

    @PostConstruct
    public void init() {
        cargar();
    }

    private void cargar() {
        try {
            clientes = dao.listar();
        } catch (SQLException e) {
            error("No se pudo cargar la lista: " + e.getMessage());
        }
    }

    /** Crea o actualiza según tenga id o no. */
    public void guardar() {
        try {
            boolean esNuevo = cliente.getId() == null;
            if (esNuevo) {
                dao.crear(cliente);
            } else {
                dao.actualizar(cliente);
            }
            info(esNuevo ? "Cliente creado" : "Cliente actualizado");
            cliente = new Cliente();
            cargar();
        } catch (SQLException e) {
            if (e.getErrorCode() == ORA_UNIQUE_VIOLATION) {
                error("Ya existe un cliente con ese email");
            } else {
                error("Error al guardar: " + e.getMessage());
            }
        }
    }

    public void editar(Cliente c) {
        // copia para no modificar la fila de la tabla hasta guardar
        this.cliente = new Cliente(c.getId(), c.getNombre(), c.getEmail(), c.getTelefono());
    }

    public void eliminar(Cliente c) {
        try {
            dao.eliminar(c.getId());
            info("Cliente eliminado");
            if (cliente.getId() != null && cliente.getId().equals(c.getId())) {
                cliente = new Cliente();
            }
            cargar();
        } catch (SQLException e) {
            error("Error al eliminar: " + e.getMessage());
        }
    }

    public void cancelar() {
        cliente = new Cliente();
    }

    private void info(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    private void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public List<Cliente> getClientes() { return clientes; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
