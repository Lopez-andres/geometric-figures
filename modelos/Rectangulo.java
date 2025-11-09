package modelos;

import java.util.List;

//Representa un rectángulo en el plano cartesiano.
public class Rectangulo extends Figura{
    protected double base, altura;

    //Crea un rectángulo con 4 vértices.
    public Rectangulo (String nombre, List<Punto> vertices){
        super(nombre, vertices); //llamada al constructor de Figura
        
        //condicional para evaluar la cantidad de vertices
        if (vertices.size() != 4){
            throw new IllegalArgumentException("Un rectangulo solo puede tener 4 vertices");
        }
        
        calcularDimensiones(); //calculo de tamaño del rectangulo
        calcularArea();
    }


    //Calcula la base y la altura del rectángulo.
    private void calcularDimensiones(){
        //determinar la distancia entre 2 vertices
        double d1 = calcularDistancia(vertices.get(0), vertices.get(1));
        double d2 = calcularDistancia(vertices.get(1), vertices.get(2));

        this.base = Math.max(d1, d2); //se asigna el valor mas grande a la base
        this.altura = Math.min(d1, d2); //se asigna el valor mas pequeño a la altura
    }

    // calcula y retorna el area del rectangulo (base x altura)
    @Override
    public double calcularArea(){
        if(!esValida()) return 0;

        //base x altura
        this.area = base * altura;
        return this.area;
    }

    // verifica si el triangulo es valido (exactamente debe de tener 4 vertices)
    @Override
    public boolean esValida() {
        if (vertices.size() != 4) {
            return false;
        }

        // Calcular las 6 distancias entre todos los pares de puntos (4 lados y las 2 diagonales)
        double[] distancias = new double[6];
        int indice = 0;

        //itera 4 veces
        for (int i = 0; i < 4; i++) {
            //itera 3,2,1 vez (es decir, calcula las 6 distancias)
            for (int j = i + 1; j < 4; j++) {
                distancias[indice++] = calcularDistancia(vertices.get(i), vertices.get(j));
            }
        }

        // Ordenar manualmente usando bubble sort (algoritmo propio), de menor a mayor
        ordenarBurbuja(distancias);

        // comprueba que las 2 distancias mas cortas sean iguales [0] y [1]
        // comprueba que las 2 distancias medianas sean iguales [2] y [3]
        boolean ladosValidos = Math.abs(distancias[0] - distancias[1]) < 0.01 &&
                Math.abs(distancias[2] - distancias[3]) < 0.01;

        // comprueba que las dos distancias mas largas sean iguales [4] y [5]
        boolean diagonalesValidas = Math.abs(distancias[4] - distancias[5]) < 0.01;

        // retorna true si las 2 condiciones anteriores son verdaderas
        return ladosValidos && diagonalesValidas;
    }

    //ordena un arreglo de 6 distancias y quedaria -> (ej. [5, 5, 8.6, 8.6, 10, 10])
    private void ordenarBurbuja(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Intercambiar
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Obtiene la información específica del rectángulo. (base y altura)
    @Override
    public String getInfoEspecifica() {
        return String.format("Base: %.2f, Altura: %.2f", base, altura);
    }

    // Calcula la distancia euclidiana entre dos puntos.
    private double calcularDistancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.getX   () - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }
    // getters
    public double getBase() {return base;}
    public double getAltura() {
        return altura;
    }

}
