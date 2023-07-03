package games.sfg;

import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class About_Window extends JFrame implements window{
    private window Parent;
    private static About_Window instance;
    private JDialog jDialog1;
    private TextArea text; 

    private About_Window() {
        initComponents();
    }
    public static About_Window getInstance(){
        if(instance == null){
            instance = new About_Window();
        }
        return instance;
    }
                        
    private void initComponents() {

        jDialog1 = new JDialog();
        text = new TextArea();
        Dimension d  = new Dimension(700, 490);
        setPreferredSize(d);
        setResizable(false);
        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        text.setFont(new java.awt.Font("Chalkduster", 1, 14));
        StringBuffer out = new StringBuffer();
        out.append("1- Add Nodes and drag them to the desired position.\n\n");
        out.append("2- Add edges:\n");
        out.append(" *- specify gain(must be a Real number).\n");
        out.append(" *- specify start and end nodes in this format: start,end.\n\n");
        out.append("3- Calculate the Transfer Function.\n\n");
        out.append("4- Analyze your Graph by printing the Loops and");
        out.append(" the paths of the Graph.\n\n");
        out.append("5- Reset Button in the Menu bar.\n\n");
        out.append("PS:\n\n**the Direction of the edge will be Determined by the position of the nodes.\n");
        out.append("So if start node came - coordinately - before the end node the edge will be\na forward branch,\n");
        out.append("But if the end node came first the edge will be a feedback branch.\n\n");
        out.append("** You can't have 2 branches between the same two nodes\n");
        out.append("if that's the case add their gains into one branch.");
        text.setText(out.toString());
        text.setEditable(false);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }                       

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        instance.setVisible(false);
        ((JFrame)getParentNode()).setVisible(true);
        ((main_window)getParentNode()).drawAll();
    }                                  


    @Override
    public void setParentNode(window w) {
        instance.Parent = w;
    }

    @Override
    public window getParentNode() {
        return instance.Parent;
    }
                   
}