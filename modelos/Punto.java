package modelos;

//Representa un punto en el plano cartesiano con coordenadas (x, y).
public class Punto {
    private double x, y;

    //Crea un punto con las coordenadas especificadas.
    public Punto(double x, double y){
        this.x = x;
        this.y = y;
    }

    // getters y setters
    public double getX(){ return x;}
    private void setX(double x){ this.x = x; }

    public double getY(){ return y; }
    private void setY(double Y){ this.y = y; }

    //Devuelve una representación en texto del punto.
    @Override
    public String toString(){ return "x = " + x + " | y = " + y; }

    //Calcula la distancia euclidiana desde este punto a otro punto.
    public double distanciaA(Punto otro) {
        return Math.sqrt(Math.pow(otro.x - this.x, 2) + Math.pow(otro.y - this.y, 2));
    }
}
