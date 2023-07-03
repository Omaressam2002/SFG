package games.sfg;


import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import games.sfg.node;
import games.sfg.edges;
import javax.swing.JOptionPane;

public class graphes {
    private HashMap<String,node> nodes;
    private HashMap<String,edges> ed;
    private boolean tf_state;    
    private ArrayList<ArrayList<edges>> paths;
    private ArrayList<Double> pgains;
    private ArrayList<edges> pathProspect;
    private double temp_gain;
    private ArrayList<ArrayList<edges>> loops;
    private ArrayList<edges> loopProspect; 
    
    public graphes(){
        nodes = new HashMap<String,node>();
        ed = new HashMap<String,edges>();
        tf_state = false;
    }
    private void resetpaths(){
        paths = new ArrayList<ArrayList<edges>>();
        pgains = new ArrayList<Double>();
        pathProspect = new ArrayList<edges>();
        temp_gain=1;
    }
    private void resetloops(){
        loops = new ArrayList<ArrayList<edges>>();
        loopProspect = new ArrayList<edges>();
    }
    public node addNode(String Node,Point point, Graphics g){
        tf_state = false;
        node n = new node(Node, point);
        nodes.put(Node,n);
        n.draw(g);
        n.draw(g);
        n.draw(g);
        return n;
    }
    public void addEdge(Double gain, String start, String end,int number,Graphics g){
        tf_state = false;
        node n1 = getNodes().get(start);
        node n2 = getNodes().get(end);
        edges edge = new edges(gain, n1, n2);
        String path = start+","+end;
        ed.put(path, edge);
        edge.draw(g);
    }
    private void getPath(node start, node end){
        checkPath(start, end);
        for(ArrayList<edges> p: paths){
            pgains.add(getGAIN(p));
        }
    }
    private void checkPath(node start,node end){
        for(node n: start.getNeighbors()){
            edges e = ed.get(start.getName()+","+n.getName());
            if(e.isDirection()){
                pathProspect.add(e);
                if(n.equals(end)){
                    paths.add(new ArrayList<edges>(pathProspect));
                }
                else{
                    checkPath(n, end); 
                }
                pathProspect.remove(e);
            }
        }
    }
    private void getloops(){
        for(node n : nodes.values()){
            HashMap<node,Boolean> visited = new HashMap<node,Boolean>();
            for(node n2 : nodes.values()){
                if (!n2.equals(n)){
                    visited.put(n2, false); //mark all as unvisited
                }
                else{
                    visited.put(n2,true);  //only the current node to be visited
                }
            }
            check_loops(n, n,visited);
            loopProspect = new ArrayList<edges>();//clear it
        }
        removeDuplicateLoops();
    }
    private void removeDuplicateLoops(){
        ArrayList<ArrayList<edges>> loopsToBeRemoved = new ArrayList<ArrayList<edges>>();
        for(int i=0; i<(loops.size()-1);i++){
            for(int j=i+1;j<loops.size();j++){
                if(isADuplicate(loops.get(i),loops.get(j))){
                    loopsToBeRemoved.add(loops.get(j));
                }
            }
        
        }
        for(ArrayList<edges> l : loopsToBeRemoved){
            if(loops.contains(l)){
                loops.remove(l);
            }
        }
    }
    private boolean isADuplicate(ArrayList<edges> l1,ArrayList<edges> l2){
        if(l1.size() == l2.size()){ // has to be of same size and has the same set of edges in any order
            for(int i=0;i<l1.size();i++){
                if(!(l2.contains(l1.get(i)))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    private void check_loops(node start,node current,HashMap<node,Boolean> visited){
        for(node n: current.getNeighbors()){
           edges e = ed.get(current.getName()+","+n.getName());
           loopProspect.add(e);
           if(visited.get(n)){
               if(n.equals(start)){ //cycle to be added
                    loops.add(new ArrayList<edges>(loopProspect));
                    }
                loopProspect.remove(e);
                continue;
               
           }
           visited.replace(n, true);
           check_loops(start, n, visited);
           loopProspect.remove(e);
           visited.replace(n, false);
        }

    }
    private double getGAIN(ArrayList<edges> a){
        double ans = 1;
        for(int i = 0 ; i < a.size(); i++){
            ans *= a.get(i).getGain();
        }
        return ans;
    }
    private double sumOfAllLoopGains(){
        double sum = 0;
        for(ArrayList<edges> l : loops){
            sum = sum + getGAIN(l);
        }
        return sum;
    }
    private boolean AreTouchingloops(ArrayList<edges> l1,ArrayList<edges> l2){
        node[] v1 = getVS(l1);
        node[] v2 = getVS(l2);
        for(int i = 0;i<v1.length;i++){
            for(int j=0;j<v2.length;j++){
               if(v1[i].equals(v2[j])){
                    return true;
               }
            }
            
        }
        return false;
    }
    private double sumOfProductsOfNon_TouchingLoops(){
        double SOP = 0;
        for(int i = 0 ;i<loops.size();i++){
            for(int j=i+1;j<loops.size();j++){
                if(!(AreTouchingloops(loops.get(i), loops.get(j)))){
                    SOP = SOP + (getGAIN(loops.get(i))*getGAIN(loops.get(j)));
                }
            }
        }
        return SOP;
    }
    private double calcDELTA_I(int i){
        double sum=0;
        ArrayList<edges> path_i = paths.get(i);
        for(int j=0;j<loops.size();j++){
            if(!(AreTouchingloops(loops.get(j),path_i))){
                sum = sum + getGAIN(loops.get(j));
            }
        }
        return (1-sum);
    }
    private node[] getVS(ArrayList<edges> l){
        node[] vs = new node[l.size()+1];
        int i = 0;
        edges tempedge = null;
        for(edges e: l){
            tempedge = e;
            vs[i] = tempedge.getStart();
            i++;
        }
        if(tempedge != null){
            vs[i] = tempedge.getEnd();}
        return vs;
    }
    public double tf(node input, node output){
        resetpaths();
        resetloops();   
        getPath(input, output);
        getloops();
        double DELTA = 1 - sumOfAllLoopGains() + sumOfProductsOfNon_TouchingLoops();
        double[] DELTA_I = new double[paths.size()];
        for(int i = 0;i<DELTA_I.length;i++){
            DELTA_I[i]= calcDELTA_I(i);// 1 - loops not touching path i
        }
        double ans=0;
        for(int i = 0;i<DELTA_I.length;i++){
            ans = ans +(DELTA_I[i]*pgains.get(i));//pi * delta_i
        }
        ans = (ans)/DELTA;
        tf_state = true;
        return ans;
}
    public void printLoops(){
        if(!tf_state){
            JOptionPane.showMessageDialog(null,"You haven't calculated the tranfer function yet!!");
            return;
        }
        StringBuffer out=new StringBuffer();

        for (int i = 0; i < loops.size(); i++) {
            out.append("L");
            out.append(String.valueOf(i+1));
            out.append("\n");
            out.append("------");
            out.append("\n");
            for(int j=0; j < loops.get(i).size(); j++){
               out.append(loops.get(i).get(j).getStart().getName());
               out.append("  ------>  ");
               out.append(loops.get(i).get(j).getEnd().getName());
               out.append(" Gain : ");
               out.append(String.valueOf(loops.get(i).get(j).getGain()));
               out.append("\n");
            }
            out.append("total Gain : ");
            out.append(String.valueOf(getGAIN(loops.get(i))));
            out.append("\n");
            out.append("------");
            out.append("\n");
        }
        JOptionPane.showMessageDialog(null,out.toString(),"Loops", JOptionPane.INFORMATION_MESSAGE);
    }
    public void printPaths(){
        if(!tf_state){
            JOptionPane.showMessageDialog(null,"You haven't calculated the tranfer function yet!!");
            return;
        }
        StringBuffer out=new StringBuffer();
        for (int i = 0; i < paths.size(); i++) {
            out.append("P");
            out.append(String.valueOf(i+1));
            out.append("\n");
            out.append("------");
            out.append("\n");
            for(int j=0; j < paths.get(i).size(); j++){
               out.append(paths.get(i).get(j).getStart().getName());
               out.append("  ------>  ");
               out.append(paths.get(i).get(j).getEnd().getName());
               out.append(" Gain : ");
               out.append(String.valueOf(paths.get(i).get(j).getGain()));
               out.append("\n");
            }
            out.append("total Gain : ");
            out.append(String.valueOf(getGAIN(paths.get(i))));
            out.append("\n");
            out.append("------");
            out.append("\n");
        }
        JOptionPane.showMessageDialog(null,out.toString(),"Paths", JOptionPane.INFORMATION_MESSAGE);
    }
    public HashMap<String, node> getNodes() {
        return nodes;
    }

    public HashMap<String, edges> getEdges() {
        return ed;
    }
    
    
}
