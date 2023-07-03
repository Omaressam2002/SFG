package games.sfg;

import java.awt.Color;
import java.awt.Graphics;
import games.sfg.node;

public class edges {
        private Double gain;
        private node start,end;
        private boolean direction;//false if Feedback path
        public edges(Double gain,node start,node end) {
            this.gain = gain;
            this.start = start;
            this.end = end;
            this.start.addNeighbor(end);
            this.end.addParent(start);
            setDirection();
        }
        
        public Double getGain() {
            return gain;
        }
        public node getStart() {
            return start;
        }

        public node getEnd() {
            return end;
        }

        public boolean isDirection() {
            return direction;
        }
        private void setDirection(){
            this.direction = (end.getPoint().x>start.getPoint().x);
        }
        public void draw(Graphics g){
            g.setColor(Color.BLACK);
            if(direction){
                g.drawLine(start.getPoint().x+30, start.getPoint().y+25, end.getPoint().x+20, end.getPoint().y+25);
                g.drawString(gain+ ">", start.getPoint().x+(((end.getPoint().x+20)-(start.getPoint().x+30))/2),start.getPoint().y+10);
            }
            else if(!direction){
               g.drawArc(end.getPoint().x+25, end.getPoint().y+10,Math.abs(start.getPoint().x-end.getPoint().x),50 ,0, -180);
               g.drawString(gain+ "<", start.getPoint().x+(((end.getPoint().x+25)-(start.getPoint().x+30))/2)+5, start.getPoint().y+((50)/2)+15);
            }
       }

}

