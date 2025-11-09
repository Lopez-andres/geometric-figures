package interfaz;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import modelos.Figura;

// Panel que muestra las estadísticas de las figuras detectadas
// Cumple con el punto 3 y 6 del proyecto

public class PanelEstadisticas extends JPanel {
    private JLabel lblTitulo;
    private JPanel panelContadores, panelListaFiguras;

    // Labels para contadores
    private JLabel lblCuadrados, lblRectangulos, lblTriangulosRect, lblTriangulosAcut, lblTotalFiguras;

    // Constructor
    public PanelEstadisticas() {
        configurarPanel();
        inicializarComponentes();
        configurarLayout();
    }

    // ========== MÉTODOS DE CONFIGURACIÓN ==========

    // Configura las propiedades del panel
    private void configurarPanel() {
        setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // Inicializa todos los componentes
    private void inicializarComponentes() {
        // Título
        lblTitulo = new JLabel("ESTADÍSTICAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(52, 73, 94));

        // Panel de contadores
        panelContadores = crearPanelContadores();

        // Panel de lista de figuras
        panelListaFiguras = new JPanel();
        panelListaFiguras.setLayout(new BoxLayout(panelListaFiguras, BoxLayout.Y_AXIS));
        panelListaFiguras.setBackground(Color.WHITE);
        panelListaFiguras.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(149, 165, 166), 2),
                "Figuras Detectadas (ordenadas por área)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(52, 73, 94)
        ));
    }

    // Crea el panel de contadores
    private JPanel crearPanelContadores() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(280, 220));

        // Crear labels de contadores
        lblTotalFiguras = crearLabelContador("TOTAL FIGURAS:", "0", new Color(52, 73, 94));
        lblCuadrados = crearLabelContador("Cuadrados:", "0", new Color(52, 152, 219));
        lblRectangulos = crearLabelContador("Rectángulos:", "0", new Color(46, 204, 113));
        lblTriangulosRect = crearLabelContador("Triángulos Rectángulos:", "0", new Color(230, 126, 34));
        lblTriangulosAcut = crearLabelContador("Triángulos Acutángulos:", "0", new Color(241, 196, 15));

        // Agregar al panel
        panel.add(lblTotalFiguras);
        panel.add(crearSeparador());
        panel.add(lblCuadrados);
        panel.add(lblRectangulos);
        panel.add(lblTriangulosRect);
        panel.add(lblTriangulosAcut);

        return panel;
    }

    // Crea un label contador con formato
    private JLabel crearLabelContador(String texto, String valor, Color color) {
        JLabel label = new JLabel(texto + " " + valor);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        return label;
    }

    //Crea un separador visual
    private JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(189, 195, 199));
        return sep;
    }

    // Configura el layout del panel
    private void configurarLayout() {
        // Título
        add(lblTitulo);
        add(Box.createRigidArea(new Dimension(0, 15)));

        // Panel de contadores
        add(panelContadores);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de lista de figuras
        add(panelListaFiguras);
        add(Box.createVerticalGlue());
    }

    // ========== MÉTODOS PÚBLICOS ==========

    // Actualiza las estadísticas con la lista de figuras detectadas
    public void actualizarEstadisticas(List<Figura> figuras) {
        if (figuras == null || figuras.isEmpty()) {
            limpiarEstadisticas();
            return;
        }

        // Contar figuras por tipo
        Map<String, Integer> contadores = contarFigurasPorTipo(figuras);

        // Actualizar labels de contadores
        actualizarContadores(contadores, figuras.size());

        // Actualizar lista de figuras
        actualizarListaFiguras(figuras);
    }


    // Cuenta cuántas figuras hay de cada tipo
    private Map<String, Integer> contarFigurasPorTipo(List<Figura> figuras) {
        Map<String, Integer> contadores = new HashMap<>();
        contadores.put("cuadrados", 0);
        contadores.put("rectangulos", 0);
        contadores.put("triangulosRect", 0);
        contadores.put("triangulosAcut", 0);

        for (Figura f : figuras) {
            String nombre = f.getNombre().toLowerCase();

            if (nombre.contains("cuadrado")) {
                contadores.put("cuadrados", contadores.get("cuadrados") + 1);
            } else if (nombre.contains("rectángulo") || nombre.contains("rectangulo")) {
                if (nombre.contains("triángulo") || nombre.contains("triangulo")) {
                    contadores.put("triangulosRect", contadores.get("triangulosRect") + 1);
                } else {
                    contadores.put("rectangulos", contadores.get("rectangulos") + 1);
                }
            } else if (nombre.contains("acutángulo") || nombre.contains("acutangulo")) {
                contadores.put("triangulosAcut", contadores.get("triangulosAcut") + 1);
            }
        }

        return contadores;
    }

    // Actualiza los contadores visuales
    private void actualizarContadores(Map<String, Integer> contadores, int total) {
        lblTotalFiguras.setText("TOTAL FIGURAS: " + total);
        lblCuadrados.setText("Cuadrados: " + contadores.get("cuadrados"));
        lblRectangulos.setText("Rectángulos: " + contadores.get("rectangulos"));
        lblTriangulosRect.setText("Triángulos Rectángulos: " + contadores.get("triangulosRect"));
        lblTriangulosAcut.setText("Triángulos Acutángulos: " + contadores.get("triangulosAcut"));
    }

    // Actualiza la lista visual de figuras con sus áreas
    private void actualizarListaFiguras(List<Figura> figuras) {
        // Limpiar lista anterior
        panelListaFiguras.removeAll();

        // Agregar cada figura
        for (int i = 0; i < figuras.size(); i++) {
            Figura f = figuras.get(i);
            JPanel itemFigura = crearItemFigura(i + 1, f);
            panelListaFiguras.add(itemFigura);
            panelListaFiguras.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Si no hay figuras, mostrar mensaje
        if (figuras.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay figuras detectadas");
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 12));
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelListaFiguras.add(lblVacio);
        }

        // Refrescar panel
        panelListaFiguras.revalidate();
        panelListaFiguras.repaint();
    }

    // Crea un item visual para una figura
    private JPanel crearItemFigura(int numero, Figura figura) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.setMaximumSize(new Dimension(260, 70));

        // Número de la figura
        JLabel lblNumero = new JLabel(String.valueOf(numero));
        lblNumero.setFont(new Font("Arial", Font.BOLD, 16));
        lblNumero.setForeground(obtenerColorPorTipo(figura));
        lblNumero.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

        // Información de la figura
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1));
        panelInfo.setOpaque(false);

        JLabel lblNombre = new JLabel(figura.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        lblNombre.setForeground(new Color(44, 62, 80));

        JLabel lblArea = new JLabel(String.format("Área: %.2f u²", figura.getArea()));
        lblArea.setFont(new Font("Arial", Font.PLAIN, 11));
        lblArea.setForeground(new Color(127, 140, 141));

        panelInfo.add(lblNombre);
        panelInfo.add(lblArea);

        panel.add(lblNumero, BorderLayout.WEST);
        panel.add(panelInfo, BorderLayout.CENTER);

        return panel;
    }

    // Obtiene un color según el tipo de figura
    private Color obtenerColorPorTipo(Figura figura) {
        String nombre = figura.getNombre().toLowerCase();

        if (nombre.contains("cuadrado")) {
            return new Color(52, 152, 219);
        } else if (nombre.contains("rectángulo") || nombre.contains("rectangulo")) {
            if (nombre.contains("triángulo") || nombre.contains("triangulo")) {
                return new Color(230, 126, 34);
            }
            return new Color(46, 204, 113);
        } else if (nombre.contains("triángulo") || nombre.contains("triangulo")) {
            return new Color(241, 196, 15);
        }

        return new Color(149, 165, 166);
    }

    // Limpia todas las estadísticas
    public void limpiarEstadisticas() {
        lblTotalFiguras.setText("TOTAL FIGURAS: 0");
        lblCuadrados.setText("Cuadrados: 0");
        lblRectangulos.setText("Rectángulos: 0");
        lblTriangulosRect.setText("Triángulos Rectángulos: 0");
        lblTriangulosAcut.setText("Triángulos Acutángulos: 0");

        panelListaFiguras.removeAll();
        JLabel lblVacio = new JLabel("No hay figuras detectadas");
        lblVacio.setFont(new Font("Arial", Font.ITALIC, 12));
        lblVacio.setForeground(Color.GRAY);
        panelListaFiguras.add(lblVacio);

        panelListaFiguras.revalidate();
        panelListaFiguras.repaint();
    }
}