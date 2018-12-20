import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//class sortinganimation
public class SortingAnimation extends JFrame
{
    //panel1
    JPanel panel1;
    //panel2
    JPanel panel2;
    
    SortingAnimation()
    {
        super("Sorting Animation");        
        setLayout(new GridLayout(1,2));//set gridlayout
        panel1 = new SortPanel();
        panel2 = new SortPanel();
        
        add(panel1);//add panel1
        add(panel2); //add panel2
        
    }
        
    //main
    public static void main(String[] args) 
    {
        // TODO code application logic here
        
        JFrame sa = new SortingAnimation();        
        sa.setSize(1500,740);//set size 
        sa.setDefaultCloseOperation(EXIT_ON_CLOSE);//set default cose operation
        sa.setVisible(true);//set visible
    }
    
}

  
  


 
 
