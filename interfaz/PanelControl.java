package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Panel de control con botones para navegar entre listas y controlar la visualización
public class PanelControl extends JPanel {
    private VentanaPrincipal ventana;

    // Componentes
    private JLabel lblInfo;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JButton btnDetectar;
    private JButton btnOrdenar;
    private JComboBox<String> comboListas;

    // Checkboxes para opciones de visualización
    private JCheckBox chkMostrarCuadricula;
    private JCheckBox chkMostrarPuntos;
    private JCheckBox chkMostrarFiguras;

    // Checkboxes para opciones de figuras
    private JCheckBox chkMostrarCuadrados;
    private JCheckBox chkMostrarRectangulos;
    private JCheckBox chkMostrarTriangulos;

    // Constructor que inicializa el panel de control
    public PanelControl(VentanaPrincipal ventana) {
        this.ventana = ventana;

        configurarPanel();
        inicializarComponentes();
        configurarLayout();
        agregarEventos();
    }

    // ========== MÉTODOS DE CONFIGURACIÓN ==========

    // Configura las propiedades del panel
    private void configurarPanel() {
        setBackground(new Color(236, 240, 241));
        setPreferredSize(new Dimension(0, 120));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Inicializa todos los componentes
    private void inicializarComponentes() {
        // Label de información
        lblInfo = new JLabel("Lista 1 de " + ventana.getCantidadListas());
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        // Botones de navegación
        btnAnterior = crearBoton("⬅ Anterior", new Color(52, 152, 219));
        btnSiguiente = crearBoton("Siguiente ➡", new Color(52, 152, 219));

        // Botones de acción
        btnDetectar = crearBoton("🔍 Detectar Figuras", new Color(46, 204, 113));
        btnOrdenar = crearBoton("📊 Ordenar por Área", new Color(155, 89, 182));

        // ComboBox para selección directa
        comboListas = new JComboBox<>();
        for (int i = 0; i < ventana.getCantidadListas(); i++) {
            comboListas.addItem("Lista " + (i + 1));
        }
        comboListas.setFont(new Font("Arial", Font.PLAIN, 14));
        comboListas.setPreferredSize(new Dimension(150, 35));

        // Checkboxes
        chkMostrarCuadricula = new JCheckBox("Mostrar Cuadrícula", true);
        chkMostrarPuntos = new JCheckBox("Mostrar Puntos", true);
        chkMostrarFiguras = new JCheckBox("Mostrar Figuras", true);

        chkMostrarCuadrados = new JCheckBox("Cuadrados", true);
        chkMostrarRectangulos = new JCheckBox("Rectángulos", false);
        chkMostrarTriangulos = new JCheckBox("Triángulos", false);

        estilizarCheckbox(chkMostrarCuadricula);
        estilizarCheckbox(chkMostrarPuntos);
        estilizarCheckbox(chkMostrarFiguras);
        estilizarCheckbox(chkMostrarCuadrados);
        estilizarCheckbox(chkMostrarRectangulos);
        estilizarCheckbox(chkMostrarTriangulos);
    }

    // Crea un botón estilizado
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(180, 40));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });

        return boton;
    }

    // Estiliza un checkbox
    private void estilizarCheckbox(JCheckBox checkbox) {
        checkbox.setFont(new Font("Arial", Font.PLAIN, 12));
        checkbox.setBackground(new Color(236, 240, 241));
        checkbox.setFocusPainted(false);
    }

    // Configura el layout del panel
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior - Información y navegación
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelSuperior.setOpaque(false);
        panelSuperior.add(lblInfo);
        panelSuperior.add(new JLabel(" | "));
        panelSuperior.add(new JLabel("Seleccionar:"));
        panelSuperior.add(comboListas);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central - Botones principales
        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelCentral.setOpaque(false);
        panelCentral.add(btnAnterior);
        panelCentral.add(btnDetectar);
        panelCentral.add(btnOrdenar);
        panelCentral.add(btnSiguiente);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior - Opciones de visualización
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelInferior.setOpaque(false);
        panelInferior.add(chkMostrarCuadricula);
        panelInferior.add(chkMostrarPuntos);
        panelInferior.add(chkMostrarFiguras);

        add(panelInferior, BorderLayout.SOUTH);
    }

    // Agrega los eventos a los componentes
    private void agregarEventos() {
        // Botón Anterior
        btnAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.cambiarAnteriorLista();
                actualizarInfo();
            }
        });

        // Botón Siguiente
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.cambiarSiguienteLista();
                actualizarInfo();
            }
        });

        // Botón Detectar Figuras
        btnDetectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.detectarFiguras();
                JOptionPane.showMessageDialog(ventana,
                        "Figuras detectadas correctamente!\n" +
                                "(Esta funcionalidad estará completa cuando implementes DetectorFiguras)",
                        "Detección Completada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Botón Ordenar
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.ordenarFigurasPorArea();
                JOptionPane.showMessageDialog(ventana,
                        "Figuras ordenadas por área!\n" +
                                "(Esta funcionalidad estará completa cuando implementes OrdenadorFiguras)",
                        "Ordenamiento Completado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ComboBox de listas
        comboListas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = comboListas.getSelectedIndex();
                ventana.cambiarLista(indice);
                actualizarInfo();
            }
        });

        // Checkboxes (estos requieren acceso al PanelPlanoCartesiano)
        // Por ahora quedan deshabilitados hasta que agregues los métodos públicos
        chkMostrarCuadricula.addActionListener(e -> {
            // TODO: ventana.getPanelPlano().setMostrarCuadricula(chkMostrarCuadricula.isSelected());
        });

        chkMostrarPuntos.addActionListener(e -> {
            // TODO: ventana.getPanelPlano().setMostrarPuntos(chkMostrarPuntos.isSelected());
        });

        chkMostrarFiguras.addActionListener(e -> {
            // TODO: ventana.getPanelPlano().setMostrarFiguras(chkMostrarFiguras.isSelected());
        });
    }

    // ========== MÉTODOS PÚBLICOS ==========

    // Actualiza la información mostrada en el panel
    public void actualizarInfo() {
        int actual = ventana.getIndiceListaActual() + 1;
        int total = ventana.getCantidadListas();
        lblInfo.setText("Lista " + actual + " de " + total);
        comboListas.setSelectedIndex(actual - 1);
    }

    // Habilita o deshabilita todos los controles
    public void setControlesHabilitados(boolean habilitado) {
        btnAnterior.setEnabled(habilitado);
        btnSiguiente.setEnabled(habilitado);
        btnDetectar.setEnabled(habilitado);
        btnOrdenar.setEnabled(habilitado);
        comboListas.setEnabled(habilitado);
    }
}