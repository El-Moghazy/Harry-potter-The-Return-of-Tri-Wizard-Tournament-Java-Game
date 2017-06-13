
package harrypotter.view;

import harrypotter.model.magic.Collectible;
import harrypotter.model.magic.Spell;
import harrypotter.model.world.Cell;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public interface GamePlay {
    
    public void generateMap(Cell[][] map) throws IOException;
    
    public void setFire(ArrayList<Point> points) throws IOException;
    
    public void damagePlayer(int dmgAmount,Point target);
    
    public void healingPlayer(int healAmount, Point targer);
    
    public void moveObject(Point start,Point end) throws IOException;
    
    public void useTrait(Point location);
    
    public void useSpell(Point location);
    
    public void readCurrentInventory(ArrayList<Collectible> inventory) throws IOException;
    
    public void readCurrentSpells(ArrayList<Spell> spells) throws IOException;
    
}