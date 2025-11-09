package modelos;

import java.util.List;

// clase que representa un triangulo en el plano 

public class Triangulo extends Figura {
    private String tipo; // por ej. acutangulo, rectangulo
    private double lado1, lado2, lado3;

    // crea un triangulo con 3 vertices
    public Triangulo(String nombre, List<Punto> vertices) {
        super(nombre, vertices);

        // condicion para evaluar la cantidad de vertices
        if (vertices.size() != 3) {
            throw new IllegalArgumentException("Un triángulo debe tener 3 vértices");
        }

        calcularLados();
        clasificar();
        calcularArea();
    }

    //Calcula la longitud de los tres lados del triángulo.
    private void calcularLados() {
        lado1 = calcularDistancia(vertices.get(0), vertices.get(1));
        lado2 = calcularDistancia(vertices.get(1), vertices.get(2));
        lado3 = calcularDistancia(vertices.get(2), vertices.get(0));
    }

    //Clasificacion del triángulo según sus características.
    private void clasificar() {
        // Usar el ClasificadorTriangulos para determinar el tipo
        this.tipo = algoritmos.ClasificadorTriangulos.clasificarPorLados(lado1, lado2, lado3);
    }

    // Calcula el área del triángulo usando la fórmula de Herón y retorna el area.
    @Override
    public double calcularArea() {
        // Se usa la formula de heron
        double s = (lado1 + lado2 + lado3) / 2.0;
        this.area = Math.sqrt(s * (s - lado1) * (s - lado2) * (s - lado3));
        return area;
    }

    // Verifica si el triángulo es válido, si tiene 3 vertices no colineales, falso en caso contrario.
    @Override
    public boolean esValida() {
        if (vertices.size() != 3) {
            return false;
        }

        // Verificar que no sean colineales (NO esten ubicados en linea recta ----)
        Punto p1 = vertices.get(0);
        Punto p2 = vertices.get(1);
        Punto p3 = vertices.get(2);

        //Formula del area de gauss (comprobar la NO colinealidad), calculando el doble del area del triangulo
        double area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) -
                (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()));

        return area > 0.01; // No son colineales    }
    }


    //Obtiene la información específica del triángulo (tipo y la longitud de sus lados).
    @Override
    public String getInfoEspecifica() {
        return String.format("Tipo: %s, Lados: %.2f, %.2f, %.2f",
                tipo, lado1, lado2, lado3);
    }

    //Calcula la distancia euclidiana entre dos puntos.
    private double calcularDistancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) +
                Math.pow(p2.getY() - p1.getY(), 2));
    }

    //getter para el tipo de triangulo.
    public String getTipo() { return tipo;}
}
