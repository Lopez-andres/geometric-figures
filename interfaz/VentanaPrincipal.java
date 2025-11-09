package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import algoritmos.DetectorFiguras;
import modelos.ListaPuntos;
import modelos.Punto;
import modelos.Figura;

 /* Ventana principal del sistema de plano cartesiano
 Contiene y coordina todos los paneles de la aplicación */

public class VentanaPrincipal extends JFrame {
    private List<ListaPuntos> listasDeLineas;
    private List<Figura> figurasDetectadas;
    private int listaActual;

    // Paneles
    private PanelPlanoCartesiano panelPlano;
    private PanelControl panelControl;
    private PanelEstadisticas panelEstadisticas;

    // constructor
    public VentanaPrincipal() {
        configurarVentana();
        inicializarDatos();
        inicializarPaneles();
        ensamblarInterfaz();

        setVisible(true);
    }

    // ========== MÉTODOS DE CONFIGURACIÓN ==========

    //Configura las propiedades básicas de la ventana
    private void configurarVentana() {
        setTitle("Proyecto ADA - Plano Cartesiano y Figuras Geométricas");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(true);
    }

    // Inicializa los datos de prueba (las 4 listas mínimas requeridas)
    private void inicializarDatos() {
        listasDeLineas = new ArrayList<>();
        figurasDetectadas = new ArrayList<>();
        listaActual = 0;

        // LISTA 1: 8 puntos - Puede formar rectángulos y triángulos
        ListaPuntos lista1 = new ListaPuntos("Lista 1");
        lista1.agregarPunto(new Punto(0, 0));
        lista1.agregarPunto(new Punto(4, 0));
        lista1.agregarPunto(new Punto(4, 3));
        lista1.agregarPunto(new Punto(0, 3));
        lista1.agregarPunto(new Punto(2, 5));
        lista1.agregarPunto(new Punto(6, 2));
        lista1.agregarPunto(new Punto(7, 5));
        lista1.agregarPunto(new Punto(1, -2));
        listasDeLineas.add(lista1);

        // LISTA 2: 6 puntos - Cuadrado + Triángulos
        ListaPuntos lista2 = new ListaPuntos("Lista 2");
        lista2.agregarPunto(new Punto(-3, -3));
        lista2.agregarPunto(new Punto(-3, 1));
        lista2.agregarPunto(new Punto(1, 1));
        lista2.agregarPunto(new Punto(1, -3));
        lista2.agregarPunto(new Punto(3, 0));
        lista2.agregarPunto(new Punto(-1, 3));
        listasDeLineas.add(lista2);

        // LISTA 3: 7 puntos - Múltiples triángulos
        ListaPuntos lista3 = new ListaPuntos("Lista 3");
        lista3.agregarPunto(new Punto(-5, 0));
        lista3.agregarPunto(new Punto(-2, 4));
        lista3.agregarPunto(new Punto(1, 0));
        lista3.agregarPunto(new Punto(4, 3));
        lista3.agregarPunto(new Punto(3, -2));
        lista3.agregarPunto(new Punto(-3, -3));
        lista3.agregarPunto(new Punto(0, -5));
        listasDeLineas.add(lista3);

        // LISTA 4: 10 puntos - Muchas figuras posibles
        ListaPuntos lista4 = new ListaPuntos("Lista 4");
        lista4.agregarPunto(new Punto(-6, -2));
        lista4.agregarPunto(new Punto(-6, 2));
        lista4.agregarPunto(new Punto(-2, 2));
        lista4.agregarPunto(new Punto(-2, -2));
        lista4.agregarPunto(new Punto(0, 0));
        lista4.agregarPunto(new Punto(3, 0));
        lista4.agregarPunto(new Punto(3, 4));
        lista4.agregarPunto(new Punto(0, 4));
        lista4.agregarPunto(new Punto(5, -3));
        lista4.agregarPunto(new Punto(1, -4));
        listasDeLineas.add(lista4);
    }

    //Inicializa todos los paneles de la interfaz
    private void inicializarPaneles() {
        // Panel del plano cartesiano (centro)
        panelPlano = new PanelPlanoCartesiano(this);

        // Panel de control (parte inferior)
        panelControl = new PanelControl(this);

        // Panel de estadísticas (lateral derecho)
        panelEstadisticas = new PanelEstadisticas();
    }

    //Ensambla todos los paneles en la ventana usando BorderLayout
    private void ensamblarInterfaz() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior - Título
        JPanel panelTitulo = crearPanelTitulo();
        add(panelTitulo, BorderLayout.NORTH);

        // Panel central - Plano cartesiano
        add(panelPlano, BorderLayout.CENTER);

        // Panel inferior - Controles
        add(panelControl, BorderLayout.SOUTH);

        // Panel derecho - Estadísticas
        JScrollPane scrollEstadisticas = new JScrollPane(panelEstadisticas);
        scrollEstadisticas.setPreferredSize(new Dimension(300, 0));
        add(scrollEstadisticas, BorderLayout.EAST);
    }

    //Crea el panel del título con información del proyecto
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setPreferredSize(new Dimension(0, 60));
        panel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("PROYECTO ADA - PLANO CARTESIANO Y FIGURAS GEOMÉTRICAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = new JLabel("Análisis y Diseño de Algoritmos I");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);

        panel.add(panelTextos, BorderLayout.CENTER);

        return panel;
    }

    // ========== MÉTODOS PÚBLICOS (Para comunicación entre paneles) ==========

    // Cambia a la siguiente lista de puntos
    public void cambiarSiguienteLista() {
        listaActual = (listaActual + 1) % listasDeLineas.size();
        actualizarVista();
    }

    // Cambia a la lista anterior de puntos
    public void cambiarAnteriorLista() {
        listaActual = (listaActual - 1 + listasDeLineas.size()) % listasDeLineas.size();
        actualizarVista();
    }

    // Cambia a una lista específica
    public void cambiarLista(int indice) {
        if (indice >= 0 && indice < listasDeLineas.size()) {
            listaActual = indice;
            actualizarVista();
        }
    }

    // Actualiza todas las vistas cuando cambia la lista actual
    public void actualizarVista() {
        // Detectar figuras automáticamente
        List<Punto> puntosActuales = getListaActual().getPuntos();
        figurasDetectadas = DetectorFiguras.detectarTodasLasFiguras(puntosActuales);

        panelPlano.repaint();
        panelEstadisticas.actualizarEstadisticas(figurasDetectadas);
        panelControl.actualizarInfo();
    }

    // Detecta todas las figuras de la lista actual
    // Este metodo será implementado cuando tengas la clase DetectorFiguras
    public void detectarFiguras() {

    }

    //Ordena las figuras por área.
    public void ordenarFigurasPorArea() {
        // TODO: Implementar cuando tengas OrdenadorFiguras
        // figurasDetectadas = OrdenadorFiguras.ordenarPorArea(figurasDetectadas);
        actualizarVista();
    }

    // ========== GETTERS ==========

    public ListaPuntos getListaActual() {
        return listasDeLineas.get(listaActual);
    }
    public List<ListaPuntos> getTodasLasListas() {
        return listasDeLineas;
    }
    public int getIndiceListaActual() {
        return listaActual;
    }
    public int getCantidadListas() {
        return listasDeLineas.size();
    }
    public List<Figura> getFigurasDetectadas() {
        return figurasDetectadas;
    }
    public void setFigurasDetectadas(List<Figura> figuras) {
        this.figurasDetectadas = figuras;
    }
}