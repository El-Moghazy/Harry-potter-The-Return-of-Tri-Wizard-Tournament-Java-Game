package harrypotter.controller;

import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.NotEnoughIPException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.exceptions.OutOfRangeException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.Tournament;
import harrypotter.model.world.Direction;
import harrypotter.view.GamePlay;
import harrypotter.view.renderTaskOne;
import harrypotter.view.renderTaskTwo;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Controller implements ActionListener, TournamentListener {
	private GamePlay view;
	// Tournament tournament;
	// renderTaskOne frame;
	private Tournament model;
	private boolean dmgSpellUsed;
	private boolean relocatSpellUsed;
	private ArrayList<Direction> directionList;
	private Spell curSpell;

	// TODO - Initiate the First Task
	public Controller(Tournament t) throws IOException {
		model = t;
		model.setListener(this);
		ArrayList<Champion> champs = t.getFirstTask().getChampions();
		Champion curChamp = t.getFirstTask().getCurrentChamp();
		view = new renderTaskOne(curChamp, champs, t.getFirstTask().getMap(), ((Wizard) curChamp).getInventory(),
				((Wizard) curChamp).getSpells(), this);
	}

	public void actionPerformed(ActionEvent e) {
		// Player makes a move in FRAME 1
		if (e.getActionCommand().length() > 4 && e.getActionCommand().substring(0, 4).equals("btn1")) {
			StringTokenizer st = new StringTokenizer(e.getActionCommand());
			st.nextToken();
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			// Position of current champion
			Point curPos = (Point) ((Wizard) model.getFirstTask().getCurrentChamp()).getLocation().clone();
			// Target Cell to move to
			Point target = new Point(x, y);
			// Directon of the clicked button with respect to current location
			Direction d = getDirection(curPos, target);
			if (dmgSpellUsed) {
				try {
					model.getFirstTask().castDamagingSpell((DamagingSpell) curSpell, d);
					((renderTaskOne) view).setChampions(model.getFirstTask().getChampions());
					((renderTaskOne) view).generateMap(model.getFirstTask().getMap());
					((renderTaskOne) view).setCurrentChamp(model.getFirstTask().getCurrentChamp());
				} catch (Exception e1) {
					infoBox(e1.getMessage(), "Invalid Action");
					dmgSpellUsed = false;
					return;
				}
				// TODO update frame
				dmgSpellUsed = false;
			} else if (relocatSpellUsed) {
				if (directionList == null) {
					infoBox("Direction list was empty\nCheck log files for more info", "Error");
					return;
				}
				directionList.add(d);
				// User makes second move and casts the spell
				if (directionList.size() == 2) {
					if (target.y != curPos.y && target.x != curPos.x) {
						infoBox("Selected cell must be in the same tile", "Invalid Action");
						dmgSpellUsed = false;
						relocatSpellUsed = false;
						directionList = null;
						return;
					}
					int range = 1000;
					if (target.x == curPos.x)
						range = Math.abs(target.y - curPos.y);
					else
						range = Math.abs(target.x - curPos.x);
					try {
						model.getFirstTask().castRelocatingSpell((RelocatingSpell) curSpell, directionList.get(0), d,
								range);
						((renderTaskOne) view).setChampions(model.getFirstTask().getChampions());
						((renderTaskOne) view).generateMap(model.getFirstTask().getMap());
						((renderTaskOne) view).setCurrentChamp(model.getFirstTask().getCurrentChamp());
					} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
							| InvalidTargetCellException | OutOfRangeException | IOException e1) {
						infoBox("Exception\n" + e1.getMessage(), "Invalid Action");
					}
					dmgSpellUsed = false;
					relocatSpellUsed = false;
					directionList = null;
				}
				return;
			} else // Move is made
			{
				if (isValidMove(curPos, target)) {
					
					if (moveChampionft(d, target))
						try {
							((renderTaskOne) view).setChampions(model.getFirstTask().getChampions());
							((renderTaskOne) view).generateMap(model.getFirstTask().getMap());
							((renderTaskOne) view).setCurrentChamp(model.getFirstTask().getCurrentChamp());

							infoBox("SUCCESSS!!!!!!!", "");

						} catch (IOException e1) {
							infoBox(d + "", "");
						}
				} else {
					infoBox("Invalid Move\n" + curPos + "\n" + target, "");
				}
			}
			return;
		}
		
		
		

		if (e.getActionCommand().length() > 4 && e.getActionCommand().substring(0, 4).equals("btn2")) {
			StringTokenizer st = new StringTokenizer(e.getActionCommand());
			st.nextToken();
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			// Position of current champion
			Point curPos = (Point) ((Wizard) model.getSecondTask().getCurrentChamp()).getLocation().clone();
			// Target Cell to move to
			Point target = new Point(x, y);
			// Directon of the clicked button with respect to current location
			Direction d = getDirection(curPos, target);
			if (dmgSpellUsed) {
				try {
					model.getSecondTask().castDamagingSpell((DamagingSpell) curSpell, d);
					((renderTaskOne) view).setChampions(model.getSecondTask().getChampions());
					((renderTaskOne) view).generateMap(model.getSecondTask().getMap());
					((renderTaskOne) view).setCurrentChamp(model.getSecondTask().getCurrentChamp());
				} catch (Exception e1) {
					infoBox(e1.getMessage(), "Invalid Action");
					dmgSpellUsed = false;
					return;
				}
				// TODO update frame
				dmgSpellUsed = false;
			} else if (relocatSpellUsed) {
				if (directionList == null) {
					infoBox("Direction list was empty\nCheck log files for more info", "Error");
					return;
				}
				directionList.add(d);
				// User makes second move and casts the spell
				if (directionList.size() == 2) {
					if (target.y != curPos.y && target.x != curPos.x) {
						infoBox("Selected cell must be in the same tile", "Invalid Action");
						dmgSpellUsed = false;
						relocatSpellUsed = false;
						directionList = null;
						return;
					}
					int range = 1000;
					if (target.x == curPos.x)
						range = Math.abs(target.y - curPos.y);
					else
						range = Math.abs(target.x - curPos.x);
					try {
						model.getSecondTask().castRelocatingSpell((RelocatingSpell) curSpell, directionList.get(0), d,
								range);
						((renderTaskOne) view).setChampions(model.getSecondTask().getChampions());
						((renderTaskOne) view).generateMap(model.getSecondTask().getMap());
						((renderTaskOne) view).setCurrentChamp(model.getSecondTask().getCurrentChamp());
					} catch (InCooldownException | NotEnoughIPException | OutOfBordersException
							| InvalidTargetCellException | OutOfRangeException | IOException e1) {
						infoBox("Exception\n" + e1.getMessage(), "Invalid Action");
					}
					dmgSpellUsed = false;
					relocatSpellUsed = false;
					directionList = null;
				}
				return;
			} else // Move is made
			{
				if (isValidMove(curPos, target)) {
					if (moveChampionst(d, target))
						try {
							((renderTaskOne) view).setChampions(model.getSecondTask().getChampions());
							((renderTaskOne) view).generateMap(model.getSecondTask().getMap());
							((renderTaskOne) view).setCurrentChamp(model.getSecondTask().getCurrentChamp());

							infoBox("SUCCESSS!!!!!!!", "");

						} catch (IOException e1) {
							infoBox(d + "", "");
						}
				} else {
					infoBox("Invalid Move\n" + curPos + "\n" + target, "");
				}
			}
			return;
		}

		// TODO Update ActionCommand to use Spells in Frame 1
		if (e.getActionCommand().length() > 6 && e.getActionCommand().substring(0, 5).equals("spel1")) {
			StringTokenizer st = new StringTokenizer(e.getActionCommand());
			st.nextToken();
			int ind = Integer.parseInt(st.nextToken());
			Spell usedSpell = ((Wizard) model.getFirstTask().getCurrentChamp()).getSpells().get(ind);
			infoBox("Spell Used\n" + usedSpell + "\n" + usedSpell.getClass(), "");
			try {
				if (usedSpell instanceof HealingSpell) {
					try {
						model.getFirstTask().castHealingSpell((HealingSpell) usedSpell);
						((renderTaskOne) view).setChampions(model.getFirstTask().getChampions());
						((renderTaskOne) view).generateMap(model.getFirstTask().getMap());
						((renderTaskOne) view).setCurrentChamp(model.getFirstTask().getCurrentChamp());
					} catch (Exception ee) {
						infoBox(ee.getMessage(), "Invalid Action");
						return;
					}
					// TODO update map: give the frame the 2D map and the
					// champions list and set the current champ
				} else if (usedSpell instanceof DamagingSpell) {
					relocatSpellUsed = false;
					directionList = new ArrayList<Direction>();
					dmgSpellUsed = true;
					curSpell = usedSpell;
				} else if (usedSpell instanceof RelocatingSpell) {
					curSpell = usedSpell;
					dmgSpellUsed = false;
					relocatSpellUsed = true;
					directionList = new ArrayList<>();
				}
			} catch (Exception ee) {

			}
			return;
		}

		if (e.getActionCommand().length() > 6 && e.getActionCommand().substring(0, 5).equals("spel2")) {
			StringTokenizer st = new StringTokenizer(e.getActionCommand());
			st.nextToken();
			int ind = Integer.parseInt(st.nextToken());
			Spell usedSpell = ((Wizard) model.getSecondTask().getCurrentChamp()).getSpells().get(ind);
			infoBox("Spell Used\n" + usedSpell + "\n" + usedSpell.getClass(), "");
			try {
				if (usedSpell instanceof HealingSpell) {
					try {
						model.getSecondTask().castHealingSpell((HealingSpell) usedSpell);
						((renderTaskTwo) view).setChampions(model.getSecondTask().getChampions());
						((renderTaskTwo) view).generateMap(model.getSecondTask().getMap());
						((renderTaskTwo) view).setCurrentChamp(model.getSecondTask().getCurrentChamp());
					} catch (Exception ee) {
						infoBox(ee.getMessage(), "Invalid Action");
						return;
					}
					// TODO update map: give the frame the 2D map and the
					// champions list and set the current champ
				} else if (usedSpell instanceof DamagingSpell) {
					relocatSpellUsed = false;
					directionList = new ArrayList<Direction>();
					dmgSpellUsed = true;
					curSpell = usedSpell;
				} else if (usedSpell instanceof RelocatingSpell) {
					curSpell = usedSpell;
					dmgSpellUsed = false;
					relocatSpellUsed = true;
					directionList = new ArrayList<>();
				}
			} catch (Exception ee) {

			}
			return;
		}

		// TODO Update ActionCommand to use Potions in Frame 1
		if(e.getActionCommand().charAt(0)=='p')
			System.out.println("-----------------------------------------------------------");
		if (e.getActionCommand().length() > 3 && e.getActionCommand().substring(0,2).equals("p1")) {
//			String str = "";
//			Pattern pattern = Pattern.compile("[1-9]+\\s[1-9]");
//			Matcher matcher = pattern.matcher(e.getActionCommand());
//			matcher.find();
//			str = matcher.group();
			StringTokenizer st = new StringTokenizer(e.getActionCommand());
			st.nextToken();
			int index = Integer.parseInt(st.nextToken());
			Wizard current = (Wizard) (model.getFirstTask().getCurrentChamp());
			model.getFirstTask().usePotion((Potion) current.getInventory().get(index));
			System.out.println("---------------------------------------------------------------------");
//			char task = st.nextToken().charAt(1);
//			System.out.println(task);
//			char index = str.charAt(2);
//			switch (task) {
//			case '1':
//				System.out.println("Here");
//				Wizard current = (Wizard) (model.getFirstTask().getCurrentChamp());
//				model.getFirstTask().usePotion((Potion) current.getInventory().get(index));
//				break;
//			case '2':
//				Wizard current2 = (Wizard) (model.getSecondTask().getCurrentChamp());
//				model.getSecondTask().usePotion((Potion) current2.getInventory().get(index));
//				break;
//			case '3':
//				Wizard current3 = (Wizard) (model.getThirdTask().getCurrentChamp());
//				model.getThirdTask().usePotion((Potion) current3.getInventory().get(index));
//				break;
//			}
//			return;

			// TODO update the frame
		}

		// TODO ActionCommand to use Trait
		if (e.getActionCommand().matches("r+[1-9]")) {
			String str = "";
			str = e.getActionCommand();
			char task = str.charAt(1);
			switch (task) {
			case '1':
				Wizard current = (Wizard) (model.getFirstTask().getCurrentChamp());
				current.setHp(0);
				try {
					model.getFirstTask().finalizeAction();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case '2':
				Wizard current2 = (Wizard) (model.getSecondTask().getCurrentChamp());
				current2.setHp(0);
				try {
					model.getSecondTask().finalizeAction();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				break;
			case '3':
				Wizard current3 = (Wizard) (model.getThirdTask().getCurrentChamp());
				current3.setHp(0);
				try {
					model.getThirdTask().finalizeAction();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;
				break;

			}

			return;
		}

		if (e.getActionCommand().matches("t+[1-9]")) {
			String str = "";
			str = e.getActionCommand();
			char task = str.charAt(1);
			switch (task) {
			case '1':
				infoBox("Trait Used", "");
				model.getFirstTask().setTraitActivated(true);
				break;
			case '2':
				infoBox("Trait Used", "");
				model.getSecondTask().setTraitActivated(true);
				break;
			case '3':
				infoBox("Trait Used", "");
				model.getThirdTask().setTraitActivated(true);
				break;

			}

			return;
		}

	}

	// Checks if a move is a valid one
	private boolean isValidMove(Point curPos, Point targetPos) {
		if (targetPos.x > 9 || targetPos.x < 0 || targetPos.y > 9 || targetPos.y < 0)
			return false;
		return (Math.abs(targetPos.x - curPos.x) == 0 && Math.abs(targetPos.y - curPos.y) == 1)
				|| (Math.abs(targetPos.x - curPos.x) == 1 && Math.abs(targetPos.y - curPos.y) == 0);
	}

	// Returns the direction from curPos to the targetPos
	// TODO Revise the directions in this method COULD BE BUGGY !!!!
	private Direction getDirection(Point curPos, Point targetPos) {
		if (targetPos.y > curPos.y)
			return Direction.RIGHT;
		if (targetPos.y < curPos.y)
			return Direction.LEFT;
		if (targetPos.x > curPos.x)
			return Direction.BACKWARD;
		return Direction.FORWARD;
	}

	// Moves Champion to the following direction
	private boolean moveChampionft(Direction direction, Point target) {
		switch (direction) {
		case FORWARD:
			try {
				model.getFirstTask().moveForward();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case BACKWARD:
			try {
				model.getFirstTask().moveBackward();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case LEFT:
			try {
				model.getFirstTask().moveLeft();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case RIGHT:
			try {
				model.getFirstTask().moveRight();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		}
		return true;
	}
	
	private boolean moveChampionst(Direction direction, Point target) {
		switch (direction) {
		case FORWARD:
			try {
				model.getSecondTask().moveForward();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case BACKWARD:
			try {
				model.getSecondTask().moveBackward();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case LEFT:
			try {
				model.getSecondTask().moveLeft();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		case RIGHT:
			try {
				model.getSecondTask().moveRight();
			} catch (OutOfBordersException | InvalidTargetCellException | IOException e1) {
				infoBox(direction + "\n" + target + "\nMoveChamp\n" + e1.getMessage(), "");
				return false;
			}
			break;
		}
		return true;
	}

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void onFinishingFirst(int n) {
		if (n == 1) {
			infoBox("Task1 is Over", "");
			((renderTaskOne) view).getMainFrame().dispose();
			ArrayList<Champion> champs = model.getSecondTask().getChampions();
			Champion curChamp = model.getSecondTask().getCurrentChamp();
			try {
				renderTaskTwo task2 = new renderTaskTwo(curChamp, champs, model.getSecondTask().getMap(),
						((Wizard) curChamp).getInventory(), ((Wizard) curChamp).getSpells(), this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			((renderTaskOne) view).getMainFrame().dispose();
			infoBox("Game Over", "");
		}
	}

}
