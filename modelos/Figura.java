package modelos;

import java.util.ArrayList;
import java.util.List;

//Clase base abstracta para todas las figuras geométricas.
public abstract class Figura {
    protected String nombre, identificador;
    protected List<Punto> vertices;
    protected double area;
    private static int contador = 0;

    //Crea una figura con un nombre y vértices especificados.
    public Figura(String nombre, List<Punto> vertices) {
        this.nombre = nombre;

        // se crea una copia para que cada instancia de figura tenga su propia lista
        // si no se hace esto otras clases como Puntos, pueden eliminar la lista original
        this.vertices = new ArrayList<>(vertices);
        this.area = 0.0;
        this.identificador = generarIdentificador();
    }

    // METODOS ABSTRACTOS
    public abstract double calcularArea();
    public abstract boolean esValida(); // verifica si la forma de la figura es correcta
    public abstract String getInfoEspecifica(); // informacion especifica de la lectura

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Punto> getVertices() { return new ArrayList<>(vertices); }
    public double getArea() { return area; }
    protected void setArea(double area) { this.area = area; }
    public String getIdentificador() { return identificador; }
    public int getNumeroVertices() {
        return vertices.size();
    }
    private String generarIdentificador() {
        return nombre + "_" + (++contador);
    }

    // Obtiene la información básica de la figura (nombre, ID, vértices y área).
    public String getInfoBasica() {
        StringBuilder sb = new StringBuilder();
        sb.append("Figura: ").append(nombre).append("\n");
        sb.append("ID: ").append(identificador).append("\n");
        sb.append("Vertices: ");
        for (Punto p : vertices) {
            sb.append(p.toString()).append(" ");
        }
        sb.append("\nArea: ").append(String.format("%.2f", area));
        return sb.toString();
    }

    //Devuelve una representación en texto de la figura.
    @Override
    public String toString() { return getInfoBasica() + "\n" + getInfoEspecifica(); }
}
