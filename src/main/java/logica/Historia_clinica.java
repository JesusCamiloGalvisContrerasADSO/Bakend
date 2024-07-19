
package logica;

import java.util.Date;
import java.util.List;


public class Historia_clinica extends Animal{
    private int id_historia;
    private String Suplementos;
    private String Enfermedades;
    private String Tratamiento;
    private String Descripcion;

    public Historia_clinica() {
    }

    public Historia_clinica(int id_historia, String Suplementos, String Enfermedades, String Tratamiento, String Descripcion, int id_animal, int num_animal, String raza, String Tipo_sexo, Date Fecha_cambios, List<Peso_animal> pesos, Lotes lote) {
        super(id_animal, num_animal, raza, Tipo_sexo, Fecha_cambios, pesos, lote);
        this.id_historia = id_historia;
        this.Suplementos = Suplementos;
        this.Enfermedades = Enfermedades;
        this.Tratamiento = Tratamiento;
        this.Descripcion = Descripcion;
    }

    public int getId_historia() {
        return id_historia;
    }

    public void setId_historia(int id_historia) {
        this.id_historia = id_historia;
    }

    public String getSuplementos() {
        return Suplementos;
    }

    public void setSuplementos(String Suplementos) {
        this.Suplementos = Suplementos;
    }

    public String getEnfermedades() {
        return Enfermedades;
    }

    public void setEnfermedades(String Enfermedades) {
        this.Enfermedades = Enfermedades;
    }

    public String getTratamiento() {
        return Tratamiento;
    }

    public void setTratamiento(String Tratamiento) {
        this.Tratamiento = Tratamiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
    
}
