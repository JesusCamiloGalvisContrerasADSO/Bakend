
package logica;

import java.util.Date;


public class Persona {
    
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String tipo_sangre;
    private String tipo_doc;
    private String direccion;
    private Date fecha_naci;
    private Date fecha_inicio_contrato;
    private String descripcion;
 

    public Persona() {
    }

    public Persona( String nombre, String apellido, String telefono, String email, String tipo_sangre, String tipo_doc, String direccion, Date fecha_naci, Date fecha_inicio_contrato, String descripcion) {
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.tipo_sangre = tipo_sangre;
        this.tipo_doc = tipo_doc;
        this.direccion = direccion;
        this.fecha_naci = fecha_naci;
        this.fecha_inicio_contrato = fecha_inicio_contrato;
        this.descripcion = descripcion;
        
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_naci() {
        return fecha_naci;
    }

    public void setFecha_naci(Date fecha_naci) {
        this.fecha_naci = fecha_naci;
    }

    public Date getFecha_inicio_contrato() {
        return fecha_inicio_contrato;
    }

    public void setFecha_inicio_contrato(Date fecha_inicio_contrato) {
        this.fecha_inicio_contrato = fecha_inicio_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
    
}
