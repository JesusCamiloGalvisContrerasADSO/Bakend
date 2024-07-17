
package logica;

import java.util.Date;

public class Persona {
    private int id_perfil;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;
    private Date fecha_naci;
    private Date fecha_inicio_contrato;
    private String descripcion;

    public Persona() {
    }

    public Persona(int id_perfil, String nombre, String apellido, String telefono, String email, String direccion, Date fecha_naci, Date fecha_inicio_contrato, String descripcion) {
        this.id_perfil = id_perfil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fecha_naci = fecha_naci;
        this.fecha_inicio_contrato = fecha_inicio_contrato;
        this.descripcion = descripcion;
    }

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
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
