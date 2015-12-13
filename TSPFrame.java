package brute_force;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * This program demonstrates how to work with JFrame in Swing.
 * @author www.codejava.net
 *
 */
public class TSPFrame extends JFrame implements ActionListener {
	private File tspFile = null;
	private Map map = null;
	private JLabel status;
	TSP t = new TSP();
	public double minPath = 0.0;
	public ArrayList<Point> minPathList;

	
	public void actionPerformed(ActionEvent e) {
	    System.out.println("Selected: " + e.getActionCommand());
	    switch(e.getActionCommand()){
	    	case "Exit":
	    		int reply = JOptionPane.showConfirmDialog(TSPFrame.this,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					return;
				}
	    		break;
	    	case "Open TSP":
	    		JFileChooser fileChooser = new JFileChooser();
	    		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    		int result = fileChooser.showOpenDialog(this);
	    		if(result == JFileChooser.APPROVE_OPTION){
	    			File fileSelected = fileChooser.getSelectedFile();
	    			tspFile = fileSelected;
	    			t.readPointsFromFile(tspFile);
	    			minPathList = new ArrayList<>();
	    			minPath = 0.0;
	    			status.setText("Processing...");
	    			this.update(getGraphics());
	    			findShortestPathList(minPathList,t.points,null);
	    			map.setRoute(minPathList);
	    			map.update(map.getGraphics());
	    			String path = "";
	    			for(int i = 0; i < minPathList.size(); i++){
	    				path += "("+minPathList.get(i).x + "," + minPathList.get(i).y+")";
	    			}
	    			status.setText("<html>The min path is:"+path + " with a distance of "+minPath+"</html");
	    			this.update(getGraphics());
	    		}
	    		break;
	    	
	    }

	  }
	
	public void findShortestPathList(List<Point> route,List<Point> notInroute, Point q){
		List<Point> p = new ArrayList<Point>(); 
		List<Point> r = new ArrayList<Point>();
		r.addAll(route); 
		p.addAll(notInroute); 
		//we have to make copies or else we'll lose reference to points
		
		if(q != null){// only null when we start out, did this to create a starting position
			p.remove(q);
			r.add(q);
		}
		
		if(p.isEmpty()){ // if all points have been visited,
			r.add(r.get(0)); // the problem requires us to come back to where we started
			//map.setRoute(r);
			//map.update(map.getGraphics());
			double dist = totalDistance(r);
			if(minPath == 0 || dist < minPath){
				map.setRoute(r);
				map.update(map.getGraphics());
				minPath = dist;
				minPathList.clear();
				minPathList.addAll(r);
			}
		}else{
			if(q == null){
				findShortestPathList(r,p,p.get(0));
			}else{
				for(int i = 0; i < p.size(); i++){
					findShortestPathList(r,p,p.get(i)); // recursively call function on each Point in point of the list
				}
			}
		}
	}
	
	public double distance(Point a, Point b){
		return Math.sqrt(Math.pow((b.x - a.x),2) + Math.pow((b.y - a.y),2));
	}
	
	public double totalDistance(List<Point> p){
		double dist = 0.0;
		for(int i = 0 ; i < p.size() -1; i++){
			dist += distance(p.get(i),p.get(i+1)); 
		}
		return dist;
	}
	

	public TSPFrame() {
		super("TSP GUI");
		map = new Map(t.points);
		status = new JLabel("Select a tsp file");
		setLayout(new BorderLayout());
		minPathList = new ArrayList<>();
		add(map,"Center");
		add(status,"South");
	
		// adds menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(this);
		menuFile.add(menuItemExit);
		JMenuItem menuItemOpen = new JMenuItem("Open TSP");
		menuItemOpen.addActionListener(this);
		menuFile.add(menuItemOpen);

		menuBar.add(menuFile);
		
		// adds menu bar to the frame
		setJMenuBar(menuBar);

		// adds window event listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				int reply = JOptionPane.showConfirmDialog(TSPFrame.this,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					return;
				}
			}
		});

		pack();
		// centers on screen
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TSPFrame();
			}
		});
	}
}
