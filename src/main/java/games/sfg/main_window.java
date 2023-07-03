
package games.sfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.rmi.dgc.VMID;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class main_window extends JFrame implements window {
    private JButton AddEdgeBtn;
    private JButton AddNodeBtn;
    private JButton TfBtn;
    private JButton LoopsBtn;
    private JButton PathsBtn;
    private JPanel drawingboard;
    private window Parent;
    private JMenuBar Menu;
    private JMenu menu;
    private JMenuItem about;
    private JMenuItem reset;
    private About_Window AboutWin;
    
    private graphes topG; 
    private Graphics g;
    private boolean move;
    private node nodeToMove;

    public main_window() {
        initComponents();
        topG = new graphes();
        g = drawingboard.getGraphics();
        drawingboard.printComponents(g);
        move = false;
    }
                         
    private void initComponents() {

        drawingboard = new JPanel();
        AddNodeBtn = new JButton();
        AddEdgeBtn = new JButton();
        TfBtn = new JButton();
        LoopsBtn = new JButton();
        PathsBtn = new JButton();
        Menu = new JMenuBar();
        menu = new JMenu("Help");
        about = new JMenuItem("about");
        reset  = new JMenuItem("reset");
        menu.add(reset);
        menu.add(about);
        Menu.add(menu);
        setJMenuBar(Menu);
        
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 aboutMouseClicked(evt);
            }
        });
        
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 resetMouseClicked(evt);
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        drawingboard.setBackground(new java.awt.Color(255, 255, 255));
        drawingboard.setBorder(new javax.swing.border.MatteBorder(null));
        drawingboard.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawingboardMouseDragged(evt);
            }});
        drawingboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                drawingboardMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(drawingboard);
        drawingboard.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        AddNodeBtn.setText("Add Node");
        AddNodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNodeBtnActionPerformed(evt);
            }
        });

        AddEdgeBtn.setText("Add Edge");
        AddEdgeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEdgeBtnActionPerformed(evt);
            }
        });

        TfBtn.setText("Transfer Function");
        TfBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TfBtnActionPerformed(evt);
            }
        });
        LoopsBtn.setText("Loops");
        LoopsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoopsBtnActionPerformed(evt);
            }
        });
        PathsBtn.setText("Paths");
        PathsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PathsBtnActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TfBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(AddNodeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddEdgeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PathsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LoopsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawingboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(drawingboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(AddNodeBtn)
                .addGap(18, 18, 18)
                .addComponent(AddEdgeBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PathsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LoopsBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TfBtn)
                .addContainerGap(183, Short.MAX_VALUE))
        );
        

        pack();
        
    }
    private void AddNodeBtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String name = JOptionPane.showInputDialog("insert node name ( it has to be unique )");
        if(!(topG.getNodes().keySet().contains(name))){
            node n = topG.addNode(name, new Point(0, 0),g);
        }
        else{
            JOptionPane.showInternalMessageDialog(null, "Node cant be added because not unique");
        }
    }                                          

    private void AddEdgeBtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String Gain_string = JOptionPane.showInputDialog("insert gain value");
        Double gain;
        String []Edge;
        int number = 1;
        try{
            gain = Double.parseDouble(Gain_string);
        }
        catch(Exception e){
            JOptionPane.showInternalMessageDialog(null, "please enter a valid gain");
            return;
        }
        try{
            Edge = JOptionPane.showInputDialog("insert start node and end node of the gain ( start,end )").split(",");}
        catch(Exception e){
        JOptionPane.showInternalMessageDialog(null, "please enter valid nodes");
        return;}
        if(!(topG.getNodes().containsKey(Edge[0])&&topG.getNodes().containsKey(Edge[1]))){//if nodes does not exist already
            JOptionPane.showInternalMessageDialog(null, "please enter nodes that exist in the graph");
            return;
        }
        topG.addEdge(gain, Edge[0],Edge[1], number,g);
    } 
    private void TfBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String n1 = JOptionPane.showInputDialog("insert Input Node");
        String n2 = JOptionPane.showInputDialog("insert Output Node");
        double tf = topG.tf(topG.getNodes().get(n1), topG.getNodes().get(n2));
        JOptionPane.showInternalMessageDialog(null,"tf ="+ tf);
    } 
    private void LoopsBtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        topG.printLoops();
    } 
    private void PathsBtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        topG.printPaths();
    }
    private void drawingboardMousePressed(java.awt.event.MouseEvent evt) {                                          

        Point p2 = drawingboard.getMousePosition();
        HashMap<String,node> allnodes = topG.getNodes();//check if not null first
        for(node n : allnodes.values()){
            if(n.contains(p2)){
                move = true;
                n.setDraggingPoint(p2);
                nodeToMove = n;
                return;
            }
        }
        move = false;
            
    }
    private void drawingboardMouseDragged(java.awt.event.MouseEvent evt) {                                          
       if(move){
           Point p2 = drawingboard.getMousePosition();
           nodeToMove.moveTo(p2);
           nodeToMove.setDraggingPoint(p2);
           g.setColor(drawingboard.getBackground());
           g.fillRect(0, 0, drawingboard.getWidth(), drawingboard.getHeight());
           for(node n : topG.getNodes().values()){
               n.draw(g);
           }
           for(edges e: topG.getEdges().values()){
               e.draw(g);
           }
       }
    }
    public void drawAll(){
        g.setColor(drawingboard.getBackground());
        g.fillRect(0, 0, drawingboard.getWidth(), drawingboard.getHeight());
        for(node n : topG.getNodes().values()){
               n.draw(g);
           }
        for(edges e: topG.getEdges().values()){
               e.draw(g);
           }
    }
    private void resetMouseClicked(java.awt.event.ActionEvent evt){
        topG = new graphes();
        g.setColor(drawingboard.getBackground());
        g.fillRect(0, 0, drawingboard.getWidth(), drawingboard.getHeight());  
    }
    private void aboutMouseClicked(java.awt.event.ActionEvent evt) {
        AboutWin = About_Window.getInstance();
        AboutWin.setParentNode(this);
        AboutWin.setVisible(true);
    }
    @Override
    public void setParentNode(window w) {
        this.Parent = w;
    }

    @Override
    public window getParentNode() {
        return Parent;
    }



                   
}
