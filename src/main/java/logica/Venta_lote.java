
package logica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class Venta_lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venta_lote;
    
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Ventas venta;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lotes lote;
    
    private float precio;

    public Venta_lote() {
    }

    public Venta_lote(int id_venta_lote, Ventas venta, Lotes lote, float precio) {
        this.id_venta_lote = id_venta_lote;
        this.venta = venta;
        this.lote = lote;
        this.precio = precio;
    }

    public int getId_venta_lote() {
        return id_venta_lote;
    }

    public void setId_venta_lote(int id_venta_lote) {
        this.id_venta_lote = id_venta_lote;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public Lotes getLote() {
        return lote;
    }

    public void setLote(Lotes lote) {
        this.lote = lote;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
}
