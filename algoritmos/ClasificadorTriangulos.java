package algoritmos;

import modelos.Punto;

import java.util.List;

/* Clase que clasifica triángulos según sus ángulos

 ALGORITMO PRINCIPAL:
 Usa el Teorema de Pitágoras generalizado para clasificar:
 - Si c² = a² + b² → Triángulo Rectángulo (la hipotenusa al cuadrado es igual a la suma de los catetos al cuadrado)
 - Si c² < a² + b² → Triángulo Acutángulo

 Donde c es el la hipotenusa, y a, b son catetos (opuesto y adyacente)
 */
public class ClasificadorTriangulos {
    private static final double EPSILON = 0.01; // Tolerancia para comparaciones de punto flotante

    // clasifica triangulos segun sus lados
    public static String clasificarPorLados(double lado1, double lado2, double lado3) {
        // Ordenar los lados de menor a mayor
        double[] lados = {lado1, lado2, lado3};
        ordenarTresNumeros(lados);

        double a = lados[0]; // Lado menor
        double b = lados[1]; // Lado medio
        double c = lados[2]; // Lado mayor

        // Calcular cuadrados
        double sumaCuadradosMenores = a * a + b * b;
        double cuadradoMayor = c * c;

        // Clasificar según teorema de Pitágoras
        double diferencia = Math.abs(cuadradoMayor - sumaCuadradosMenores);

        if (diferencia < EPSILON) {
            return "Rectángulo";
        } else {
            return "Acutángulo";
        }
    }

    // Clasifica un triángulo según sus tres vértices
    public static String clasificarPorVertices(List<Punto> vertices) {
        if (vertices.size() != 3) {
            throw new IllegalArgumentException("Se requieren exactamente 3 vértices");
        }

        // Calcular las longitudes de los lados
        double lado1 = calcularDistancia(vertices.get(0), vertices.get(1));
        double lado2 = calcularDistancia(vertices.get(1), vertices.get(2));
        double lado3 = calcularDistancia(vertices.get(2), vertices.get(0));

        return clasificarPorLados(lado1, lado2, lado3);
    }

    // Verifica si un triángulo es rectángulo
    public static boolean esRectangulo(double lado1, double lado2, double lado3) {
        return clasificarPorLados(lado1, lado2, lado3).equals("Rectángulo");
    }

    // Verifica si un triángulo es rectángulo (mediante sus vertices)
    public static boolean esRectangulo(List<Punto> vertices) {
        return clasificarPorVertices(vertices).equals("Rectángulo");
    }

    // erifica si un triángulo es acutángulo
    public static boolean esAcutangulo(double lado1, double lado2, double lado3) {
        return clasificarPorLados(lado1, lado2, lado3).equals("Acutángulo");
    }

    // Verifica si un triángulo es acutángulo (mediante sus vértices)
    public static boolean esAcutangulo(List<Punto> vertices) {
        return clasificarPorVertices(vertices).equals("Acutángulo");
    }

    //Calcula los tres ángulos internos del triángulo en grados
    //Usa la ley de cosenos: cos(C) = (a² + b² - c²) / (2ab)
    public static double[] calcularAngulos(double lado1, double lado2, double lado3) {
        double[] angulos = new double[3];

        // Ángulo opuesto al lado1 (usando ley de cosenos)
        double cosAngulo1 = (lado2 * lado2 + lado3 * lado3 - lado1 * lado1) / (2 * lado2 * lado3);
        angulos[0] = Math.toDegrees(Math.acos(cosAngulo1));

        // Ángulo opuesto al lado2
        double cosAngulo2 = (lado1 * lado1 + lado3 * lado3 - lado2 * lado2) / (2 * lado1 * lado3);
        angulos[1] = Math.toDegrees(Math.acos(cosAngulo2));

        // Ángulo opuesto al lado3
        double cosAngulo3 = (lado1 * lado1 + lado2 * lado2 - lado3 * lado3) / (2 * lado1 * lado2);
        angulos[2] = Math.toDegrees(Math.acos(cosAngulo3));

        return angulos;
    }

    //Calcula los ángulos del triángulo dado sus vértices
    public static double[] calcularAngulos(List<Punto> vertices) {
        if (vertices.size() != 3) {
            throw new IllegalArgumentException("Se requieren exactamente 3 vértices");
        }

        double lado1 = calcularDistancia(vertices.get(0), vertices.get(1));
        double lado2 = calcularDistancia(vertices.get(1), vertices.get(2));
        double lado3 = calcularDistancia(vertices.get(2), vertices.get(0));

        return calcularAngulos(lado1, lado2, lado3);
    }

    // ========== MÉTODOS AUXILIARES ==========

    // Ordena tres números de menor a mayor
    // Usa ordenamiento por comparación directa
    private static void ordenarTresNumeros(double[] numeros) {
        // Ordenamiento simple para 3 elementos
        if (numeros[0] > numeros[1]) {
            swap(numeros, 0, 1);
        }
        if (numeros[1] > numeros[2]) {
            swap(numeros, 1, 2);
        }
        if (numeros[0] > numeros[1]) {
            swap(numeros, 0, 1);
        }
    }

    // Intercambia dos elementos en un array
    private static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Calcula la distancia euclidiana entre dos puntos
    private static double calcularDistancia(Punto p1, Punto p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}