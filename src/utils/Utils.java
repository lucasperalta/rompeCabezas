package utils;

import java.util.Random;
import javax.swing.JOptionPane;

public class Utils {

    /**
     * genera un array a partir del enviado por parametro tomando aleatoriamente
     * la posicion de este y asignandola al nuevo array sin repetir posiciones
     *
     * @param posiciones
     *
     * @return
     */
    public static int[] generateRandom(final int[]  posiciones) {
        int cantidad = posiciones.length;
        Random rnd = new Random();
        int[] posicionesMezcladas = new int[cantidad];
        int auxTotal = cantidad;
        int numRandom;
        for (int i = 0; i < cantidad; i++) {
            numRandom = rnd.nextInt(auxTotal);
            posicionesMezcladas[i] = posiciones[numRandom];
            posiciones[numRandom] = posiciones[auxTotal - 1];
            auxTotal--;

        }

        return posicionesMezcladas;
    }
    
   
    public static void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje);
    }

}
