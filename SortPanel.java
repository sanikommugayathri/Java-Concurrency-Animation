import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import javafx.animation.PauseTransition;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//class sortPanel extends JPanel
public class SortPanel extends JPanel 
{
    //button populatearray
    private JButton populateArrayButton;
    //button sort
    private JButton sortButton;
    //button pauseresume
    private JButton pauseResumeBtn;
    //button stop
    private JButton stopBtn;
    //comboBox
    private JComboBox<String> comboBox;
    //sortSpeed combo box
    private JComboBox<String> sortSpeedCB;
    //direction of sort combo box
    private JComboBox<String> directionOfSortCB;
    //initial sortorder combo box
    private JComboBox<String> initialSortOrderCB;
    //sortinganimationpanel
    private JPanel sap;
    
    //panel height and width
    private final int panelWidth = 700;
    private final int panelHeight = 600;
    
    private int[] list = new int[panelWidth];
    private int[] sortedList;
    
    //sorting algorithms
    private String[] algorithms = {"Heap","Selection", "Insertion"};
    //sorting speed
    private String[] sortSpeedItems = {"Slow", "Medium", "Fast"};
    //order of population
    private String[] orderPopulated = {"Random", "Ascending","Descending"};
    //direction of sort
    private String[] directionOfSort = {"Ascending", "Descending"};
    
    private Thread t;
    private boolean threadStarted = false;
    
    //sort panel
    SortPanel()
    {              
        createButton();//creating a button
        createComboBox();//creating a combobox
        addControls();//add controls
        
    }
    
    public void addControls()
    {
        sap = new SortAnimationPanel();
        sap.setPreferredSize(new Dimension(panelWidth, panelHeight));  //dimensions     
        add(sap);//add sortinganimation panel
        
        add(initialSortOrderCB);//add initial sort order
        add(populateArrayButton);//add populate button
        add(sortButton);//add sort button
        add(comboBox);//add combo box
        add(sortSpeedCB);//add sort speed
        add(directionOfSortCB);//add direction of sort
        add(pauseResumeBtn);//add pause resume button
        add(stopBtn);//add stop button
       
    }
    
    //function create button
    public void createButton()
    {
        //populate button
        populateArrayButton = new JButton("PopulateArray");
        //sort button
        sortButton = new JButton("Sort");
        //pause resume button
        pauseResumeBtn = new JButton("Pause");
        //stop button
        stopBtn = new JButton("Stop");
        
        //action listener for populte button
        ActionListener pab1 = new populateArrayButtonListener();
        populateArrayButton.addActionListener(pab1);
        sortButton.setEnabled(false);
        
        //action listener for sort button
        ActionListener sb1 = new SortButtonListener();
        sortButton.addActionListener(sb1);
        
        //action listener for pause resume button
        ActionListener pr1 = new pauseResumeButtonListener();
        pauseResumeBtn.addActionListener(pr1);
        
        //action listener for stop button
        ActionListener stopb1 = new stopButtonListener();
        stopBtn.addActionListener(stopb1);
                
    }
    
    //function create combo box
    public void createComboBox()
    {
        comboBox = new JComboBox<String>(algorithms);
        sortSpeedCB = new JComboBox<String>(sortSpeedItems);
        initialSortOrderCB = new JComboBox<String>(orderPopulated);
        directionOfSortCB = new JComboBox<String>(directionOfSort);
    }

    //class sortAnimationPanel 
    class SortAnimationPanel extends JPanel implements Runnable
    {
       
        private int sortTime = 0;
        private String selectedAlgo;
        
        private static final long serialVersionUID = 1L;
        
        @Override
        public void run()
        {
            if(sortSpeedCB.getSelectedIndex() == 0)//if sortspeed slow is selected
            {
                sortTime = 5000;
            }
            else if(sortSpeedCB.getSelectedIndex() == 1)//if sortspeed medium is selected
            {
                sortTime = 1000;
            }else if(sortSpeedCB.getSelectedIndex() == 2)//if sort speed fast is selected
            {
                sortTime = 100;
            }
            
            System.out.println("Running a thread");
            
            //if Selection sort algorithm is selected
            if(selectedAlgo == "Selection")
            { 
                try 
                {
                    doSelectionSort(list, sortTime); 
                    
                }
                catch (InterruptedException e)
                {
                  e.printStackTrace();
                }
                
            }
            //if insertion sort algorithm is selected
            else if(selectedAlgo == algorithms[2])
            {
                try 
                {
                    doInsertionSort(list, sortTime);
                }
                catch (InterruptedException e)
                {
                  e.printStackTrace();
                }
            }
            //if heap sort algorithm is selected
            else if(selectedAlgo == algorithms[0])
            {    
                try 
                {
                    HeapSort ob = new HeapSort(); 
                    ob.sort(list,sortTime); 

                }
                catch (InterruptedException e)
                {
                  e.printStackTrace();
                }                
            }
            
            sortButton.setEnabled(true);
            populateArrayButton.setEnabled(true);
            
                              
        }
        
        @Override       
        public void paintComponent(Graphics g)
        {
            //paint component
            super.paintComponent(g);
            setBackground(Color.WHITE);//set background color to white
            g.setColor(Color.blue);//set color to blue
            if(list.length > 0)
            {
                if(directionOfSortCB.getSelectedIndex() == 0)
                {
                for(int j=0; j < list.length; j++)
                {
                    g.drawLine(j, panelHeight, j, panelHeight - list[j]);
                }
                }
                else if(directionOfSortCB.getSelectedIndex() == 1)
                {
                for(int j=0; j < list.length; j++)
                {
                    g.drawLine(j, panelHeight, j, list[j]);
                }    
                }
            }
            
        }
        
        //function start
        public void start(String xyz)
        {
            selectedAlgo = xyz;//selected algorithm
            
            System.out.println("Starting a thread with algo" +xyz);
            
            t = new Thread(this, "Thread1");
            t.start();//start thread
            threadStarted = true;
        }
        
        //if the selected algorithm is selection sort
        public int[] doSelectionSort(int [] arr, int sortTime) throws InterruptedException
        {
            for(int j = 0; j < arr.length - 1; j++)
            {
                int index = j;
                for (int i = j + 1; i < arr.length; i++)
                    if (arr[i] < arr[index])
                        index = i;
                int smallNum = arr[index];//smallnumber equals index
                arr[index] = arr[j];//swap index with j
                arr[j] = smallNum;//swap j with smallnumber
                repaint();
                
                Thread.sleep(sortTime);//thread sleep time
            }
            
            return arr;
        }
        
        //if the seleted algorithm is insertion sort
        public int[] doInsertionSort(int[] input ,int sortTime) throws InterruptedException
        {
            int temp;
            
            for(int j = 1; j < input.length; j++)
            {
                for(int i = j; i > 0; i--)
                {
                    if(input[i] < input[i-1])
                    {
                        temp = input[i];//temp equals i
                        input[i] = input[i-1];//i equals i-1
                        input[i-1] = temp;//i-1 equals temp
                        
                    }
                    
                }
                repaint();
                    Thread.sleep(sortTime);//thread sleep time
            }    
            
            return input;
        }
         
        
        // Java program for implementation of Heap Sort 
        public class HeapSort 
        { 
            public void sort(int arr[],int sortTime) throws InterruptedException 
            { 
                int n = arr.length; 
  
                // Build heap (rearrange array) 
                for (int i = n / 2 - 1; i >= 0; i--) 
                    heapify(arr, n, i, sortTime); 
  
                // One by one extract an element from heap 
                for (int i=n-1; i>=0; i--) 
                { 
                    // Move current root to end 
                    int temp = arr[0]; 
                    arr[0] = arr[i]; 
                    arr[i] = temp; 
  
                    // call max heapify on the reduced heap 
                    heapify(arr, i, 0, sortTime); 
                } 
            } 
  
            // To heapify a subtree rooted with node i which is 
            // an index in arr[]. n is size of heap 
            void heapify(int arr[], int n, int i, int sortTime) throws InterruptedException 
            { 
                int largest = i; // Initialize largest as root 
                int l = 2*i + 1; // left = 2*i + 1 
                int r = 2*i + 2; // right = 2*i + 2 
  
                // If left child is larger than root 
                if (l < n && arr[l] > arr[largest]) 
                    largest = l; 
  
                // If right child is larger than largest so far 
                if (r < n && arr[r] > arr[largest]) 
                    largest = r; 
  
                // If largest is not root 
                if (largest != i) 
                { 
                    int swap = arr[i]; 
                    arr[i] = arr[largest]; 
                    arr[largest] = swap; 
  
                    repaint();
                    Thread.sleep(sortTime);//thread sleep time
                // Recursively heapify the affected sub-tree 
                heapify(arr, n, largest, sortTime); 
                } 
            }            
       } 
        
    }
    
    //function fill array
    void fillArray()
    {       
        list = new int[panelWidth];        
        int min = 1;
        int max = panelHeight;        
        Random rand = new Random();
        
        for(int j = 0; j < list.length; j++)
        {
            list[j] = rand.nextInt((max - min) + 1) + min;
        }
       
        if(initialSortOrderCB.getSelectedIndex() == 1)
        {
            Arrays.sort(list);            
        }
        else if(initialSortOrderCB.getSelectedIndex() == 2)
        {
            Arrays.sort(list);
            
            for(int i=0; i<list.length/2; i++){ 
              int temp = list[i]; 
              list[i] = list[list.length -i -1]; 
              list[list.length -i -1] = temp; 
            }


        }
        
        sortButton.setEnabled(true);//enable sort button
    }
    
    //class populate array button
    class populateArrayButtonListener implements ActionListener
    {
        @Override
        
        public void actionPerformed(ActionEvent arg0)
        {
            fillArray();//fill array
            
            sap.repaint();//repaint sortinganimationpanel
            
            populateArrayButton.setEnabled(false);
        }
    }
    
    //class sortbutton
    class SortButtonListener implements ActionListener
    {
        @Override
        
        public void actionPerformed(ActionEvent e)
        {
            String selectedValue = (comboBox.getSelectedItem().toString());//selected value
            
            ((SortAnimationPanel) sap).start(selectedValue);
            
            sortButton.setEnabled(false);
        }
    }
    
    //class pauseresume button
    class pauseResumeButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            if(pauseResumeBtn.getText() == "Pause")//if we click on pause button
            {
                t.suspend();//suspend thread
                pauseResumeBtn.setText("Resume");//display resume inplace of pause
            }
            else if(pauseResumeBtn.getText() == "Resume")//if we click on resume button
            {
                t.resume();
                pauseResumeBtn.setText("Pause");//display pause 
            }
        }
    }
    
    //class stopButton
    class stopButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(threadStarted) //if thread starts               
                t.stop();//stop thread
            
            populateArrayButton.setEnabled(true);
        }
    }
    
    
}