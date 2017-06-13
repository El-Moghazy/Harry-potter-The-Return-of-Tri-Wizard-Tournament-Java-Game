package harrypotter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


	    @SuppressWarnings("serial")
		class MyButton extends JButton {

	        private Image hoverImg;
	        private Image pressedImg;
	        private Image buttonImg;
            private int x;
            private int y;
	        
	        
	        public MyButton(String text,String buttonImg,String hoverImgPath,String pressedImgPath) throws IOException {
	            super(text);
	            super.setContentAreaFilled(false);
	            this.hoverImg = ImageIO.read(new File(hoverImgPath));
	            this.pressedImg = ImageIO.read(new File(pressedImgPath));
	            this.buttonImg = ImageIO.read(new File(buttonImg));
	            this.setSize(new Dimension(this.buttonImg.getWidth(this),this.buttonImg.getHeight(this)));
	            this.setPreferredSize(new Dimension(this.buttonImg.getWidth(this),this.buttonImg.getHeight(this)));
	            
	        }
	        public MyButton(String buttonImg,String hoverImgPath,String pressedImgPath,int x,int y) throws IOException {
	            super.setContentAreaFilled(false);
	            this.hoverImg = ImageIO.read(new File(hoverImgPath));
	            this.pressedImg = ImageIO.read(new File(pressedImgPath));
	            this.buttonImg = ImageIO.read(new File(buttonImg));
	            this.x = x;
	            this.y = y;
	            this.setBorder(null);
	            this.setSize(new Dimension(this.buttonImg.getWidth(this),this.buttonImg.getHeight(this)));
	            this.setPreferredSize(new Dimension(this.buttonImg.getWidth(this),this.buttonImg.getHeight(this)));
	            setBackground(Color.white);
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            if (getModel().isPressed()) {
	            	this.setSize(new Dimension(this.pressedImg.getWidth(this),this.pressedImg.getHeight(this)));
	            	g.drawImage(this.pressedImg , 0 , 0, getWidth(), getHeight(), this);
	            } else if (getModel().isRollover()) {
	            	this.setSize(new Dimension(this.hoverImg.getWidth(this),this.hoverImg.getHeight(this)));
	            	g.drawImage(this.hoverImg,0,0, getWidth(), getHeight(), this);
	            } else {
	            	this.setSize(new Dimension(this.buttonImg.getWidth(this),this.buttonImg.getHeight(this)));
	                g.drawImage(this.buttonImg,0,0, getWidth() , getHeight() , this);
	            }
	            super.paintComponent(g);
	        }


	       public Image getHoverImg() {
				return hoverImg;
			}
			public void setHoverImg(Image hoverImg) {
				this.hoverImg = hoverImg;
			}
			public Image getPressedImg() {
				return pressedImg;
			}
			public void setPressedImg(Image pressedImg) {
				this.pressedImg = pressedImg;
			}
			public Image getButtonImg() {
				return buttonImg;
			}
			public void setButtonImg(Image buttonImg) {
				this.buttonImg = buttonImg;
			}
		protected void createAndShowGUI() throws IOException {
	        JFrame frame = new JFrame("Test button");
	        frame.setLayout(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        final MyButton btnSave = new MyButton("D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1.bmp",
					"D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1 - hover.bmp",
					"D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1.bmp",0,0);
	       // btnSave.setForeground(new Color(0, 135, 200).brighter());
	        //btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
	        //btnSave.setBorder(null);
	        //btnSave.setBackground(new Color(3, 59, 90));
	        //btnSave.setLocation(0,0);
	        JLabel cc = new  JLabel();
	        cc.setText("Helloeeeeeeeeeeeeeeeeeeeeeeeeefefffffffff");
	        cc.setSize(new Dimension(20,20));
	        cc.setLocation(100, 0);
	        //btnSave.setHoverBackgroundColor(new Color(3, 59, 90).brighter());
	        //btnSave.setPressedBackgroundColor(new Color(0, 135, 200,0));
	        frame.add(cc);
	        frame.add(btnSave);
	        frame.setSize(200, 200);
	        frame.setVisible(true);
	    }

	   public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	MyButton btnSave = new MyButton("D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1.bmp",
								"D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1 - hover.bmp",
								"D:/workspaceGame/HarryPotter-M37/assets/map/taskOne/bigRockCell1.bmp",0,0);
	                	btnSave.createAndShowGUI();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });

	    }

} 
