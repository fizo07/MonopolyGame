import java.awt.*;
import javax.swing.*;

class BoardDimension{
	public int height;
	public int width;
	public Dimension size;
	
	public BoardDimension(){
		}
		
	public void setHieght(int height){
		this.height=height;
		}
	
	public void setWidth(int width){
		this.width=width;
		}
	public void setDimension(Dimension size){
		this.size=size;
		}
	
	public static double getHeight(double length){
		return ((length/3.7)*2.7)/9;
		}
		
	public static double getWidth(double length){
		return (length/3.7)/2;
		}
	
	public static double getCenterSize(double length){
		return (length/3.7)*2.7;
		}
		
	public Dimension getPanelSize(JPanel p){
		return p.getSize();
		}
		
	public int boardsize(){
		double x=size.height;
		int y=(int)(x * 0.82);
		while(true){
		double z=y/3.7;
			if((z % 1) == 0){
				break;
			}
			y++;
		}
		return y;
	}
    
	public Dimension DeedSize(){
		Dimension d=new Dimension();
		int x=(int)(boardsize()*0.25);
		int y=(int)(boardsize()*0.2);
		d.setSize(y,x);
		return d;
	}
	
	public Dimension DeedPosition(){
		Dimension d=new Dimension();
		int x=(int)(boardsize()*0.15);
		int y=(int)(boardsize()*0.3);
		d.setSize(y,x);
		return d;
	}
		
}