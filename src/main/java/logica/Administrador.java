
package logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Administrador extends Persona implements Serializable {
//    private int id_Admin;
    private Usuario unUsuario;
    @OneToMany(mappedBy = "administrador")
    private List<Lotes> listaLotes;
    
    public Administrador() {
    }

    public Administrador(Usuario unUsuario, List<Lotes> listaLotes, int id, String nombre, String apellido, String telefono, String email, String tipo_sangre, String tipo_doc, String direccion, Date fecha_naci, Date fecha_inicio_contrato, String descripcion) {
        super(id, nombre, apellido, telefono, email, tipo_sangre, tipo_doc, direccion, fecha_naci, fecha_inicio_contrato, descripcion);
        this.unUsuario = unUsuario;
        this.listaLotes = listaLotes;
    }

    
      
//    public int getId_Admin() {
//        return id_Admin;
//    }
//
//    public void setId_Admin(int id_Admin) {
//        this.id_Admin = id_Admin;
//    }

    public Usuario getUnUsuarui() {
        return unUsuario;
    }

    public void setUnUsuarui(Usuario unUsuarui) {
        this.unUsuario = unUsuarui;
    }

    public List<Lotes> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(List<Lotes> listaLotes) {
        this.listaLotes = listaLotes;
    }
    
    
    
    
    
}
