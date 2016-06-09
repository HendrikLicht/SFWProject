import java.awt.GridLayout;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerUI extends JFrame
{
	GridLayout layout = new GridLayout(0,1);
	ImageIcon yellowIco = new ImageIcon("src/graphics/yellow.png");
	ImageIcon greenIco = new ImageIcon("src/graphics/green.png");
	JLabel stateLbl = new JLabel();
	JLabel adressLbl = new JLabel();
	JLabel portLbl = new JLabel();
	JPanel panel = new JPanel();
	
	public ServerUI()
	{
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("SFW-Server Alpha 0.3");
		layout.setHgap(5);
		layout.setVgap(5);
		this.add(panel);
		this.panel.add(stateLbl);
		this.panel.add(adressLbl);
		this.panel.add(portLbl);
		this.pack();
		this.setSize(512, 128);
	}
	
	public void setAdressLbl(InetAddress Adress, int Port)
	{
		this.adressLbl.setText("IP:" + Adress);
		this.portLbl.setText("Port: " + Port);
		this.repaint();
	}
	
	public void yellow()
	{
		this.stateLbl.setIcon(yellowIco);
		this.repaint();
	}
	
	public void green()
	{
		this.stateLbl.setIcon(greenIco);
		this.repaint();
	}
}
