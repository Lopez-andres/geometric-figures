package modelos;

import java.util.ArrayList;
import java.util.List;

//Agrupa un conjunto de puntos en el plano cartesiano.
public class ListaPuntos {
    private String identificador, descripcion;
    private List<Punto> puntos;

    //Crea una lista de puntos vacía.
    public ListaPuntos(){
        this.identificador="";
        this.puntos=new ArrayList<>();
        this.descripcion="";
    }

    //Crea una lista de puntos con un identificador.
    public ListaPuntos(String identificador){
        this.identificador = identificador;
        this.puntos = new ArrayList<>();
        this.descripcion="";
    }

    //Crea una lista de puntos con identificador y descripción.
    public ListaPuntos(String identificador, String descripcion){
        this.identificador = identificador;
        this.puntos = new ArrayList<>();
        this.descripcion = descripcion;
    }

     //Crea una lista de puntos con identificador y puntos iniciales.
    public ListaPuntos(String identificador, List<Punto> puntos){
        this.identificador = identificador;
        this.puntos = new ArrayList<>(puntos); //copia protegida de la lista original
        this.descripcion="";
    }

    // ------- GESTION DE PUNTOS -------

    //Agrega un punto a la lista.
    public boolean agregarPunto(Punto punto){
        if(punto == null){ return false; }
        return puntos.add(punto);
    }

    //Agrega un punto a la lista usando coordenadas.
    public boolean agregarPunto(double x, double y){
        Punto nuevoPunto = new Punto(x, y);
        return puntos.add(nuevoPunto);
    }

    //Elimina un punto en la posición especificada.
    public Punto eliminarPunto(int indice){
        if(indice < 0 || indice >= puntos.size()) return null;
        return puntos.remove(indice);
    }

    //Elimina un punto específico de la lista.
    public boolean eliminarPunto(Punto punto){ return puntos.remove(punto);}

    //Obtiene un punto en una posición específica.
    public Punto obtenerPunto(int indice){
        if(indice < 0 || indice >= puntos.size()) return null;
        return puntos.get(indice);
    }

    //Obtiene una copia de todos los puntos de la lista.
    public List<Punto> obtenerTodosPuntos(){ return new ArrayList<>(puntos);}

    //Elimina todos los puntos de la lista.
    public void limpiar(){ puntos.clear();}

    // ------- CONSULTAS -------

    //Obtiene la cantidad de puntos en la lista.
    public int cantidadPuntos(){ return puntos.size();}

    //Verifica si la lista está vacía.
    public boolean estaVacia(){ return puntos.isEmpty();}

    //Verifica si la lista tiene una cantidad mínima de puntos.
    public boolean tieneMinimoPuntos(int minimo){ return puntos.size() >= minimo;}

    //Verifica si la lista contiene un punto específico.
    public boolean contienePunto(Punto punto){return puntos.contains(punto);}

    //Verifica si existe un punto con coordenadas específicas.
    public boolean contienePunto(double x, double y){
        for (Punto p : puntos){
            if(p.getX() == x && p.getY() == y){
                return true;
            }
        }
        return false;
    }

    // ========== GETTERS Y SETTERS ==========

    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // ========== MÉTODOS ÚTILES ==========

    //Obtiene los límites de los puntos para graficar [minX, maxX, minY, maxY].
    public double[] obtenerLimites() {
        if (puntos.isEmpty()) {
            return new double[]{0, 0, 0, 0}; //si la lista de puntos esta vacia retorna 0
        }

        // toma el primer punto y asume que tiene todos los casos (min en x,y | max en x,y)
        double minX = puntos.get(0).getX();
        double maxX = puntos.get(0).getX();
        double minY = puntos.get(0).getY();
        double maxY = puntos.get(0).getY();

        // compara los min, max con todos los puntos de la lista, para actualizar valores
        for (Punto p : puntos) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getY() > maxY) maxY = p.getY();
        }
        return new double[]{minX, maxX, minY, maxY}; //contiene los valores mas extremos de la lista
    }

    //Crea una copia de la lista de puntos.
    public ListaPuntos clonar() {
        ListaPuntos copia = new ListaPuntos(this.identificador, this.descripcion);
        for (Punto p : this.puntos) {
            copia.agregarPunto(new Punto(p.getX(), p.getY()));
        }
        return copia;
    }

    // ========== REPRESENTACION ESCRITA DE PUNTOS ==========

    //Devuelve una representación en texto de la lista de puntos.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListaPuntos{");
        sb.append("identificador='").append(identificador).append('\'');
        sb.append(", cantidad=").append(puntos.size());
        sb.append(", puntos=[");

        for (int i = 0; i < puntos.size(); i++) {
            sb.append(puntos.get(i));
            if (i < puntos.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");
        return sb.toString();
    }

    //Devuelve una representación detallada de la lista de puntos.
    public String toStringDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(identificador).append(" ===\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Cantidad de puntos: ").append(puntos.size()).append("\n");
        sb.append("Puntos:\n");

        for (int i = 0; i < puntos.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(puntos.get(i)).append("\n");
        }
        return sb.toString();
    }

    public List<Punto> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<Punto> puntos) {
        this.puntos = puntos;
    }
}
