package pe.edu.utp.inventariopedidos.model;

import java.time.LocalDate;

public class Pedido {

    private Long id;
    private Long clienteId;
    private LocalDate fechaPedido;
    private String estado; // "PENDIENTE", "ENVIADO", "ENTREGADO", "CANCELADO"
    private Double total;

    public Pedido() {
    }

    public Pedido(Long id, Long clienteId, LocalDate fechaPedido, String estado, Double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}