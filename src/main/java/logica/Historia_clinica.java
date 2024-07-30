
package logica;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Historia_clinica extends Animal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_historia;
    private String suplementos;
    private String enfermedades;
    private String tratamiento;
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public Historia_clinica() {
    }

    public Historia_clinica(int id_historia, String suplementos, String enfermedades, String tratamiento, String descripcion, Animal animal, int id_animal, int num_animal, String raza, String Tipo_sexo, Date Fecha_cambios, List<Peso_animal> pesos, Lotes lote) {
        super(id_animal, num_animal, raza, Tipo_sexo, Fecha_cambios, pesos, lote);
        this.id_historia = id_historia;
        this.suplementos = suplementos;
        this.enfermedades = enfermedades;
        this.tratamiento = tratamiento;
        this.descripcion = descripcion;
        this.animal = animal;
    }

    public int getId_historia() {
        return id_historia;
    }

    public void setId_historia(int id_historia) {
        this.id_historia = id_historia;
    }

    public String getSuplementos() {
        return suplementos;
    }

    public void setSuplementos(String suplementos) {
        this.suplementos = suplementos;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    
    
}
