package interfaz;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Usar el tema del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo establecer el tema del sistema");
        }

        // Crear y mostrar la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
}