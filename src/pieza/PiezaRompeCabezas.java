package pieza;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author lucas
 */
public class PiezaRompeCabezas extends JLabel{
       private Icon image1 ;
      //representa el numero de pieza
       private int order; 
    public PiezaRompeCabezas(Icon imagen)
    {
        this.image1=imagen;
        this.setPreferredSize(new Dimension(200,150));
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.setIcon(image1);
        this.setVisible(true);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    
    
}
