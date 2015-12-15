package QLearning;

import java.util.Map;

/**
 * Created by jmmodi on 11/19/2015.
 */
public class State {

    int reward;
    int gridX;
    int gridY;
    int actualX;
    int actualY;
    String name;
    Map<String, Double> actionQValue;


    public State(int reward, int gridX, int gridY, Map<String, Double> actionQValue, int actualX, int actualY, String name) {
        this.reward = reward;
        this.gridX = gridX;
        this.gridY = gridY;
        this.actionQValue = actionQValue;
        this.actualX = actualX;
        this.actualY = actualY;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActualX() {
        return actualX;
    }

    public void setActualX(int actualX) {
        this.actualX = actualX;
    }

    public int getActualY() {
        return actualY;
    }

    public void setActualY(int actualY) {
        this.actualY = actualY;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public Map<String, Double> getActionQValue() {
        return actionQValue;
    }

    public void setActionQValue(Map<String, Double> actionQValue) {
        this.actionQValue = actionQValue;
    }

}
