package logica;

import java.util.Date;

public class Peso_animal {
    
    private int id_peso;
    private float peso;
    private Date fecha_cambio;

    public Peso_animal() {
    }

    public Peso_animal(int id_peso, float peso, Date fecha_cambio) {
        this.id_peso = id_peso;
        this.peso = peso;
        this.fecha_cambio = fecha_cambio;
    }

    public int getId_peso() {
        return id_peso;
    }

    public void setId_peso(int id_peso) {
        this.id_peso = id_peso;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Date getFecha_cambio() {
        return fecha_cambio;
    }

    public void setFecha_cambio(Date fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }
    
     // Método para calcular la ganancia de peso
//    public float calcularGanancia() {
//        if (pesos.size() < 2) {
//            return 0;
//        }
//        PesoAnimal ultimoPeso = pesos.get(pesos.size() - 1);
//        PesoAnimal penultimoPeso = pesos.get(pesos.size() - 2);
//        return ultimoPeso.getPeso() - penultimoPeso.getPeso();
//    }
}


//esto seria una nueva clase para determinar la ganancia de peso, mas como controlador
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        // Crear un animal
//        Animal animal = new Animal(1, 001, "Raza1", "Macho");
//
//        // Crear y agregar algunos registros de peso
//        PesoAnimal peso1 = new PesoAnimal(1, 120.0f, new Date());
//        PesoAnimal peso2 = new PesoAnimal(2, 125.0f, new Date());
//        animal.agregarPeso(peso1);
//        animal.agregarPeso(peso2);
//
//        // Imprimir detalles del animal y sus pesos
//        System.out.println("Animal ID: " + animal.getId_animal());
//        System.out.println("Número de animal: " + animal.getNum_animal());
//        System.out.println("Raza: " + animal.getRaza());
//        System.out.println("Tipo de sexo: " + animal.getTipo_sexo());
//
//        System.out.println("Registros de peso:");
//        for (PesoAnimal peso : animal.getPesos()) {
//            System.out.println("Peso: " + peso.getPeso() + " kg, Fecha: " + peso.getFecha_cambio());
//        }
//
//        // Calcular la ganancia de peso
//        float ganancia = animal.calcularGanancia();
//        System.out.println("Ganancia de peso: " + ganancia + " kg");
//
//        // Agregar un nuevo registro de peso
//        PesoAnimal peso3 = new PesoAnimal(3, 130.0f, new Date());
//        animal.agregarPeso(peso3);
//
//        System.out.println("Nuevo registro de peso agregado:");
//        for (PesoAnimal peso : animal.getPesos()) {
//            System.out.println("Peso: " + peso.getPeso() + " kg, Fecha: " + peso.getFecha_cambio());
//        }
//
//        // Calcular la nueva ganancia de peso
//        ganancia = animal.calcularGanancia();
//        System.out.println("Nueva ganancia de peso: " + ganancia + " kg");
//    }
//}
