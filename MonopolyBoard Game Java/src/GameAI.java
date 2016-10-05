
import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import monopolyUML.BrownColorGroup;
import monopolyUML.Cell;
import monopolyUML.DarkBlueColorGroup;
import monopolyUML.GreenColorGroup;
import monopolyUML.LightBlueColorGroup;
import monopolyUML.OrangeColorGroup;
import monopolyUML.PinkColorGroup;
import monopolyUML.Player;
import monopolyUML.PropertyCell;
import monopolyUML.RailRoadCell;
import monopolyUML.RedColorGroup;
import monopolyUML.UtilityCell;
import monopolyUML.YellowColorGroup;

/*
 * 
 */
/**
 *
 * @author Abdulsalam M.T Umar
 */
public class GameAI {
    //dice odds

    JFrame frame;
    JTextArea log;
    String ColorGroups[] = {"BrownColorGroup", "LightBlueColorGroup", "PinkColorGroup", "OrangeColorGroup", "RedColorGroup", "YellowColorGroup", "GreenColorGroup", "DarkBlueColorGroup"};
    String Utilities[] = {"Electric", "WaterWorks"};
    String Railroad[] = {"Liverpool", "Marylebone", "Fenchurch", "KingCross"};

    public GameAI() {
        frame = new JFrame();
        log = new JTextArea();
        log.setBackground(Color.BLACK);
        log.setForeground(Color.RED);
        log.setLineWrap(true);

        frame.add(new JScrollPane(log));
        frame.setSize(500, 500);
        //frame.setVisible(true);
    }

    public double getDiceODD(int dice) {
        if (dice == 2 || dice == 12) {
            return 1;
        } else if (dice == 3 || dice == 11) {
            return 2;
        } else if (dice == 4 || dice == 10) {
            return 3;
        } else if (dice == 5 || dice == 9) {
            return 4;
        } else if (dice == 6 || dice == 8) {
            return 5;
        } else if (dice == 7) {
            return 6;
        } else {
            return 0;
        }
    }

    //probability of landing on an owned property when dice is rolled fron from current position
    public double probOwned(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (Cell[no] instanceof PropertyCell) {
                if (Cell[no] != null && Cell[no].getOwner() != player) {
                    x++;
                }
            }
        }
        return x / 11;
    }

    //probability of landing on an unowned property when dice is rolled fron current position
    public double probUnOwned(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (Cell[no] instanceof PropertyCell) {
                if (Cell[no] == null) {
                    x++;
                }
            }
        }
        return x / 11;
    }

    //propbability of landing on community chest cell
    public double probCChest(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 2 || no == 17 || no == 33) {
                x++;
            }
        }
        return x / 11;
    }

    //propbability of landing on chance cell
    public double probChance(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 7 || no == 22 || no == 36) {
                x++;
            }
        }
        return x / 11;
    }

    //probalitity of landing on go cell
    public double probGo(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 0) {
                x++;
            }
        }
        return x / 11;
    }

    //probalitity of landing on jail cell
    public double probJail(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 10) {
                x++;
            }
        }
        return x / 11;
    }

    //probalitity of landing on freeparking
    public double probFreeParking(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 20) {
                x++;
            }
        }
        return x / 11;
    }

    //probalitity of landing on go to jail cell
    public double probGoToJail(Player player, Cell Cell[]) {
        int x = 0;
        for (int i = 2; i <= 12; i++) {
            int no = player.getPosition() + i;
            if (no >= 40) {
                i = i - 40;
            }
            if (no == 30) {
                x++;
            }
        }
        return x / 11;
    }

    //probability of available properties
    public double probAvailableProperty(Cell Cell[]) {
        int x = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() == null) {
                    x++;
                }
                x++;
            }
        }
        return x / 28;
    }

    //hit frequence for colorgroup
    public int hitRate(Cell cell) {
        if (cell instanceof BrownColorGroup) {
            return 1;
        } else if (cell instanceof LightBlueColorGroup) {
            return 4;
        } else if (cell instanceof PinkColorGroup) {
            return 5;
        } else if (cell instanceof OrangeColorGroup) {
            return 9;
        } else if (cell instanceof RedColorGroup) {
            return 8;
        } else if (cell instanceof YellowColorGroup) {
            return 7;
        } else if (cell instanceof GreenColorGroup) {
            return 6;
        } else if (cell instanceof DarkBlueColorGroup) {
            return 2;
        } else if (cell instanceof UtilityCell) {
            return 3;
        } else if (cell instanceof RailRoadCell) {
            return 10;
        } else {
            return 0;
        }
    }

    //property buy desicion with priority
    public boolean buyDecision(Player player, Cell cell[]) {
        //if player has enough money
        if (player.getMoney() > cell[player.getPosition()].getCost()) {
            //if it is an orange color group
            if (cell[player.getPosition()] instanceof OrangeColorGroup) {
                return true;
            }//end logic
            //if no other player owns a property in this color group
            int nocg = 0;
            for (int i = 0; i < cell.length; i++) {
                if (cell[i] instanceof PropertyCell) {
                    if (cell[player.getPosition()].getColorGroup().equals(cell[i].getColorGroup())) {
                        if (cell[i].getOwner() == null) {
                            nocg++;
                            if (nocg > 2 | nocg == 2) {
                                return true;
                            }
                        }
                    }
                }
            }//end logic
            //if it gives you a second or third property of its group.
            int cg = 0;
            for (int i = 0; i < player.getProperties().length; i++) {
                if (cell[player.getPosition()].getColorGroup().equals(player.getProperties()[i].getColorGroup())) {
                    cg++;
                }
                if (cg > 0) {
                    return true;
                }
            }


            return true;//buy when have money
        }//end enough money check

        return false;
    }

    //probality of paying rent
    public double rentEstimate(Cell Cell[], Player player) {
        int x = 0;
        for (int i = 0; i < 40; i++) {

            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i] != null && Cell[i].getOwner() != player && !Cell[i].isMortgaged()) {
                    x++;
                }
            }
        }
        return x / 7;
    }

    //probability of development  btwn jail cell
    public double probDevtBtwnJail(Cell Cell[], Player player) {
        int x = 0;
        for (int i = 11; i <= 29; i++) {

            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i] != null && Cell[i].getOwner() != player) {
                    if (Cell[i].getNumberOfHouses() > 0) {
                        x++;
                    }
                }
            }
        }
        return x / 11;
    }

    //level of development on the monopoly board
    public double probDevt(Cell Cell[], Player player) {
        int x = 0;
        for (int i = 0; i < Cell.length; i++) {

            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i] != null && Cell[i].getOwner() != player) {
                    if (Cell[i].getNumberOfHouses() > 0) {
                        x++;
                    }
                }
            }
        }
        return x / 28;
    }

    //decision to make when in jail
    public int jailDecision(Player player, Cell Cell[]) {
        if (probAvailableProperty(Cell) > 0.3) {//when most of the properties are onwmed
            if (probDevtBtwnJail(Cell, player) > 0.5) {
                return 1;
            }
            if (player.hasChanceJailCard() || player.hasCommunityJailCard()) {
                return 3;
            } else {
                if (player.getMoney() > 50) {
                    return 2;
                }
            }
        } else if (probAvailableProperty(Cell) <= 0.3) {
            if (probDevtBtwnJail(Cell, player) < 0.5) {
                if (player.hasChanceJailCard() || player.hasCommunityJailCard()) {
                    return 3;
                } else {
                    if (player.getMoney() > 50) {
                        return 2;
                    }
                }
            }
            return 1;
        }
        return 1;
    }

    //CHECKS FOR PLAYER SPENDING POWER
    public int spendingPower(Cell Cell[], Player player) {
        if (probDevt(Cell, player) > 0.5) {
            if (player.getMoney() > 400) {
                return player.getMoney() - 400;
            }
        } else if (probDevt(Cell, player) < 0.5) {
            if (player.getMoney() > 200) {
                return player.getMoney() - 200;
            }
        }
        return 0;
    }


    //CHECKS WHETHER A PLAYER HAS ANY MONOPOLY
    public boolean hasPlayerMonopoly(Cell Cell[], Player player) {
        int count = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() == player) {
                    if (Cell[i] instanceof BrownColorGroup) {
                        count++;
                        if (count == 2) {
                            return true;
                        }
                    } else if (Cell[i] instanceof LightBlueColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof PinkColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof OrangeColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof RedColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof YellowColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof GreenColorGroup) {
                        count++;
                    } else if (Cell[i] instanceof DarkBlueColorGroup) {
                        count++;
                        if (count == 2) {
                            return true;
                        }
                    }
                    if (count == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //CHECKS WHETHER TO BUILD OR NOT
    public boolean toBuild(Cell Cell[], Cell prop, Player player) {
        if (prop instanceof PropertyCell) {
            if (hasPlayerMonopoly(Cell, player)) {
                if (spendingPower(Cell, player) > prop.getCostOfHouse()) {
                    return true;
                }
            }
        }
        return false;
    }

    //CHECKS WHETHER A PLAYER HAS ANY MONOPOLY
    //RETURNS COLOR GROUPS WHICH A PLAYER OWNS A PROPERTY
    public List playerPropertyInColorGroup(Cell Cell[], Player player) {
        List s = new LinkedList();
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i].getOwner() == player) {
                if (Cell[i] instanceof BrownColorGroup) {
                    s.add("BrownColorGroup");
                } else if (Cell[i] instanceof LightBlueColorGroup) {
                    s.add("LightBlueColorGroup");
                } else if (Cell[i] instanceof PinkColorGroup) {
                    s.add("PinkColorGroup");
                } else if (Cell[i] instanceof OrangeColorGroup) {
                    s.add("OrangeColorGroup");
                } else if (Cell[i] instanceof RedColorGroup) {
                    s.add("RedColorGroup");
                } else if (Cell[i] instanceof YellowColorGroup) {
                    s.add("YellowColorGroup");
                } else if (Cell[i] instanceof GreenColorGroup) {
                    s.add("GreenColorGroup");
                } else if (Cell[i] instanceof DarkBlueColorGroup) {
                    s.add("DarkBlueColorGroup");
                }
            }
        }
        //log(player.getName() + " has properties in these color groups: " + s);
        return s;
    }

    // color group which are sold
    public Set soldColorGroups(Cell Cell[]) {
        Set s = new HashSet();
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() != null) {
                    if (Cell[i] instanceof BrownColorGroup) {
                        a++;
                        if (a == 2) {
                            s.add("BrownColorGroup");
                        }
                    } else if (Cell[i] instanceof LightBlueColorGroup) {
                        b++;
                        if (b == 3) {
                            s.add("LightBlueColorGroup");
                        }
                    } else if (Cell[i] instanceof PinkColorGroup) {
                        c++;
                        if (c == 3) {
                            s.add("PinkColorGroup");
                        }
                    } else if (Cell[i] instanceof OrangeColorGroup) {
                        d++;
                        if (d == 3) {
                            s.add("OrangeColorGroup");
                        }
                    } else if (Cell[i] instanceof RedColorGroup) {
                        e++;
                        if (e == 3) {
                            s.add("RedColorGroup");
                        }
                    } else if (Cell[i] instanceof YellowColorGroup) {
                        f++;
                        if (f == 3) {
                            s.add("YellowColorGroup");
                        }
                    } else if (Cell[i] instanceof GreenColorGroup) {
                        g++;
                        if (g == 3) {
                            s.add("GreenColorGroup");
                        }
                    } else if (Cell[i] instanceof DarkBlueColorGroup) {
                        h++;
                        if (h == 2) {
                            s.add("DarkBlueColorGroup");
                        }
                    }
                }
            }
        }
        return s;
    }

    //possible trade properties
    public Vector tradeProps(Cell Cell[], Player owner) {
        Set ax = this.soldColorGroups(Cell);
        List bx = this.playerPropertyInColorGroup(Cell, owner);
        List s = new LinkedList();
        Object cs[] = bx.toArray();
        String cellcolor = "";
        Vector v = new Vector();
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
        for (int i = 0; i < cs.length; i++) {
            if (ax.contains(cs[i])) {
                s.add(cs[i]);
            }
        }
        Object st[] = s.toArray();
        for (int j = 0; j < st.length; j++) {
            if (st[j].equals("OrangeColorGroup")) {
                cellcolor = "Orange";
                a++;
            } else if (st[j].equals("RedColorGroup")) {
                cellcolor = "Red";
                b++;
            } else if (st[j].equals("YellowColorGroup")) {
                cellcolor = "Yellow";
                c++;
            } else if (st[j].equals("GreenColorGroup")) {
                cellcolor = "Green";
                d++;
            } else if (st[j].equals("PinkColorGroup")) {
                cellcolor = "Pink";
                e++;
            } else if (st[j].equals("LightBlueColorGroup")) {
                cellcolor = "Light Blue";
                f++;
            } else if (st[j].equals("DarkBlueColorGroup")) {
                cellcolor = "Dark Blue";
                g++;
            } else if (st[j].equals("BrownColorGroup")) {
                cellcolor = "Brown";
                h++;
            }
        }

        if (a == 2) {
            v.add("Orange");
        }
        if (b == 2) {
            v.add("Red");
        }
        if (c == 2) {
            v.add("Yellow");
        }
        if (d == 2) {
            v.add("Green");
        }
        if (e == 2) {
            v.add("Pink");
        }
        if (f == 2) {
            v.add("Ligth Blue");
        }
        if (g == 1) {
            v.add("Dark Blue");
        }
        if (h == 1) {
            v.add("Brown");
        }
        Vector end = new Vector();
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (v.contains(Cell[i].getColorGroup())) {
                    if (Cell[i].getOwner() != owner) {
                        end.add(Cell[i]);
                    }
                }
            }
        }

        log("????--->" + bx);
        log("zzzz---> " + ax);
        log("@@@---> " + s);
        log("trade peoperites: " + end);
        log(":::::---> " + v.toString());
        return end;
    }

    //best property to trade
    public Vector propertyToTrade(Cell Cell[], Player owner) {
        Cell c = null;
        int count = 0;
        int money = 0;
        int demand = 0;
        Cell offer = null;
        Vector v = this.tradeProps(Cell, owner);
        for (int i = 0; i < v.size(); i++) {
            Cell x = (Cell) v.elementAt(i);
            if (this.hitRate(x) > count) {
                if (this.spendingPower(Cell, owner) + ((int) this.spendingPower(Cell, owner) * 0.15) > x.getCost()) {
                    c = x;
                    money = x.getCost() + (int) (x.getCost() * 0.15);
                } else if (!this.offerProperties(Cell, owner).isEmpty()) {
                    Vector vc = this.offerProperties(Cell, owner);
                    if (this.opponentOwnUtitlity(Cell, x.getOwner())) {
                        for (int j = 0; j < vc.size(); j++) {
                            Cell cd = (Cell) vc.elementAt(j);
                            if (cd instanceof UtilityCell) {
                                int mn = cd.getCost() + this.spendingPower(Cell, owner);
                                if (mn > x.getCost()) {
                                    int g = x.getCost();
                                    int r = g - cd.getCost();
                                    if (r > 0) {
                                        money = r;
                                    } else {
                                        demand = r * (-1);
                                    }
                                    c = x;
                                    offer = cd;
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < vc.size(); j++) {
                            Cell cd = (Cell) vc.elementAt(j);
                            int mn = cd.getCost() + this.spendingPower(Cell, owner);
                            if (mn > x.getCost()) {
                                int g = x.getCost();
                                int r = g - cd.getCost();
                                if (r > 0) {
                                    money = r;
                                } else {
                                    demand = r * (-1);
                                }
                                c = x;
                                offer = cd;
                            }
                        }
                    }
                }
            }
            count = this.hitRate(c);
        }

        Vector last = new Vector();
        last.add(c);
        last.add(money);
        last.add(offer);
        last.add(demand);
        return last;
    }

    public Vector offerProperties(Cell Cell[], Player owner) {

        Vector v = new Vector();
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i] instanceof RailRoadCell || Cell[i] instanceof UtilityCell) {
                    if (Cell[i].getOwner() == owner) {
                        v.add(Cell[i]);
                    }
                }
            }
        }
        return v;
    }

    public boolean opponentOwnUtitlity(Cell Cell[], Player opp) {
        int a = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i] instanceof UtilityCell) {
                    if (Cell[i].getOwner() == opp) {
                        a++;
                    }
                }
            }
        }
        if (a == 1) {
            return true;
        }
        return false;
    }

    //whether a trade peoperty available
    public boolean tradeAvailable(Cell Cell[], Player owner) {
        Cell c = (Cell) this.propertyToTrade(Cell, owner).elementAt(0);
        if (c != null) {
            return true;
        }
        return false;
    }

    public boolean acceptTrade(Cell Cell[], Cell offer, Cell request, int amount, int cash, Player owner) {
        int count = 0;
        Vector v = this.tradeProps(Cell, offer.getOwner());
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof UtilityCell) {
                if (owner.getNoOfUtilities() == 1) {
                    int money = offer.getCost() + (int) (offer.getCost() * 0.15);
                    if ((cash + request.getCost()) <= money) {
                        for (int j = 0; j < v.size(); j++) {
                            Cell c = (Cell) v.elementAt(j);
                            if (c == request) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            } else if (Cell[i] instanceof RailRoadCell) {
                int money = offer.getCost() + (int) (offer.getCost() * 0.15);
                if ((cash + request.getCost()) <= money) {
                    for (int j = 0; j < v.size(); j++) {
                        Cell c = (Cell) v.elementAt(j);
                        if (c == request) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (Cell[i].getColorGroup().equals(offer.getColorGroup())) {
                if (Cell[i].getOwner() == owner) {
                    int money = offer.getCost() + (int) (offer.getCost() * 0.15);
                    if ((cash + request.getCost()) <= money) {
                        //check for
                        for (int j = 0; j < v.size(); j++) {
                            Cell c = (Cell) v.elementAt(j);
                            if (c == request) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

//color groups owned by player
    public Set playerMonopolies(Cell Cell[], Player player) {
        Set s = new HashSet();
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() == player) {
                    if (Cell[i] instanceof BrownColorGroup) {
                        a++;
                        if (a == 2) {
                            s.add("BrownColorGroup");
                        }
                    } else if (Cell[i] instanceof LightBlueColorGroup) {
                        b++;
                        if (b == 3) {
                            s.add("LightBlueColorGroup");
                        }
                    } else if (Cell[i] instanceof PinkColorGroup) {
                        c++;
                        if (c == 3) {
                            s.add("PinkColorGroup");
                        }
                    } else if (Cell[i] instanceof OrangeColorGroup) {
                        d++;
                        if (d == 3) {
                            s.add("OrangeColorGroup");
                        }
                    } else if (Cell[i] instanceof RedColorGroup) {
                        e++;
                        if (e == 3) {
                            s.add("RedColorGroup");
                        }
                    } else if (Cell[i] instanceof YellowColorGroup) {
                        f++;
                        if (f == 3) {
                            s.add("YellowColorGroup");
                        }
                    } else if (Cell[i] instanceof GreenColorGroup) {
                        g++;
                        if (g == 3) {
                            s.add("GreenColorGroup");
                        }
                    } else if (Cell[i] instanceof DarkBlueColorGroup) {
                        h++;
                        if (h == 2) {
                            s.add("DarkBlueColorGroup");
                        }
                    }
                }
            }
        }
        log(player.getName() + " has these monopolies: " + s);
        return s;
    }

    public boolean isPlayerMonopoly(Cell Cell[], String color, Player player) {
        int count = 0;
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getColorGroup().equals(color)) {
                    if (Cell[i].getOwner() == player) {
                        count++;
                        if (Cell[i].getColorGroup().equals("Brown") || Cell[i].getColorGroup().equals("Dark Blue")) {
                            if (count == 2) {
                                return true;
                            }
                        } else {
                            if (count == 3) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //CHECKS WHETHER A PROPERTY MONOPOLY IS CONTROLLED BY OTHER PLAYER
    public boolean isOtherPlayerMonopoly(Cell Cell[], Cell prop, Player player) {
        if (isPlayerMonopoly(Cell, prop.getColorGroup(), player)) {
            return true;
        }
        return false;
    }

    //RETURNS COLOR GROUPS THAT HAS MONOPOLY
    public Set propertyMonopoly(Cell Cell[], Player player[]) {
        Set s = new HashSet();
        for (int i = 0; i < Cell.length; i++) {
            for (int j = 0; j < player.length; j++) {
                if (isPlayerMonopoly(Cell, Cell[i].getColorGroup(), player[j])) {
                    if (Cell[i] instanceof BrownColorGroup) {
                        s.add("BrownColorGroup");
                    } else if (Cell[i] instanceof LightBlueColorGroup) {
                        s.add("LightBlueColorGroup");
                    } else if (Cell[i] instanceof PinkColorGroup) {
                        s.add("PinkColorGroup");
                    } else if (Cell[i] instanceof OrangeColorGroup) {
                        s.add("OrangeColorGroup");
                    } else if (Cell[i] instanceof RedColorGroup) {
                        s.add("RedColorGroup");
                    } else if (Cell[i] instanceof YellowColorGroup) {
                        s.add("YellowColorGroup");
                    } else if (Cell[i] instanceof GreenColorGroup) {
                        s.add("GreenColorGroup");
                    } else if (Cell[i] instanceof DarkBlueColorGroup) {
                        s.add("DarkBlueColorGroup");
                    }
                }
            }
        }
        log("monopoly properties: " + s);
        return s;
    }

    //Number of monopolies owned by player
    public int noOfPlayerMonopoly(Cell Cell[], Player player) {
        Set s = this.playerMonopolies(Cell, player);
        return s.size();
    }

    //number of monopolies on board
    public int noOfMonopoly(Cell Cell[], Player player[]) {
        Set s = this.propertyMonopoly(Cell, player);
        return s.size();
    }

    //number of monopolies belonging to other players
    public int noOfOtherMonopoly(Cell Cell[], Player player[], Player owner) {
        return this.noOfMonopoly(Cell, player) - this.noOfPlayerMonopoly(Cell, owner);
    }

    public double probOfProperties(Cell Cell[], Player owner) {
        int count=0;
        for(int i=0;i<Cell.length;i++){
            if(Cell[i] instanceof PropertyCell){
                if(Cell[i].getOwner()==owner){
                    count++;
                }
            }
        }
        return ((count/28)*100);
    }

    //high hit rate properties
    public Set highHitMonopolies(Cell Cell[], Player player[]) {
        Set s = new HashSet();
        Set m = this.propertyMonopoly(Cell, player);
        if (m.contains(ColorGroups[3])) {
            s.add(ColorGroups[3]);
        } else if (m.contains(ColorGroups[4])) {
            s.add(ColorGroups[4]);
        } else if (m.contains(ColorGroups[5])) {
            s.add(ColorGroups[5]);
        } else if (m.contains(ColorGroups[6])) {
            s.add(ColorGroups[6]);
        }
        log("high hit: " + s);
        return s;
    }

    //low hit rate properties
    public Set lowHitMonopolies(Cell Cell[], Player player[]) {
        Set s = new HashSet();
        Set m = this.propertyMonopoly(Cell, player);
        if (m.contains(ColorGroups[2])) {
            s.add(ColorGroups[2]);
        } else if (m.contains(ColorGroups[1])) {
            s.add(ColorGroups[1]);
        } else if (m.contains(ColorGroups[7])) {
            s.add(ColorGroups[7]);
        } else if (m.contains(ColorGroups[0])) {
            s.add(ColorGroups[0]);
        }
        log("low hit: " + s);
        return s;
    }

//whether a player has high hit color group
    public boolean hasHigherHit(Cell Cell[], Player player[], Player owner) {
        Set s = this.highHitMonopolies(Cell, player);//high hit properties
        Set m = this.playerMonopolies(Cell, owner);//player monopolies
        Object cs[] = m.toArray();
        int count = 0;
        for (int i = 0; i < cs.length; i++) {
            if (s.contains(cs[i])) {
                count++;
            }
        }
        if (count >= ((int) s.size() / 2)) {
            return true;
        }
        return false;
    }

    //whether a player has low hit color group
    public boolean hasLowHit(Cell Cell[], Player player[], Player owner) {
        Set s = this.lowHitMonopolies(Cell, player);
        Set sx = this.playerMonopolies(Cell, owner);
        Object cs[] = sx.toArray();
        int count = 0;
        for (int i = 0; i < cs.length; i++) {
            if (s.contains(cs[i])) {
                count++;
            }
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    //returns a property with the highest hit
    public Cell playerHighestHit(Cell Cell[], Player player[], Player owner) {
        Cell c;
        Vector v = new Vector();
        //Vector v2 = new Vector();
        String cellcolor = "";
        Set s = this.playerMonopolies(Cell, owner);
        //Set sx = this.highHitMonopolies(Cell, player);
        Object cs[] = s.toArray();
        for (int i = 0; i < cs.length; i++) {
            v.add(cs[i]);
        }
        if (!v.isEmpty()) {
            for (int i = 0; i < v.size(); i++) {
                String st = v.elementAt(i).toString();
                if (st.equals("OrangeColorGroup")) {
                    cellcolor = "Orange";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("RedColorGroup")) {
                    cellcolor = "Red";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("YellowColorGroup")) {
                    cellcolor = "Yellow";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("GreenColorGroup")) {
                    cellcolor = "Green";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("PinkColorGroup")) {
                    cellcolor = "Pink";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("LightBlueColorGroup")) {
                    cellcolor = "Light Blue";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("DarkBlueColorGroup")) {
                    cellcolor = "Dark Blue";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                } else if (st.equals("BrownColorGroup")) {
                    cellcolor = "Brown";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 5) {
                        break;
                    }
                }
            }
            return this.evenDevt(Cell, cellcolor);
        }

        return null;
    }

    //return the lowest hist prperty
    public Cell playerLowestHit(Cell Cell[], Player player[], Player owner) {
        Cell c;
        Vector v = new Vector();
        String cellcolor = "";
        Set s = this.playerMonopolies(Cell, owner);
        //Set sx = this.highHitMonopolies(Cell, player);
        Object cs[] = s.toArray();
        for (int i = 0; i < cs.length; i++) {
            v.add(cs[i]);
        }
        if (!v.isEmpty()) {
            for (int i = v.size() - 1; i >= 0; i--) {
                String st = v.elementAt(i).toString();
                if (st.equals("BrownColorGroup")) {
                    cellcolor = "Brown";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("LightBlyeColorGroup")) {
                    cellcolor = "Light Blue";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("PinkColorGroup")) {
                    cellcolor = "Pink";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("DarkBlueColorGroup")) {
                    cellcolor = "Dark Blue";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("GreenColorGroup")) {
                    cellcolor = "Green";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("YellowBlueColorGroup")) {
                    cellcolor = "Yellow";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("RedColorGroup")) {
                    cellcolor = "Red";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                } else if (st.equals("OrangeColorGroup")) {
                    cellcolor = "Orange";
                    if (this.evenDevt(Cell, cellcolor).getNumberOfHouses() < 4) {
                        break;
                    }
                }
            }
            return this.evenDevt(Cell, cellcolor);
        }

        return null;
    }

    //checks even development for a property
    public Cell evenDevt(Cell Cell[], String color) {
        Cell c = null;
        Vector v = new Vector();
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getColorGroup().equals(color)) {
                    v.add(Cell[i]);
                }
            }
        }

        int i = 0;
        c = (Cell) v.elementAt(i);
        for (int j = i + 1; j < v.size(); j++) {
            Cell b = (Cell) v.elementAt(j);
            if (c.getNumberOfHouses() > b.getNumberOfHouses()) {
                c = b;
                break;
            }
        }

        log("Even devt: " + c.getName());
        return c;
    }

    //Check whether player has mortgaged property
    public boolean hasMortgage(Cell[] Cell, Player player) {
        if (!mortgagedProperties(Cell, player).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    //returns all mortgages prpoerty for a player
    public Vector mortgagedProperties(Cell[] Cell, Player player) {
        Vector data = new Vector();
        for (int i = 0; i < Cell.length; i++) {
            if (Cell[i] instanceof PropertyCell) {
                if (Cell[i].getOwner() == player) {
                    if (Cell[i].isMortgaged()) {
                        data.add(Cell[i]);
                    }
                }
            }
        }
        log(player.getName() + "'s mortgaged properties: " + data.toString());
        return data;
    }

    //returns property with highest priority to un mortgage
    public Cell unmortgagePriority(Cell[] Cell, Player player) {
        Vector data = mortgagedProperties(Cell, player);
        Cell c;
        if (data.isEmpty()) {
            return null;
        } else {
            c = (Cell) data.elementAt(0);
            for (int i = 0; i < data.size(); i++) {
                Cell d = (Cell) data.elementAt(i);
                if (this.hitRate(c) < this.hitRate(d)) {
                    c = d;
                }
            }
            log(player.getName() + "unmortgage prioprity is " + c.getName());
            return c;
        }
    }

    public void log(String s) {
        log.append(s + "\n");
    }
}
