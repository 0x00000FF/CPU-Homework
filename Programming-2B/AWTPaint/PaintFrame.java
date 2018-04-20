package me.patche.javapaint;

import java.awt.*;
import java.awt.event.*; 
import java.awt.geom.Line2D;

import java.io.*;
import java.util.*;

class Draw implements Serializable {
	 private int x, y, x1, y1;
	 private int dist;
	 private int stroke;
	 private Color color;
	 
	 public int getDist() {return dist;}
	 public void setDist(int dist) {this.dist = dist;}
	 
	 public int getX() {return x;}
	 public void setX(int x) {this.x = x;}
	 
	 public int getY() {return y;}
	 public void setY(int y) {this.y = y;}

	 public int getX1() {return x1;}
	 public void setX1(int x1) {this.x1 = x1;}

	 public int getY1() {return y1;}
	 public void setY1(int y1) {this.y1 = y1;}
	 
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	
	public int getStroke() { return stroke; }
	public void setStroke(int stroke) { this.stroke = stroke; }
}

class PaintFrame extends Frame implements ActionListener, ItemListener, MouseListener, MouseMotionListener {
	private static final String title = "자바 AWT 그림판";
	
	private MenuBar mb = new MenuBar();
	private Menu fileTools = new Menu("파일");
	private MenuItem reset = new MenuItem("다시그리기");
	private MenuItem open = new MenuItem("열기");
	private MenuItem save = new MenuItem("저장");
	private MenuItem terminate = new MenuItem("끝내기");
	
	private Menu drawTools = new Menu("그리기 도구");
	public CheckboxMenuItem[] drawToolsSub =  new CheckboxMenuItem[] {
			new CheckboxMenuItem("펜", true),
			new CheckboxMenuItem("선"),
			new CheckboxMenuItem("타원"),
			new CheckboxMenuItem("직사각형")
	};
	
	private Menu lineThicknessOptions = new Menu("선 굵기");
	private MenuItem currentThickness = new MenuItem("굵기 : 1");
	private MenuItem thicker = new MenuItem("+1");
	private MenuItem thiner = new MenuItem("-1");
	
	
	private Menu lineColor = new Menu("선 색상");
	public CheckboxMenuItem[] lineColorSub = new CheckboxMenuItem[] {
			new CheckboxMenuItem("검정", true),
			new CheckboxMenuItem("빨강"),
			new CheckboxMenuItem("초록"),
			new CheckboxMenuItem("파랑"),
			new CheckboxMenuItem("노랑")
	};
	
	private int x, y, x1, y1, stroke = 1, dist = 1;
	private Color color = Color.black;
	
	public Vector<Draw> vc = new Vector<Draw>();

	
	
	public PaintFrame() {
		this.initializeMenu();
		
		super.setMenuBar(mb);
		
		super.setTitle(title);
		super.setSize(400, 400);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);

		super.setLocation(xpos, ypos);
		super.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	private void initializeMenu() {
		currentThickness.setEnabled(false);
		
		mb.add(fileTools);
		fileTools.add(reset);
		reset.setActionCommand("resetDrawing");
		reset.addActionListener(this);
		
		fileTools.add(open);
		fileTools.add(save);
		fileTools.add(terminate);
		terminate.setActionCommand("terminate");
		terminate.addActionListener(this);
		
		mb.add(drawTools);
		for (CheckboxMenuItem mi : drawToolsSub) {
			drawTools.add(mi);
			mi.addItemListener(this);
		}
		
		mb.add(lineThicknessOptions);
		lineThicknessOptions.add(currentThickness);
		lineThicknessOptions.add(thicker);
		thicker.setActionCommand("thicker");
		thicker.addActionListener(this);
		
		lineThicknessOptions.add(thiner);
		thiner.setActionCommand("thiner");
		thiner.addActionListener(this);
		
		mb.add(lineColor);
		for (CheckboxMenuItem mi : lineColorSub) {
			lineColor.add(mi);
			mi.addItemListener(this);
		}
		
		
	}

	@Override
	public void paint(Graphics g) {
		
		
		   for (Draw d : vc) {
			   Graphics2D g2 = (Graphics2D)g;
			   
			   g2.setColor(d.getColor());
			   g2.setStroke(new BasicStroke(d.getStroke()));

			   if(d.getDist() <= 1) {
				   g2.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
			   }
	
			   else if(d.getDist() == 2) {
				   g2.drawOval(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
			   }
	
			   else if(d.getDist() == 3) {
				   g2.drawRect(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
			   }
	    }
	}
	
	// Event Methods
	@Override
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		
		this.repaint();
		

		if (drawToolsSub[0].getState()) {
			Draw d = new Draw();
			d.setColor(color);
			d.setDist(dist);
			d.setStroke(stroke);
				
			d.setX(x);
			d.setY(y);
	
			d.setX1(x1);
			d.setY1(y1);
	
			vc.add(d);
			
			x = x1;
			y = y1;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.setTitle(title + " x: " + e.getX() + " / y: " + e.getY());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		
		this.repaint();
		
		if(!drawToolsSub[0].getState()) {  
		   Draw d = new Draw();

		   d.setDist(dist); 
		   d.setColor(color);
		   d.setStroke(stroke); 

		   d.setX(x);  
		   d.setY(y);
		   d.setX1(x1);
		   d.setY1(y1);

		   vc.add(d);
		  }
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CheckboxMenuItem item = (CheckboxMenuItem) e.getSource();
		for (CheckboxMenuItem cmi:drawToolsSub) {
			if (cmi.equals(item)) {
				for (CheckboxMenuItem cmiSet:drawToolsSub) {
					cmiSet.setState(false);
				}
				switch(item.getLabel()) {
				case "펜":
					dist = 0;
					break;
				case "선":
					dist = 1;
					break;
				case "타원":
					dist = 2;
					break;
				case "직사각형":
					dist = 3;
					break;
				}
				item.setState(true);
				return;
			}
		}
		
		for (CheckboxMenuItem cmi:lineColorSub) {
			if (cmi.equals(item)) {
				for (CheckboxMenuItem cmiSet:lineColorSub) {
					cmiSet.setState(false);
				}
				
				switch(item.getLabel()) {
					case "빨강":
						color = Color.RED;
						break;
					case "파랑":
						color = Color.BLUE;
						break;
					case "초록":
						color = Color.GREEN;
						break;
					case "노랑":
						color = Color.YELLOW;
						break;
					default:
						color = Color.BLACK;
						break;
				}
				item.setState(true);
				return;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "resetDrawings":
			vc.clear();
			break;
			
		case "terminate":
			System.exit(0);
			break;
			
		case "thicker":
			stroke++;
			currentThickness.setLabel("현재 굵기 : " + stroke);
			break;
			
		case "thiner":
			stroke--;
			currentThickness.setLabel("현재 굵기 : " + stroke);
			break;
		}
		
		this.repaint();
	}
		
}
	

