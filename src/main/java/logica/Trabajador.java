
package logica;

import java.util.Date;
import java.util.List;


public class Trabajador extends Persona{
    private int id_Trabajador;
    private Usuario unUsuario;
    private List<Lotes> listaLotes;

    public Trabajador() {
    }
    
    

    public Trabajador(int id_Trabajador, Usuario unUsuarui, List<Lotes> listaLotes, String nombre, String apellido, String telefono, String email, String tipo_sangre, String tipo_doc, String direccion, Date fecha_naci, Date fecha_inicio_contrato, String descripcion) {
        super(nombre, apellido, telefono, email, tipo_sangre, tipo_doc, direccion, fecha_naci, fecha_inicio_contrato, descripcion);
        this.id_Trabajador = id_Trabajador;
        this.unUsuario = unUsuario;
        this.listaLotes = listaLotes;
    }

    public int getId_Trabajador() {
        return id_Trabajador;
    }

    public void setId_Trabajador(int id_Trabajador) {
        this.id_Trabajador = id_Trabajador;
    }

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
