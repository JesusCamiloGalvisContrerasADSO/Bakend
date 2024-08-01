
package logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id_usuario;
    private String rol;
    private String contrasenia;
    private String Nombre_user;

    
    
    public Usuario() {
    }
    
    
    public Usuario(int id_usuario, String rol, String contrasenia, String Nombre_user) {
        this.id_usuario = id_usuario;
        this.rol = rol;
        this.contrasenia = contrasenia;
        this.Nombre_user = Nombre_user;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre_user() {
        return Nombre_user;
    }

    public void setNombre_user(String Nombre_user) {
        this.Nombre_user = Nombre_user;
    }
    
    
    
            
}
