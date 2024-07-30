
package logica;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_animal;
    private int num_animal;
    private String raza;
    private String Tipo_sexo;
    private Date Fecha_cambios;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lotes lote;

    @OneToMany(mappedBy = "animal")
    private List<Peso_animal> pesos;

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    private Historia_clinica historiaClinica;

    public Animal() {
    }

    public Animal(int id_animal, int num_animal, String raza, String Tipo_sexo, Date Fecha_cambios, List<Peso_animal> pesos, Lotes lote) {
        this.id_animal = id_animal;
        this.num_animal = num_animal;
        this.raza = raza;
        this.Tipo_sexo = Tipo_sexo;
        this.Fecha_cambios = Fecha_cambios;
        this.pesos = pesos;
        this.lote = lote;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public int getNum_animal() {
        return num_animal;
    }

    public void setNum_animal(int num_animal) {
        this.num_animal = num_animal;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTipo_sexo() {
        return Tipo_sexo;
    }

    public void setTipo_sexo(String Tipo_sexo) {
        this.Tipo_sexo = Tipo_sexo;
    }

    public Date getFecha_cambios() {
        return Fecha_cambios;
    }

    public void setFecha_cambios(Date Fecha_cambios) {
        this.Fecha_cambios = Fecha_cambios;
    }

    public List<Peso_animal> getPesos() {
        return pesos;
    }

    public void setPesos(List<Peso_animal> pesos) {
        this.pesos = pesos;
    }

    public Lotes getLote() {
        return lote;
    }

    public void setLote(Lotes lote) {
        this.lote = lote;
    }

    
    
}
