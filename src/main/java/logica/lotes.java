
package logica;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Lotes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_lote;
    private String Tipo_raza;
    
    @Temporal(TemporalType.DATE)
    private Date Fecha_cambios;
    private int Cantidad_animales;
    
    @OneToMany(mappedBy = "lote")
    private List<Animal> animales;
    
    @ManyToOne
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    
    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

    
    

    public Lotes() {
    }

    public Lotes(int id_lote, String Tipo_raza, Date Fecha_cambios, int Cantidad_animales, List<Animal> animales, Trabajador trabajador, Administrador administrador) {
        this.id_lote = id_lote;
        this.Tipo_raza = Tipo_raza;
        this.Fecha_cambios = Fecha_cambios;
        this.Cantidad_animales = Cantidad_animales;
        this.animales = animales;
        this.trabajador = trabajador;
        this.administrador = administrador;
    }

    public int getId_lote() {
        return id_lote;
    }

    public void setId_lote(int id_lote) {
        this.id_lote = id_lote;
    }

    public String getTipo_raza() {
        return Tipo_raza;
    }

    public void setTipo_raza(String Tipo_raza) {
        this.Tipo_raza = Tipo_raza;
    }

    public Date getFecha_cambios() {
        return Fecha_cambios;
    }

    public void setFecha_cambios(Date Fecha_cambios) {
        this.Fecha_cambios = Fecha_cambios;
    }

    public int getCantidad_animales() {
        return Cantidad_animales;
    }

    public void setCantidad_animales(int Cantidad_animales) {
        this.Cantidad_animales = Cantidad_animales;
    }

    public List<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    


    
    
    
    
    
    
}
