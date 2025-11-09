package interfaz;

import javax.swing.*;
import java.awt.*;
// import java.awt.geom.Point2D; // (opcional) Este import no se usa, puedes eliminarlo
import modelos.*;

import java.util.List;

// Panel que dibuja el plano cartesiano con puntos y figuras
// Cumple con los puntos 4 y 5 del proyecto

public class PanelPlanoCartesiano extends JPanel {
    private VentanaPrincipal ventana;
    private final int ESCALA = 35; // Píxeles por unidad

    // Configuración visual
    private boolean mostrarCuadricula = true;
    private boolean mostrarEjes = true;
    private boolean mostrarPuntos = true;
    private boolean mostrarFiguras = true;

    // constructor
    public PanelPlanoCartesiano(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 700));
    }

    // ========== METODO PRINCIPAL DE DIBUJO ==========

    //Metodo que dibuja todo el contenido del panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activar antialiasing para mejor calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        int centroX = ancho / 2;
        int centroY = alto / 2;

        // Orden de dibujo
        if (mostrarCuadricula) {
            dibujarCuadricula(g2d, centroX, centroY, ancho, alto);
        }

        if (mostrarEjes) {
            dibujarEjes(g2d, centroX, centroY, ancho, alto);
        }

        // Obtener lista actual
        ListaPuntos listaActual = ventana.getListaActual();

        if (listaActual != null) {
            // Dibujar figuras primero (debajo de los puntos)
            if (mostrarFiguras) {
                dibujarFiguras(g2d, centroX, centroY);
            }

            // Dibujar puntos encima
            if (mostrarPuntos) {
                dibujarPuntos(g2d, listaActual, centroX, centroY);
            }
        }

        // Dibujar leyenda
        dibujarLeyenda(g2d);
    }

    // ========== MÉTODOS DE DIBUJO ==========

    // Dibuja la cuadrícula del plano cartesiano.
    private void dibujarCuadricula(Graphics2D g2d, int cx, int cy, int w, int h) {
        g2d.setColor(new Color(240, 240, 240));
        g2d.setStroke(new BasicStroke(1));

        // Líneas verticales
        for (int x = cx % ESCALA; x < w; x += ESCALA) {
            g2d.drawLine(x, 0, x, h);
        }

        // Líneas horizontales
        for (int y = cy % ESCALA; y < h; y += ESCALA) {
            g2d.drawLine(0, y, w, y);
        }
    }

    // Dibuja los ejes (X,Y) con sus etiquetas
    private void dibujarEjes(Graphics2D g2d, int cx, int cy, int w, int h) {
        g2d.setColor(new Color(52, 73, 94));
        g2d.setStroke(new BasicStroke(2));

        // Eje X
        g2d.drawLine(0, cy, w, cy);
        // Eje Y
        g2d.drawLine(cx, 0, cx, h);

        // Flechas de los ejes
        dibujarFlechaEjeX(g2d, w, cy);
        dibujarFlechaEjeY(g2d, cx, h);

        // Etiquetas
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.setColor(new Color(52, 73, 94));

        // Etiquetas eje X
        for (int i = -20; i <= 20; i++) {
            if (i != 0) {
                int x = cx + i * ESCALA;
                if (x > 0 && x < w) {
                    g2d.drawLine(x, cy - 4, x, cy + 4);
                    g2d.drawString(String.valueOf(i), x - 5, cy + 18);
                }
            }
        }

        // Etiquetas eje Y
        for (int i = -20; i <= 20; i++) {
            if (i != 0) {
                int y = cy - i * ESCALA;
                if (y > 0 && y < h) {
                    g2d.drawLine(cx - 4, y, cx + 4, y);
                    g2d.drawString(String.valueOf(i), cx + 8, y + 4);
                }
            }
        }

        // Origen
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("O", cx + 8, cy + 15);

        // Etiquetas de ejes
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("X", w - 25, cy - 10);
        g2d.drawString("Y", cx + 10, 20);
    }

    // Dibuja flecha en el eje X
    private void dibujarFlechaEjeX(Graphics2D g2d, int x, int y) {
        int[] xPoints = {x - 15, x - 15, x - 5};
        int[] yPoints = {y - 5, y + 5, y};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }


    // Dibuja flecha en el eje Y
    private void dibujarFlechaEjeY(Graphics2D g2d, int x, int y) {
        int[] xPoints = {x - 5, x + 5, x};
        int[] yPoints = {y + 15, y + 15, y + 5};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }


    // Dibuja todos los puntos de la lista actual
    private void dibujarPuntos(Graphics2D g2d, ListaPuntos lista, int cx, int cy) {
        List<Punto> puntos = lista.obtenerTodosPuntos();

        for (int i = 0; i < puntos.size(); i++) {
            Punto p = puntos.get(i);
            int x = cx + (int)(p.getX() * ESCALA);
            int y = cy - (int)(p.getY() * ESCALA);

            // Punto rojo grande
            g2d.setColor(new Color(231, 76, 60));
            g2d.fillOval(x - 7, y - 7, 14, 14);

            // Borde blanco para contraste
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x - 7, y - 7, 14, 14);

            // Etiqueta del punto
            g2d.setColor(new Color(44, 62, 80));
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            String label = "P" + (i + 1) + "(" + p.getX() + "," + p.getY() + ")";
            g2d.drawString(label, x + 12, y - 10);
        }
    }

    // Dibuja todas las figuras detectadas
    private void dibujarFiguras(Graphics2D g2d, int cx, int cy) {
        List<Figura> figuras = ventana.getFigurasDetectadas();

        if (figuras == null || figuras.isEmpty()) {
            return;
        }

        // Filtrar solo las primeras de cada tipo para evitar saturación
        boolean dibujoCuadrado = false;
        int triangulosDibujados = 0;
        int maxTriangulos = 3; // Limitar a 3 triángulos máximo

        for (Figura figura : figuras) {
            // Dibujar solo el primer cuadrado encontrado
            if (figura instanceof Cuadrado && !dibujoCuadrado) {
                dibujarFigura(g2d, figura, cx, cy);
                dibujoCuadrado = true;
            }
            // Dibujar máximo 3 triángulos
            else if (figura instanceof Triangulo && triangulosDibujados < maxTriangulos) {
                dibujarFigura(g2d, figura, cx, cy);
                triangulosDibujados++;
            }
        }
    }


    // Dibuja una figura individual (aristas entre sus puntos)
    private void dibujarFigura(Graphics2D g2d, Figura figura, int cx, int cy) {
        List<Punto> vertices = figura.getVertices();

        if (vertices == null || vertices.size() < 2) return;

        // Color y estilo
        g2d.setColor(obtenerColorFigura(figura));
        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < vertices.size(); i++) {
            Punto a = vertices.get(i);
            Punto b = vertices.get((i + 1) % vertices.size());

            int x1 = cx + (int)(a.getX() * ESCALA);
            int y1 = cy - (int)(a.getY() * ESCALA);
            int x2 = cx + (int)(b.getX() * ESCALA);
            int y2 = cy - (int)(b.getY() * ESCALA);

            g2d.drawLine(x1, y1, x2, y2);
        }
    }


    // Obtiene el color según el tipo de figura
    private Color obtenerColorFigura(Figura figura) {
        String nombre = figura.getNombre().toLowerCase();

        // Reordenado para que el caso combinado no sea inalcanzable
        if (nombre.contains("cuadrado")) {
            return new Color(52, 152, 219); // Azul
        } else if ((nombre.contains("rectángulo") || nombre.contains("rectangulo")) && (nombre.contains("triángulo") || nombre.contains("triangulo"))) {
            return new Color(230, 126, 34); // Naranja
        } else if (nombre.contains("rectángulo") || nombre.contains("rectangulo")) {
            return new Color(46, 204, 113); // Verde
        } else if (nombre.contains("triángulo") || nombre.contains("triangulo")) {
            return new Color(241, 196, 15); // Amarillo
        }

        return new Color(149, 165, 166); // Gris por defecto
    }

    // Dibuja una leyenda con información.
    private void dibujarLeyenda(Graphics2D g2d) {
        ListaPuntos lista = ventana.getListaActual();

        if (lista == null) return;

        // Fondo de la leyenda
        g2d.setColor(new Color(255, 255, 255, 230));
        g2d.fillRoundRect(10, 10, 250, 80, 10, 10);

        g2d.setColor(new Color(52, 73, 94));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(10, 10, 250, 80, 10, 10);

        // Texto de la leyenda
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(lista.getIdentificador(), 20, 30); // CORRECCIÓN: antes getNombre()

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Puntos: " + lista.cantidadPuntos(), 20, 50); // CORRECCIÓN: antes getCantidadPuntos()

        List<Figura> figuras = ventana.getFigurasDetectadas();
        int cantFiguras = (figuras != null) ? figuras.size() : 0;
        g2d.drawString("Figuras detectadas: " + cantFiguras, 20, 70);
    }



    // ========== MÉTODOS PÚBLICOS (Configuración) ==========

    public void setMostrarCuadricula(boolean mostrar) {
        this.mostrarCuadricula = mostrar;
        repaint();
    }

    public void setMostrarEjes(boolean mostrar) {
        this.mostrarEjes = mostrar;
        repaint();
    }

    public void setMostrarPuntos(boolean mostrar) {
        this.mostrarPuntos = mostrar;
        repaint();
    }

    public void setMostrarFiguras(boolean mostrar) {
        this.mostrarFiguras = mostrar;
        repaint();
    }
}