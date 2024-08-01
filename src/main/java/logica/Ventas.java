
package logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity

public class Ventas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venta;
    private Date fecha_venta;
    private float precio_total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Venta_lote> ventaLotes;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<Venta_animal> ventaAnimales;

    public Ventas() {
    }

    public Ventas(int id_venta, Date fecha_venta, float precio_total, List<Venta_animal> ventaAnimales, List<Venta_lote> ventaLotes) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.precio_total = precio_total;
        this.ventaAnimales = ventaAnimales;
        this.ventaLotes = ventaLotes;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public Date getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Date fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public List<Venta_animal> getVentaAnimales() {
        return ventaAnimales;
    }

    public void setVentaAnimales(List<Venta_animal> ventaAnimales) {
        this.ventaAnimales = ventaAnimales;
    }

    public List<Venta_lote> getVentaLotes() {
        return ventaLotes;
    }

    public void setVentaLotes(List<Venta_lote> ventaLotes) {
        this.ventaLotes = ventaLotes;
    }
    
    
}
