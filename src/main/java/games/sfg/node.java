package games.sfg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
 
public class node{
        private String name;
        private Point point;
        private Point draggingPoint;
        private ArrayList<node> neighbors = new ArrayList<node>();
        private ArrayList<node> parents  = new ArrayList<node>();
        public node(String name, Point point) {
            this.name = name;
            this.point = point;
            this.neighbors = new ArrayList<node>();
            this.parents  = new ArrayList<node>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public Point getDraggingPoint() {
            return draggingPoint;
        }

        public void setDraggingPoint(Point draggingPoint) {
            this.draggingPoint = draggingPoint;
        }

        public ArrayList<node> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(ArrayList<node> neighbors) {
            this.neighbors = neighbors;
        }  
        
        public void addNeighbor(node n){
            neighbors.add(n);
        }

        public ArrayList<node> getParents() {
            return parents;
        }

        public void setParents(ArrayList<node> parents) {
            this.parents = parents;
        }
        public void addParent(node n){
            parents.add(n);
        }
        public void draw(Graphics g){
            g.setColor(Color.BLACK);
            g.drawOval(point.x+20,point.y+20, 10, 10);
            g.drawString(this.name,point.x+10, point.y+15);
        }
        public void moveTo(Point point) {
            if(getDraggingPoint() != null){
                setPoint(new Point(getPoint().x + (point.x - getDraggingPoint().x),getPoint().y + (point.y - getDraggingPoint().y)));}
        }
    
        public boolean contains(Point point) {
            if((point.x <= (this.getPoint().x + 30) && point.x >= this.getPoint().x + 10) && (point.y <= (this.getPoint().y + 30) && point.y >= this.getPoint().y+10)){
                return true;
            }
            return false;
    }
        
    }

