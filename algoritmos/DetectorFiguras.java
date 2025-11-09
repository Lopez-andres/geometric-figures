package algoritmos;

import modelos.*;
import java.util.ArrayList;
import java.util.List;

// Clase principal para detectar todas las figuras geométricas
// en un conjunto de puntos del plano cartesiano.

public class DetectorFiguras {
    private static final double EPSILON = 0.01; // Tolerancia para comparaciones

    // detecta todas las figuras en una lista de puntos
    public static List<Figura> detectarTodasLasFiguras(List<Punto> puntos) {
        List<Figura> figuras = new ArrayList<>();

        if (puntos == null || puntos.size() < 3) {
            return figuras;
        }

        // Detectar Cuadrados y Rectángulos (requieren 4 puntos)
        if (puntos.size() >= 4) {
            List<List<Punto>> combinaciones4 = generarCombinaciones4Puntos(puntos);

            for (List<Punto> combo : combinaciones4) {
                if (esCuadrado(combo)) {
                    figuras.add(new Cuadrado("Cuadrado", combo));
                } else if (esRectangulo(combo)) {
                    figuras.add(new Rectangulo("Rectángulo", combo));
                }
            }
        }

        // Detectar Triángulos (requieren 3 puntos)
        List<List<Punto>> combinaciones3 = generarCombinaciones3Puntos(puntos);

        for (List<Punto> combo : combinaciones3) {
            if (!sonColineales(combo)) {
                Triangulo triangulo = new Triangulo("Triángulo", combo);
                figuras.add(triangulo);
            }
        }

        return figuras;
    }

    // genera todas las combinaciones posibles de 4 puntos
    public static List<List<Punto>> generarCombinaciones4Puntos(List<Punto> puntos) {
        List<List<Punto>> combinaciones = new ArrayList<>();
        int n = puntos.size();

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        List<Punto> combo = new ArrayList<>();
                        combo.add(puntos.get(i));
                        combo.add(puntos.get(j));
                        combo.add(puntos.get(k));
                        combo.add(puntos.get(l));
                        combinaciones.add(combo);
                    }
                }
            }
        }

        return combinaciones;
    }

    // genera todas las combinaciones posibles de 3 puntos
    public static List<List<Punto>> generarCombinaciones3Puntos(List<Punto> puntos) {
        List<List<Punto>> combinaciones = new ArrayList<>();
        int n = puntos.size();

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    List<Punto> combo = new ArrayList<>();
                    combo.add(puntos.get(i));
                    combo.add(puntos.get(j));
                    combo.add(puntos.get(k));
                    combinaciones.add(combo);
                }
            }
        }

        return combinaciones;
    }

    // Verifica si 4 puntos forman un cuadrado
    public static boolean esCuadrado(List<Punto> puntos) {
        if (puntos.size() != 4) return false;

        // Calcular todas las distancias (6 en total)
        double[] distancias = new double[6];
        int idx = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                distancias[idx++] = calcularDistancia(puntos.get(i), puntos.get(j));
            }
        }

        // Ordenar distancias
        ordenarArray(distancias);

        // En un cuadrado: 4 lados iguales y 2 diagonales iguales
        // Verificar que los 4 lados sean iguales
        boolean ladosIguales =
                Math.abs(distancias[0] - distancias[1]) < EPSILON &&
                        Math.abs(distancias[1] - distancias[2]) < EPSILON &&
                        Math.abs(distancias[2] - distancias[3]) < EPSILON;

        // Verificar que las 2 diagonales sean iguales
        boolean diagonalesIguales =
                Math.abs(distancias[4] - distancias[5]) < EPSILON;

        // Verificar que diagonal = lado × √2
        double relacionDiagonal = distancias[4] / distancias[0];
        boolean relacionCorrecta =
                Math.abs(relacionDiagonal - Math.sqrt(2)) < EPSILON;

        return ladosIguales && diagonalesIguales && relacionCorrecta;
    }

    // Verifica si 4 puntos forman un rectangulo
    public static boolean esRectangulo(List<Punto> puntos) {
        if (puntos.size() != 4) return false;

        // Calcular todas las distancias
        double[] distancias = new double[6];
        int idx = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                distancias[idx++] = calcularDistancia(puntos.get(i), puntos.get(j));
            }
        }

        // Ordenar distancias
        ordenarArray(distancias);

        // En un rectángulo: 2 pares de lados iguales y 2 diagonales iguales
        boolean dosParesDeLados =
                Math.abs(distancias[0] - distancias[1]) < EPSILON &&
                        Math.abs(distancias[2] - distancias[3]) < EPSILON;

        boolean diagonalesIguales =
                Math.abs(distancias[4] - distancias[5]) < EPSILON;

        return dosParesDeLados && diagonalesIguales;
    }

    // Detecta solo los cuadrados de una lista de puntos
    public static List<Cuadrado> detectarCuadrados(List<Punto> puntos) {
        List<Cuadrado> cuadrados = new ArrayList<>();

        if (puntos.size() < 4) return cuadrados;

        List<List<Punto>> combinaciones = generarCombinaciones4Puntos(puntos);

        for (List<Punto> combo : combinaciones) {
            if (esCuadrado(combo)) {
                cuadrados.add(new Cuadrado("Cuadrado", combo));
            }
        }

        return cuadrados;
    }

    // Detecta solo los rectángulos de una lista de puntos
    public static List<Rectangulo> detectarRectangulos(List<Punto> puntos) {
        List<Rectangulo> rectangulos = new ArrayList<>();

        if (puntos.size() < 4) return rectangulos;

        List<List<Punto>> combinaciones = generarCombinaciones4Puntos(puntos);

        for (List<Punto> combo : combinaciones) {
            if (esRectangulo(combo) && !esCuadrado(combo)) {
                rectangulos.add(new Rectangulo("Rectángulo", combo));
            }
        }

        return rectangulos;
    }

    // Detecta solo los triángulos acutángulos
    public static List<Triangulo> detectarTriangulosAcutangulos(List<Punto> puntos) {
        List<Triangulo> triangulos = new ArrayList<>();

        if (puntos.size() < 3) return triangulos;

        List<List<Punto>> combinaciones = generarCombinaciones3Puntos(puntos);

        for (List<Punto> combo : combinaciones) {
            if (!sonColineales(combo)) {
                Triangulo t = new Triangulo("Triángulo", combo);
                if (t.getTipo().equals("Acutángulo")) {
                    triangulos.add(t);
                }
            }
        }

        return triangulos;
    }

    // Detecta solo los triángulos rectángulos
    public static List<Triangulo> detectarTriangulosRectangulos(List<Punto> puntos) {
        List<Triangulo> triangulos = new ArrayList<>();

        if (puntos.size() < 3) return triangulos;

        List<List<Punto>> combinaciones = generarCombinaciones3Puntos(puntos);

        for (List<Punto> combo : combinaciones) {
            if (!sonColineales(combo)) {
                Triangulo t = new Triangulo("Triángulo", combo);
                if (t.getTipo().equals("Rectángulo")) {
                    triangulos.add(t);
                }
            }
        }

        return triangulos;
    }

    // Cuenta cuántas figuras hay de cada tipo
    public static int[] contarFigurasPorTipo(List<Figura> figuras) {
        int[] contadores = new int[4]; // [cuadrados, rectangulos, triRect, triAcut]

        for (Figura f : figuras) {
            if (f instanceof Cuadrado) {
                contadores[0]++;
            } else if (f instanceof Rectangulo) {
                contadores[1]++;
            } else if (f instanceof Triangulo) {
                Triangulo t = (Triangulo) f;
                if (t.getTipo().equals("Rectángulo")) {
                    contadores[2]++;
                } else if (t.getTipo().equals("Acutángulo")) {
                    contadores[3]++;
                }
            }
        }

        return contadores;
    }

    // ========== MÉTODOS AUXILIARES ==========

    // Verifica si 3 puntos son colineales (están en línea recta)
    private static boolean sonColineales(List<Punto> puntos) {
        if (puntos.size() != 3) return false;

        Punto p1 = puntos.get(0);
        Punto p2 = puntos.get(1);
        Punto p3 = puntos.get(2);

        // Fórmula del área usando determinante
        double area = Math.abs(
                (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) -
                        (p3.getX() - p1.getX()) * (p2.getY() - p1.getY())
        );

        return area < EPSILON;
    }

    // Calcula la distancia euclidiana entre dos puntos
    private static double calcularDistancia(Punto p1, Punto p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Ordena un array de doubles (Bubble Sort simple para arrays pequeños)
    private static void ordenarArray(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}