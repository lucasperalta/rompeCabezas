package rompecabezas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.ImageIcon;
import utils.*;
import pieza.PiezaRompeCabezas;

public class Controlador implements MouseListener {

    private final int[] posicionFinal =new int[]{-1, 2, 3, 4, 5, 6, 7, 8, 9}; // -1 =vacio
    private int[] posicionActual = new int[Constantes.FILAS * Constantes.COLUMNAS];
    Formulario form=null;
    private PiezaRompeCabezas arrayPiezas[] = new PiezaRompeCabezas[Constantes.FILAS * Constantes.COLUMNAS];
    Boolean terminarJuego= false;
    Boolean estaArmado=false;
    Boolean habilitarTablero=false;
    
    
    
    private void mezclarPiezas() {
        //uso el copy porque sino pasa el array por referencia y me lo modifica
        //aunque este declarado como final ...raro
        int[] posiciones=Arrays.copyOf(posicionFinal, posicionFinal.length);
        posicionActual = Utils.generateRandom(posiciones);
//posicionActual=new int[]{4,2, 3, -1, 5, 6, 7, 8, 9}; lo uso para test asi no tengo que armar todo el rompecabezas
        llenarTablero(posicionActual);
    }
    
    /**
     * Lleno el tablero  con las imagenes y el order
     * a partir de un array con las posiciones de las piezas
     * @param posiciones 
     */
    private void llenarTablero(int[] posiciones) {
        for (int i = 0; i < posiciones.length; i++) {
            if (posiciones[i] == -1) {
                this.arrayPiezas[i].setIcon(new ImageIcon(getClass().getResource("/imagenesPiezas/vacio.jpg")));
            } else {
                this.arrayPiezas[i].setIcon(new ImageIcon(getClass().getResource("/imagenesPiezas/" + posiciones[i] + ".jpg")));
            }
            this.arrayPiezas[i].setOrder(posiciones[i]);
        }
    }

    public void nuevoJuego(PiezaRompeCabezas[] piezas,Formulario form) {
        this.form=form;
        this.arrayPiezas=null;
        this.arrayPiezas = piezas;
        this.estaArmado=false;
        this.terminarJuego=false;
        this.posicionActual=null;
        mezclarPiezas();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(habilitarTablero){
            PiezaRompeCabezas piezaSeleccionada = (PiezaRompeCabezas) e.getSource();
            int posicionActualPieza = getPosicionPiezaEnPosActual(piezaSeleccionada.getOrder());
            int[] movimientosPosibles = calcularMovimientosPosibles(posicionActualPieza);
            Integer movimiento = puedoMoverPieza(movimientosPosibles);
            if (movimiento != null) {
                mover(movimiento, piezaSeleccionada);
                if(estaArmado){
                    terminarJuego();
                }
            }
        }
    }
    
    /**
     * Mueve la pieza que elegimos a la posicion vacia
     * y la posicion vacia a donde estaba la pieza seleccionada
     * lo hace sobre el array posicionActual
     * luego llena el tablero con las nuevas posiciones
     * @param movimiento
     * @param piezaSeleccionada 
     */
    private void mover(Integer movimiento, PiezaRompeCabezas piezaSeleccionada) {
        int tmpPos = getPosicionPiezaEnPosActual(piezaSeleccionada.getOrder());
        int tmpPiez = posicionActual[tmpPos];
        posicionActual[movimiento] = tmpPiez;
        posicionActual[tmpPos] = -1;
        llenarTablero(posicionActual);
        estaArmado();
    }

    /**
     * si la pieza vacia (order -1) esta en la posicion de un posible movimiento
     * ,devuelvo el lugar a donde mover la pieza Si devuelvo null es porque la
     * pieza que seleccione no se puede mover
     *
     * @param movimientosPosibles
     * @return
     */
    private Integer puedoMoverPieza(int[] movimientosPosibles) {
        Integer movimiento = null;
        for (int i = 0; i < movimientosPosibles.length; i++) {
            int posibleMovimiento = movimientosPosibles[i];
            if (posicionActual[posibleMovimiento] == -1) {
                movimiento = posibleMovimiento;
            }
        }
        return movimiento;
    }

    /**
     * devuelve la posicion de la pieza en el vector
     * |0 |1 |2
     * -------- 
     * |3 |4| 5
     * --------- 
     * |6 |7| 8
     *
     * @param order
     * @return
     */
    private int getPosicionPiezaEnPosActual(int order) {
        int posicionActualPieza = -1000; //pieza no existe, error
        for (int i = 0; i < posicionActual.length; i++) {
            if (posicionActual[i] == order) {
                posicionActualPieza = i;
            }
        }
        return posicionActualPieza;
    }

    /**
     * Calcula los movimientos posibles que puede hacer la pieza seleccionada
     * |0|1 |2
     * --------
     * |3 |4|5 
     * --------- 
     * |6 |7|8
     *
     * @param posicionActual
     * @return int[]movimientos posibles
     */
    private int[] calcularMovimientosPosibles(int posicionActual) {
        int[] movimientosPosibles = null;
        switch (posicionActual) {
            case 0:
                movimientosPosibles = new int[]{1, 3};
                break;
            case 1:
                movimientosPosibles = new int[]{0, 2, 4};
                break;
            case 2:
                movimientosPosibles = new int[]{1, 5};
                break;
            case 3:
                movimientosPosibles = new int[]{0, 4, 6};
                break;
            case 4:
                movimientosPosibles = new int[]{1, 3, 5, 7};
                break;
            case 5:
                movimientosPosibles = new int[]{2, 4, 8};
                break;
            case 6:
                movimientosPosibles = new int[]{3, 7};
                break;
            case 7:
                movimientosPosibles = new int[]{6, 4, 8};
                break;
            case 8:
                movimientosPosibles = new int[]{5, 7};
                break;
        }
        return movimientosPosibles;

    }

    /**
     * Si el array con las posiciones actuales es igual 
     * a las posiciones finales esta armado
     */
    private void estaArmado() {
        int cantidadCorrecta = 0;
        for (int i = 0; i < posicionFinal.length; i++) {
            if (posicionFinal[i] == posicionActual[i]) {
                cantidadCorrecta++;
            }
        }
        if (cantidadCorrecta == posicionFinal.length) {
            this.estaArmado = true;
        }
    }
    
    /**
     * termina el juego ya sea porque lo armo
     * o porque apreto el boton terminar
     */
    private void terminarJuego() {
        if (estaArmado || terminarJuego) { 
            setHabilitarTablero(false); 
            form.setEnabledNuevo(true);
            form.setEnabledTerminar(false);
            if (estaArmado) {
                Utils.mostrarMensaje("FELICIDADES!! GANO EL JUEGO!!");
            }else {
                Utils.mostrarMensaje("BUUUH, EL QUE ABANDONA NO TIENE PREMIO!");
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        //NO HACER NADA
    }

   
    @Override
    public void mouseReleased(MouseEvent e) {
        //Evento del listener que no tengo en cuenta para nada
    }

    @Override
    public void mouseEntered(MouseEvent e) {
         //Evento del listener que no tengo en cuenta para nada
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Evento del listener que no tengo en cuenta para nada
    }

    public int[] getPosicionFinal() {
        return this.posicionFinal;
    }

    public void setTerminarJuego(Boolean terminarJuego) {
        this.terminarJuego = terminarJuego;
         terminarJuego();
    }

    public void setHabilitarTablero(Boolean habilitarTablero) {
        this.habilitarTablero = habilitarTablero;
    }
}
