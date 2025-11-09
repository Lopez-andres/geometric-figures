package modelos;

import java.util.List;

//Representa un cuadrado en el plano cartesiano.
public class Cuadrado extends Rectangulo{
    private double lado;

    //Crea un cuadrado con 4 vértices.
    public Cuadrado(String nombre, List<Punto> vertices){
        super(nombre, vertices);

        //validacion de cuadrado, luego de haber validado que es un rectangulo
        if (esValida() && esCuadradoValido()) {
            this.lado = base;
            this.nombre = "Cuadrado";
            this.area = calcularArea();
        }
    }

    //Verifica si la figura es un cuadrado válido (todos los lados iguales).
    private boolean esCuadradoValido(){
        if(!super.esValida()) return false; //verifica que sea un rectangulo

        //todos los lados deben ser iguales (la base debe ser igual a la altura)
        return Math.abs(base - altura) < 0.01;
    }

    //Verifica si el cuadrado es válido (rectángulo con todos los lados iguales).
    @Override
    public boolean esValida(){
        //un cuadrado debe tener todos los lados iguales y ser un rectangulo
        return super.esValida() && esCuadradoValido();
    }

    //Calcula y retorna el área del cuadrado (lado x lado).
    @Override
    public double calcularArea(){
        if (!esValida()) return 0;
        area = lado * lado;
        return area;
    }

    // GETTER
    public double getLado(){
        return lado;
    }

    //Devuelve una representación en texto del cuadrado.
    @Override
    public String toString(){
        return "Cuadrado - lado: " + String.format("%2.f", lado) +
                ", Area: " + String.format("%.2f", area);
    }
}
