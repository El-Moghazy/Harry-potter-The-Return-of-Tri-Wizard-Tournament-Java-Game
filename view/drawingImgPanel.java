package harrypotter.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class drawingImgPanel extends JPanel{
	 BufferedImage img; 
	 public drawingImgPanel(String path){
		 try {
				this.img = ImageIO.read(new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 this.setSize(new Dimension(this.getImg().getWidth(), this.getImg().getHeight()));
		 this.setPreferredSize(new Dimension(this.getImg().getWidth(), this.getImg().getHeight()));
		
	 }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, null);
	}
	public BufferedImage getImg(){
		return this.img;
	}
	public void setimg(String path)
	{
		try {
			this.img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
