package harrypotter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.Timer;
import javax.swing.UIManager;

public class gameStratUpFrame extends JFrame {


	ImagePanel panel;

	public ImagePanel getPanel() {
		return panel;
	}


	String animteImg[] = { "strat", "second", "third", "fourth", "fifth", "last" };

	public gameStratUpFrame() {

		UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());

		setBounds(0, 0, 1100, 1000);

		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image image = (new ImageIcon(((new ImageIcon("src/StartUpImg.png")
				.getImage().getScaledInstance(2000, 2000, java.awt.Image.SCALE_SMOOTH))))).getImage();
		panel = new ImagePanel(image);

		panel.setLayout(null);
		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		
		
		setVisible(true);
	}

}
