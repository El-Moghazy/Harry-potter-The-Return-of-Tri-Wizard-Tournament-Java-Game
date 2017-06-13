package harrypotter.view;

import harrypotter.model.character.Champion;
import harrypotter.model.magic.Collectible;
import harrypotter.model.magic.Spell;
import harrypotter.model.world.Cell;

import java.awt.GridLayout;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class FirstTaskFrame extends JFrame implements View,GamePlay{
	private static final long serialVersionUID = 1L;
	private GridLayout layOut;
	
	private ArrayList<Champion> champs; 
	
	public FirstTaskFrame(ArrayList<Champion> champs) 
	{
		this.champs = champs;
		setTitle("First Task");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50,500, 600);
		layOut = new GridLayout();
		this.setLayout(layOut);
		
		
	}
	@Override
	public void generateMap(Cell[][] map) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFire(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		
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
	public void moveObject(Point start, Point end) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void useTrait(Point location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void useSpell(Point location) {
		// TODO Auto-generated method stub
		
	}
	public void setCurrentChampion(Champion champ) {
		// TODO Auto-generated method stub
		
	}
	public void victoryChampion() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void readCurrentInventory(ArrayList<Collectible> inventory) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void readCurrentSpells(ArrayList<Spell> spells) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
