
package logica;

public class Venta_animal {
     private int id_venta_animal;
    private Ventas venta;
    private Animal animal;
    private float precio;

    public Venta_animal() {
    }
    
    

    public Venta_animal(int id_venta_animal, Ventas venta, Animal animal, float precio) {
        this.id_venta_animal = id_venta_animal;
        this.venta = venta;
        this.animal = animal;
        this.precio = precio;
    }

    public int getId_venta_animal() {
        return id_venta_animal;
    }

    public void setId_venta_animal(int id_venta_animal) {
        this.id_venta_animal = id_venta_animal;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
}
