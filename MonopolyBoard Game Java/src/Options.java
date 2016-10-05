/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdulsalam M.T Umar
 */
public class Options {
    public boolean type;
    public String win;
    public String time;
    public String turn;

    public Options(boolean type, String win,String time,String turn){
        this.type=type;
        this.win=win;
        this.time=time;
        this.turn=turn;
    }

    public void setNormalGame(boolean type){
        this.type=type;
    }
    public void setWin(String win){
        this.win=win;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setTurn(String turn){
        this.turn=turn;
    }
    
    public boolean isNormalGame(){
        return type;
    }
    public String getWin(){
        return win;
    }
    public String getTime(){
        return time;
    }
    public String getTurn(){
        return turn;
    }

}
