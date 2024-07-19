
package logica;

import java.util.Date;
import java.util.List;


public class Lotes {
    
    private int id_lote;
    private String Tipo_raza;
    private Date Fecha_cambios;
    private int Cantidad_animales;
    private List<Animal> Animal;

    public Lotes() {
    }

    public Lotes(int id_lote, String Tipo_raza, Date Fecha_cambios, int Cantidad_animales, List<Animal> Animal) {
        this.id_lote = id_lote;
        this.Tipo_raza = Tipo_raza;
        this.Fecha_cambios = Fecha_cambios;
        this.Cantidad_animales = Cantidad_animales;
        this.Animal = Animal;
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

    public List<Animal> getAnimal() {
        return Animal;
    }

    public void setAnimal(List<Animal> Animal) {
        this.Animal = Animal;
    }

    
    
    
    
    
    
}
