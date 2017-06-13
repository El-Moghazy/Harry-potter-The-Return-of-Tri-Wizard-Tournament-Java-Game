package harrypotter.view;

import harrypotter.controller.Controller;
import harrypotter.model.character.Champion;
import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.Collectible;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.ObstacleCell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class renderTaskThree implements View,GamePlay {
	
	private Timer timer = null;
	private Champion currentChamp;
	private ArrayList<Champion> champions;
	private MyButton [][] JButtonMap;
	private MyButton [] JButtonBag;
	private static int itemsNumbeInBag;
	private MyButton [] JButtonSpells;
	private Cell [][] map;
	private ArrayList<Collectible> inventory;
	private ArrayList<Spell> spells;
	private JFrame mainFrame;
	private JPanel gridPanel;
	private drawingImgPanel spellsPanel;
	private drawingImgPanel infoPanel;
	private drawingImgPanel questPanel;
	private drawingImgPanel bagPanel;
	private JPanel bagItemPanel;
	private JTextArea questInfo;
	private drawingImgPanel questIcon;
	private drawingImgPanel enviromentPanel;
	private JLayeredPane LayeredPane;
	private JLabel PepperPotionNumber;
	private JLabel SkelePotionNumber;
	private JLabel AmortentiaPotionNumber;
	private JLabel SenzuPotionNumber;
	private JLabel ThunderPotionNumber;
	private JLabel FelixPotionNumber;
	private JPanel bagItemNumberPanel;
	private JLayeredPane PepperPotionHolder;
	private JLayeredPane SkelePotionHolder;
	private JLayeredPane AmortentiaPotionHolder;
	private JLayeredPane SenzuPotionHolder;
	private JLayeredPane ThunderPotionHolder;
	private JLayeredPane FelixPotionHolder;
	private JLabel currentPlayerHp;
	private JLabel currentPlayerIp;
	private JPanel currentPlayerInfo;
	
	private Controller listener;
	
	private JPanel shawky;
	
	public renderTaskThree(Champion currentChamp,ArrayList<Champion> champions,Cell [][] map,ArrayList<Collectible> inventory,ArrayList<Spell> spells, Controller listener) throws IOException{
		this.listener = listener;
		this.itemsNumbeInBag = 0;
		this.champions = champions;
		this.currentChamp = currentChamp;
		this.JButtonMap = new MyButton [map.length][map[0].length];
		this.JButtonSpells = new MyButton [3];
		this.JButtonBag = new MyButton [7];
		this.map = map;
		this.inventory = inventory;
		this.mainFrame = new JFrame("Harry Potter Balabizo");
		this.LayeredPane = new JLayeredPane();
		LayeredPane.setLayout(null);
		mainFrame.setUndecorated(true);
		mainFrame.setBackground(new Color(0, 255, 0, 0));
		mainFrame.setLayout(new BorderLayout());
		this.gridPanel = new JPanel();
		gridPanel.setBackground(new Color(0, 255, 0, 0));
		gridPanel.setLayout(new GridLayout(10,10));
		gridPanel.setSize(642 , 646);
		generateMap(this.map);
		readCurrentSpells(spells);
		spellsPanel.setLayout(null);
		spellsPanel.setBackground(new Color(0, 255, 0, 0));
		spellsPanel.setLocation(0, 830);
		gridPanel.setLocation((mainFrame.getToolkit().getScreenSize().height/2) + 90, 424);
		enviromentPanel = new drawingImgPanel("assets/ui/envi.bmp");
		enviromentPanel.setLocation((mainFrame.getToolkit().getScreenSize().height/2) + 30, 50);
		renderPlayerInfo();
		infoPanel.setLayout(null);
		infoPanel.setBackground(new Color(0, 255, 0, 0));
		infoPanel.setLocation(-40, 0);
		renderQuestInfo();
		questPanel.setLayout(null);
		questPanel.setBackground(new Color(0, 255, 0, 0));
		questPanel.setLocation(0, infoPanel.getHeight()- 22);
		renderInfoText();
		readCurrentInventory(inventory);
		bagPanel.setLayout(null);
		bagPanel.setBackground(new Color(0, 255, 0, 0));
		bagPanel.setLocation(1320,0);
		mainFrame.add(LayeredPane,BorderLayout.CENTER);
		LayeredPane.add(enviromentPanel,1,0);
		LayeredPane.add(gridPanel,2,0);
		LayeredPane.add(infoPanel,1,0);
		LayeredPane.add(questPanel,1,0);
		LayeredPane.add(spellsPanel,3,0);
		LayeredPane.add(bagPanel,3,0);
		mainFrame.setSize(mainFrame.getToolkit().getScreenSize());
		mainFrame.setState(JFrame.MAXIMIZED_BOTH);
		//mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	public void renderPlayerInfo() throws IOException {
		if(infoPanel == null)
			infoPanel = new drawingImgPanel("assets/ui/grffplayInfo.png");
		infoPanel.removeAll();
			this.currentPlayerInfo = new JPanel();
			currentPlayerInfo.setLayout(new GridLayout(2,1,1,1));
			currentPlayerInfo.setSize(110, 65);
			currentPlayerInfo.setLocation(310, 180);
			currentPlayerInfo.setBackground(new Color(0, 255, 0, 0));
		int currentHp = ((Wizard) currentChamp).getHp();
		int currentIp = ((Wizard) currentChamp).getIp();
		this.currentPlayerHp = new JLabel();
		Font font = currentPlayerHp.getFont();
		float size = font.getSize() + 30.0f;
		currentPlayerHp.setFont( font.deriveFont(size));
		currentPlayerHp.setText(" "+currentHp);
		currentPlayerHp.setSize(150, 150);
		currentPlayerHp.setLocation(0, 0);
		currentPlayerHp.setBackground(new Color(0, 255, 0, 0));
		Color hpColor = Color.decode("#008000");
		currentPlayerHp.setForeground(hpColor);
		
		this.currentPlayerIp = new JLabel();
		Font font2 = currentPlayerIp.getFont();
		float size2 = font2.getSize() + 20.0f;
		currentPlayerIp.setFont( font2.deriveFont(size2));
		currentPlayerIp.setText("  "+currentIp);
		currentPlayerIp.setSize(150, 150);
		currentPlayerIp.setLocation(0, 50);
		currentPlayerIp.setBackground(new Color(0, 255, 0, 0));
		Color ipColor = Color.decode("#0099FF");
		currentPlayerIp.setForeground(ipColor);
		
		currentPlayerInfo.add(currentPlayerHp);
		currentPlayerInfo.add(currentPlayerIp);
		if(currentChamp instanceof GryffindorWizard){
//			this.infoPanel = new drawingImgPanel("assets/ui/grffplayInfo.png");
			infoPanel.setimg("assets/ui/grffplayInfo.png");
		}
		else if(currentChamp instanceof HufflepuffWizard){
//			this.infoPanel = new drawingImgPanel("assets/ui/huffplayInfo.png");
			infoPanel.setimg("assets/ui/huffplayInfo.png");
		}
        else if(currentChamp instanceof RavenclawWizard){
//        	this.infoPanel = new drawingImgPanel("assets/ui/ravenplayInfo.png"); 
        	infoPanel.setimg("assets/ui/ravenplayInfo.png");
		}
        else if(currentChamp instanceof SlytherinWizard){
//        	this.infoPanel = new drawingImgPanel("assets/ui/slythplayInfo.png");
        	infoPanel.setimg("assets/ui/slythplayInfo.png");
		}
		infoPanel.add(currentPlayerInfo);
		infoPanel.validate();
		infoPanel.repaint();
	}
	public void renderQuestInfo() throws IOException {
			this.questPanel = new drawingImgPanel("assets/ui/questInfo.png");
	}
	public void renderInfoText(){
		GridBagConstraints c = new GridBagConstraints();
		this.questIcon = new drawingImgPanel("assets/icons/questIcon.png");
		questIcon.setBackground(new Color(0, 255, 0, 0));
		questIcon.setLocation(220, 100);
		questPanel.add(questIcon);
		this.questInfo = new JTextArea("In Third task the first champion");
		//questInfo.setColumns(1);
		//questInfo.setRows(100);
		questInfo.setLineWrap(true);
		//questInfo.setWrapStyleWord(true);
		questInfo.append(" reaches the cup cell will win the game!!");
		questInfo.setSize(348, 50);
		questInfo.setEditable(false);
		Font font = questInfo.getFont();
		float size = font.getSize() + 5.0f;
		questInfo.setFont( font.deriveFont(size));
		questInfo.setBackground(new Color(0, 255, 0, 0));
		questInfo.setLocation(100, 250);
		questPanel.add(questInfo);
	}

	@Override
	public void generateMap(Cell[][] map) throws IOException {
		gridPanel.removeAll();
//		Random rand = new Random();
//		int randNumber = 0;
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				/*if(row == 4 && col == 4){
					MyButton newCell = new MyButton("assets/map/taskThree/eggCell.gif","assets/map/taskOne/eggCell - hover.gif","assets/map/taskOne/eggCell.gif",row,col);
					JButtonMap[row][col] = newCell;
					gridPanel.add(newCell);

					newCell.setActionCommand("btn1 "+row+" "+col);
					newCell.addActionListener(listener);
					//System.out.println("Egg");
				}*/
			  if(map[row][col] instanceof ObstacleCell){// CANT READ FILE _______________________________________________________________
//					randNumber = rand.nextInt(5) + 1;
					String pathImg = "assets/map/taskThree/bigRockCell"+1+".bmp";
					String pathImgHover = "assets/map/taskThree/bigRockCell"+1+" - hover.bmp";
					//System.out.println(pathImg);
					//System.out.println(pathImgHover);
					MyButton newCell = new MyButton(pathImg,pathImgHover,pathImg,row,col);
//					MyButton newCell = new MyButton("assets/map/taskOne/bigRockCell1.bmp",
//							"assets/map/taskOne/bigRockCell1 - hover.bmp",
//							"assets/map/taskOne/bigRockCell1.bmp",row,col);
					JButtonMap[row][col] = newCell;
					gridPanel.add(newCell);
					//System.out.println("Obstacle");
					newCell.setActionCommand("btn1 "+row+" "+col);
					newCell.addActionListener(listener);
				}
				else if(map[row][col] instanceof ChampionCell){
					ChampionCell currentTurn = (ChampionCell)map[row][col];
					Champion turnChamp = currentTurn.getChamp();
//					champions.add(turnChamp);
					MyButton newCell = null;
					if(turnChamp instanceof GryffindorWizard){
						newCell = new MyButton("assets/map/taskThree/grffCell.bmp","assets/map/taskThree/grffCell - hover.bmp","assets/map/taskThree/grffCell.bmp",row,col);
					}
                    if(turnChamp instanceof HufflepuffWizard){
                    	newCell = new MyButton("assets/map/taskThree/huffCell.bmp","assets/map/taskThree/huffCell - hover.bmp","assets/map/taskThree/huffCell.bmp",row,col);
					}
                    if(turnChamp instanceof RavenclawWizard){
                    	newCell = new MyButton("assets/map/taskThree/revanCell.bmp","assets/map/taskThree/revanCell - hover.bmp","assets/map/taskThree/revanCell.bmp",row,col);
					} 
                    if(turnChamp instanceof SlytherinWizard){
                    	newCell = new MyButton("assets/map/taskThree/slythCell.bmp","assets/map/taskThree/slythCell - hover.bmp","assets/map/taskThree/slythCell.bmp",row,col);
					}
                    JButtonMap[row][col] = newCell;
					gridPanel.add(newCell);
					//System.out.println("champ");
					newCell.setActionCommand("btn1 "+row+" "+col);
					newCell.addActionListener(listener);
						
				}else{ // CANT READ FILE _______________________________________________________________
//					randNumber = rand.nextInt(3) + 1;
					String pathImg = "assets/map/taskThree/bigGrassCell.bmp";
					String pathImgHover = "assets/map/taskThree/bigGrassCell - hover.bmp";
					MyButton newCell = new MyButton(pathImg,pathImgHover,pathImg,row,col);
//					MyButton newCell = new MyButton("assets/map/taskOne/bigSandCell1.bmp",
//							"assets/map/taskOne/bigSandCell1 - hover.bmp",
//							"assets/map/taskOne/bigSandCell1.bmp",row,col);
					JButtonMap[row][col] = newCell;
					gridPanel.add(newCell);
					//System.out.println("Empty");
					newCell.setActionCommand("btn1 "+row+" "+col);
					newCell.addActionListener(listener);
				}
				
			}
		}
		gridPanel.validate();
		gridPanel.repaint();
//		infoBox("Repaint", "");
	}

	@Override
	public void setFire(ArrayList<Point> points) throws IOException {
		for(int i = 0; i < points.size();i++){
			int row = (points.get(i)).x;
			int col = (points.get(i)).y;
			MyButton Temp = JButtonMap[row][col];
			JButtonMap[row][col] = new  MyButton("assets/map/taskOne/fireCell.gif","assets/map/taskOne/fireCell - hover.gif","assets/map/taskOne/fireCell.gif",row,col);
			gridPanel.repaint();
			timer = new Timer(4000, new ActionListener(){     
	            public void actionPerformed(ActionEvent e) {
	            	JButtonMap[row][col] = Temp;
	            	gridPanel.repaint();
	            }
		});
		
	}
		}

	@Override
	public void damagePlayer(int dmgAmount, Point target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void healingPlayer(int healAmount, Point targer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveObject(Point start, Point end) throws IOException {
		int rowStart = start.x;
		int colStart = start.y;
		int rowEnd = end.x;
		int colEnd = end.y;
		MyButton Temp = JButtonMap[rowStart][colStart];
		Random rand = new Random();
		int randNumber = rand.nextInt(4);
		JButtonMap[rowStart][colStart] = new MyButton("assets/map/taskThree/bigGrassCell.bmp","assets/map/taskThree/bigGrassCell - hover.bmp","assets/map/taskThree/bigGrassCell.bmp",rowStart,colStart);
		JButtonMap[rowEnd][colEnd] = Temp;
		gridPanel.repaint();
		
	}

	@Override
	public void useTrait(Point location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useSpell(Point location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readCurrentInventory(ArrayList<Collectible> inventory) throws IOException {
//		if(bagPanel != null)
//			bagPanel.removeAll();
//		if(bagItemPanel != null)
//			bagItemPanel.removeAll();
//		if(FelixPotionHolder != null)
//			FelixPotionHolder.removeAll();
		if(bagPanel == null){
		 this.bagPanel = new drawingImgPanel("assets/ui/inventory.png");
		}if(bagItemPanel == null){
		 this.bagItemPanel = new JPanel(new GridLayout(3,7,10,10));
		 bagItemPanel.setSize(400, 400);
			bagItemPanel.setBackground(new Color(0, 255, 0, 0));
			//bagItemPanel.setBackground(Color.BLUE);
			bagItemPanel.setLocation(130, 150);
		}
        if(bagItemPanel != null){
        	bagItemPanel.removeAll();
		}
        if(bagPanel != null){
        	bagPanel.removeAll();
        	this.itemsNumbeInBag = 0;
    		this.JButtonBag = new MyButton [7];
		}
		int FFC = 0;
		int AMC = 0;
		int PPC = 0;
		int SGC = 0;
		int SEC = 0;
		int TBC = 0;
		MyButton newSlot = null;
		for(int i = 0;i < inventory.size(); i++){
			Potion turnPotion = (Potion)inventory.get(i);
			String turnPotionName = turnPotion.getName();
			if(turnPotionName.equals("Felix Felicis")){
				if(FFC == 0){
				this.FelixPotionHolder = new JLayeredPane();
				FelixPotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Felix_Felicis.png","assets/potions/Felix_FelicisH.png","assets/potions/Felix_FelicisP.png",0,0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				FelixPotionHolder.add(newSlot,1,0);
				FFC++;
				}else{
				  FFC++;
				}
			}
			else if(turnPotionName.equals("Amortentia")){
				if(AMC == 0){
				this.AmortentiaPotionHolder = new JLayeredPane();
				AmortentiaPotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Amortentia.png","assets/potions/AmortentiaH.png","assets/potions/AmortentiaP.png",0,0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				AmortentiaPotionHolder.add(newSlot,1,0);
				AMC++;
				}else{
				  AMC++;
				}
			}
			else if(turnPotionName.equals("Skele-Gro")){
				if(SGC == 0){
				this.SkelePotionHolder = new JLayeredPane();
				SkelePotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Skele-Gro.png","assets/potions/Skele-GroH.png","assets/potions/Skele-GroP.png",0,0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				SkelePotionHolder.add(newSlot,1,0);
				SGC++;
				}else{
					SGC++;
				}
			}
			else if(turnPotionName.equals("Senzu")){
				if(SEC == 0){
				this.SenzuPotionHolder = new JLayeredPane();
				SenzuPotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Senzu.png","assets/potions/SenzuH.png","assets/potions/SenzuP.png",0,0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				SenzuPotionHolder.add(newSlot,1,0);
				SEC++;
				}else{
					SEC++;
				}
			}
			else if(turnPotionName.equals("Thunder Bolt")){
				if(TBC == 0){
				this.ThunderPotionHolder = new JLayeredPane();
				ThunderPotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Thunder_Bolt.png","assets/potions/Thunder_BoltH.png","assets/potions/Thunder_BoltP.png",0,0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				ThunderPotionHolder.add(newSlot,1,0);
				TBC++;
				}else{
					TBC++;
				}
			}
			else if(turnPotionName.equals("Pepperup Potion")){
				if(PPC == 0){
				this.PepperPotionHolder = new JLayeredPane();
				PepperPotionHolder.setSize(300,400);
				newSlot = new  MyButton("assets/potions/Pepperup_Potion.png","assets/potions/Pepperup_PotionH.png","assets/potions/Pepperup_PotionP.png",0,0);
				newSlot.setLocation(0, 0);
				JButtonBag[i] = newSlot;
				itemsNumbeInBag++;
				PepperPotionHolder.add(newSlot,1,0);
				PPC++;
				}else{
					PPC++;
				}
			}
			newSlot.setActionCommand("p. "+i);
			newSlot.addActionListener(listener);
		}
		if(PPC > 1 || TBC > 1 || SEC > 1 || SGC > 1 || AMC > 1 || FFC > 1){
			this.bagItemNumberPanel = new JPanel(new GridLayout(3,7,10,10));
			if(PPC > 1){
				this.PepperPotionNumber = new JLabel();
				PepperPotionNumber.setSize(30, 30);
				PepperPotionNumber.setLocation(105,85);
				PepperPotionNumber.setText(""+PPC);
				Font font = PepperPotionNumber.getFont();
				float size = font.getSize() + 5.0f;
				PepperPotionNumber.setFont( font.deriveFont(size));
				PepperPotionHolder.add(PepperPotionNumber,2,0);
			}
            if(TBC > 1){
            	this.ThunderPotionNumber = new JLabel();
            	ThunderPotionNumber.setSize(30, 30);
            	ThunderPotionNumber.setLocation(105,85);
            	ThunderPotionNumber.setText(""+TBC);
            	Font font = ThunderPotionNumber.getFont();
				float size = font.getSize() + 5.0f;
				ThunderPotionNumber.setFont( font.deriveFont(size));
            	ThunderPotionHolder.add(ThunderPotionNumber,2,0);
			}
            if(SEC > 1){
            	this.SenzuPotionNumber = new JLabel();
            	SenzuPotionNumber.setSize(30, 30);
            	SenzuPotionNumber.setLocation(105,85);
            	SenzuPotionNumber.setText(""+SEC);
            	Font font = SenzuPotionNumber.getFont();
				float size = font.getSize() + 5.0f;
				SenzuPotionNumber.setFont( font.deriveFont(size));
				SenzuPotionHolder.add(SenzuPotionNumber,2,0);
           }
           if(SGC > 1){
        	  this.SkelePotionNumber = new JLabel();
        	  SkelePotionNumber.setSize(30, 30);
        	  SkelePotionNumber.setLocation(105,85);
        	  SkelePotionNumber.setText(""+SGC);
        	  Font font = SkelePotionNumber.getFont();
			  float size = font.getSize() + 5.0f;
			  SkelePotionNumber.setFont( font.deriveFont(size));
        	  SkelePotionHolder.add(SkelePotionNumber,2,0);
           }
           if(AMC > 1){
        	  this.AmortentiaPotionNumber = new JLabel();
        	  AmortentiaPotionNumber.setSize(30, 30);
        	  AmortentiaPotionNumber.setLocation(105,85);
        	  AmortentiaPotionNumber.setText(""+AMC);
        	  Font font = AmortentiaPotionNumber.getFont();
			  float size = font.getSize() + 5.0f;
			  AmortentiaPotionNumber.setFont( font.deriveFont(size));
			  AmortentiaPotionHolder.add(AmortentiaPotionNumber,2,0);
           }
           if(FFC > 1){
        	   this.FelixPotionNumber = new JLabel();
        	   FelixPotionNumber.setSize(30, 30);
        	   FelixPotionNumber.setLocation(105,85);
        	   FelixPotionNumber.setText(""+FFC);
        	   Font font = FelixPotionNumber.getFont();
 			  float size = font.getSize() + 5.0f;
 			  FelixPotionNumber.setFont( font.deriveFont(size));
 			  FelixPotionHolder.add(FelixPotionNumber,2,0);
           }
			
		}
		newSlot = new  MyButton("assets/potions/pringles.png","assets/potions/pringlesH.png","assets/potions/pringlesP.png",0,0);
		    JButtonBag[JButtonBag.length -1 ] = newSlot;
		    itemsNumbeInBag++;
		    if(PPC >= 1)bagItemPanel.add(PepperPotionHolder);
		    if(FFC >= 1)bagItemPanel.add(FelixPotionHolder);
		    if(AMC >= 1)bagItemPanel.add(AmortentiaPotionHolder);
		    if(SEC >= 1)bagItemPanel.add(SenzuPotionHolder);
			if(SGC >= 1)bagItemPanel.add(SkelePotionHolder);
			if(TBC >= 1)bagItemPanel.add(ThunderPotionHolder);
			bagItemPanel.add(newSlot);
			for(int i = itemsNumbeInBag; i < JButtonBag.length ;i++){
				System.out.println("Hello");
				bagItemPanel.add(new MyButton("assets/potions/emptySlot.png","assets/potions/emptySlotH.png","assets/potions/emptySlotP.png",0,0));
			
		}
	    bagPanel.add(bagItemPanel);
	    bagItemPanel.validate();
	    bagItemPanel.repaint();
		bagPanel.validate();
		bagPanel.repaint();
	}
	public void readCurrentSpells(ArrayList<Spell> spells) throws IOException{
		if(spellsPanel == null)
			this.spellsPanel = new drawingImgPanel("assets/ui/spellsBar.png");
		else
			spellsPanel.removeAll();
		for(int i = 0;i < spells.size(); i++){
			Spell spellTurn = spells.get(i);
			MyButton SpellSlot = null;
			if(spellTurn instanceof DamagingSpell){
				SpellSlot = new MyButton("assets/spells/damageSpell.png","assets/spells/damageSpellH.png","assets/spells/damageSpellU.png",0,0);
				SpellSlot.setLocation((spellsPanel.getWidth()/2) - 240, (spellsPanel.getHeight()/2)- 50);
				spellsPanel.add(SpellSlot);
				JButtonSpells[i] = SpellSlot;
			}
            if(spellTurn instanceof HealingSpell){
            	SpellSlot = new MyButton("assets/spells/healingSpell.png","assets/spells/healingSpellH.png","assets/spells/healingSpellU.png",0,0);
            	SpellSlot.setLocation((spellsPanel.getWidth()/2) - 130, (spellsPanel.getHeight()/2) - 50);
				spellsPanel.add(SpellSlot);
				JButtonSpells[i] = SpellSlot;
				
			}
            if(spellTurn instanceof RelocatingSpell){
            	SpellSlot = new MyButton("assets/spells/relocateSpell.png","assets/spells/relocateSpellH.png","assets/spells/relocateSpellU.png",0,0);
            	SpellSlot.setLocation((spellsPanel.getWidth()/2) -15, (spellsPanel.getHeight()/2) - 50);
				spellsPanel.add(SpellSlot);
				JButtonSpells[i] = SpellSlot;				
			}
            SpellSlot.setActionCommand("spel1 "+i);
            SpellSlot.addActionListener(listener);
		}
		MyButton SpecialTraitSlot = null;
		if(currentChamp instanceof GryffindorWizard){
			SpecialTraitSlot = new MyButton("assets/spells/grffTrait.png","assets/spells/grffTraitH.png","assets/spells/grffTraitU.png",0,0);
			SpecialTraitSlot.setLocation((spellsPanel.getWidth()/2) + 100, (spellsPanel.getHeight()/2) - 50);
			spellsPanel.add(SpecialTraitSlot);
			JButtonSpells[JButtonSpells.length - 1] = SpecialTraitSlot;
		}
		if(currentChamp instanceof HufflepuffWizard){
			SpecialTraitSlot = new MyButton("assets/spells/huffTrait.png","assets/spells/huffTraitH.png","assets/spells/huffTraitU.png",0,0);
			SpecialTraitSlot.setLocation((spellsPanel.getWidth()/2) + 100, (spellsPanel.getHeight()/2) - 50);
			spellsPanel.add(SpecialTraitSlot);
			JButtonSpells[JButtonSpells.length - 1] = SpecialTraitSlot;
		}
		if(currentChamp instanceof RavenclawWizard){
			SpecialTraitSlot = new MyButton("assets/spells/ravenTrait.png","assets/spells/ravenTraitH.png","assets/spells/ravenTraitU.png",0,0);
			SpecialTraitSlot.setLocation((spellsPanel.getWidth()/2) + 100, (spellsPanel.getHeight()/2) - 50);
			spellsPanel.add(SpecialTraitSlot);
			JButtonSpells[JButtonSpells.length - 1] = SpecialTraitSlot;
		}
		if(currentChamp instanceof SlytherinWizard){
			SpecialTraitSlot = new MyButton("assets/spells/slythTrait.png","assets/spells/slythTraitH.png","assets/spells/slythTraitU.png",0,0);
			SpecialTraitSlot.setLocation((spellsPanel.getWidth()/2) + 100, (spellsPanel.getHeight()/2) - 50);
			spellsPanel.add(SpecialTraitSlot);
			JButtonSpells[JButtonSpells.length - 1] = SpecialTraitSlot;
		}
		SpecialTraitSlot.setActionCommand("t1"); 
		SpecialTraitSlot.addActionListener(listener);
		spellsPanel.validate();
		spellsPanel.repaint();
	}

	public MyButton [][] getJButtonMap() {
		return JButtonMap;
	}

	public void setJButtonMap(MyButton [][] jButtonMap) {
		JButtonMap = jButtonMap;
	}

	public ArrayList<Champion> getChampions() {
		return champions;
	}

	public void setInventory(ArrayList<Collectible> inventory) {
		this.inventory = inventory;
	}
	public ArrayList<Collectible> getInventory() {
		return inventory;
	}

	public void setChampions(ArrayList<Champion> champions) {
		this.champions = champions;
	}

	public Cell [][] getMap() {
		return map;
	}

	public void setMap(Cell [][] map) {
		this.map = map;
	}

	public Champion getCurrentChamp() {
		return currentChamp;
	}

	public void setCurrentChamp(Champion currentChamp) throws IOException {
		this.currentChamp = currentChamp;
		readCurrentInventory(((Wizard)currentChamp).getInventory());
		readCurrentSpells(((Wizard)currentChamp).getSpells());
		renderPlayerInfo();
		
	}
	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}
