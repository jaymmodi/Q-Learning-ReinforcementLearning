package QLearning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by jmmodi on 11/19/2
 * 15.
 */
public class LearningAlgorithm {

    State[][] gridWorld;

    public LearningAlgorithm(State[][] gridWorld) {
        this.gridWorld = gridWorld;
    }

    public void learnQValues(int x, int y) {

        State currentState;
        double exploreProbability = 0.5;
        State nextState;

        for (int i = 0; i < 5000; i++) {

            for (int j = 0; j < 10; j++) {

                currentState = gridWorld[x][y];

                while (!currentState.getName().equals("goal")) {
                    double probability = getRandomProbability();

                    if (probability <= exploreProbability) {
                        int randomActionNumber = getRandomNumber(1, 4);
                        String actionName = getActionNameForRandom(randomActionNumber);
                        nextState = explore(actionName, currentState);
                        updateQValue(currentState, nextState, actionName);
                        currentState = nextState;
                    } else {
                        currentState = exploit(currentState);
                    }
                }
                if (currentState.getName().equals("goal")) {
                    moveOneStepFromGoal(currentState, exploreProbability);
                }
            }
            exploreProbability = decreaseExploreProbability(exploreProbability);
        }
        System.out.println("Done");
    }

    private void moveOneStepFromGoal(State currentState, double exploreProbability) {
        double probability = getRandomProbability();
        State nextState;

        if (probability <= exploreProbability) {
            //explore
            int randomActionNumber = getRandomNumber(1, 4);
            String actionName = getActionNameForRandom(randomActionNumber);
            nextState = currentState;
            updateQValue(currentState, nextState, actionName);
        } else {
            //exploit
            HashMap<String, Double> maxActionValue = findMax(currentState.getActionQValue());

            nextState = currentState;
            if (maxActionValue.containsKey("up")) {
                updateQValue(currentState, nextState, "up");
            } else if (maxActionValue.containsKey("down")) {
                updateQValue(currentState, nextState, "down");
            } else if (maxActionValue.containsKey("left")) {
                updateQValue(currentState, nextState, "left");
            } else if (maxActionValue.containsKey("right")) {
                updateQValue(currentState, nextState, "right");
            }
        }
    }

    private double decreaseExploreProbability(double exploreProbability) {
        return (exploreProbability) / (1 + exploreProbability);
    }

    private State exploit(State currentState) {
        HashMap<String, Double> maxActionValue = findMax(currentState.getActionQValue());

        State nextState = null;

        if (maxActionValue.containsKey("up")) {
            nextState = moveUp(currentState);
            updateQValue(currentState, nextState, "up");
        } else if (maxActionValue.containsKey("down")) {
            nextState = moveDown(currentState);
            updateQValue(currentState, nextState, "down");
        } else if (maxActionValue.containsKey("left")) {
            nextState = moveLeft(currentState);
            updateQValue(currentState, nextState, "left");
        } else if (maxActionValue.containsKey("right")) {
            nextState = moveRight(currentState);
            updateQValue(currentState, nextState, "right");
        }
        return nextState;
    }

    private void updateQValue(State currentState, State nextState, String actionName) {
        double currentQValue = currentState.getActionQValue().get(actionName);
        double learningRate = 0.1;
        double discount = 0.9;

        double maxValue = findMaxDouble(nextState.getActionQValue());
        currentQValue = currentQValue + learningRate * (currentState.getReward() + discount * maxValue - currentQValue);
        currentState.getActionQValue().put(actionName, currentQValue);
        this.gridWorld[currentState.actualX][currentState.actualY] = currentState;
    }

    private double findMaxDouble(Map<String, Double> actionQValue) {
        Iterator<Map.Entry<String, Double>> it = actionQValue.entrySet().iterator();
        double max = -Double.MAX_VALUE;
        while (it.hasNext()) {
            Map.Entry<String, Double> entry = it.next();
            double value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    private HashMap<String, Double> findMax(Map<String, Double> actionQValue) {
        Iterator<Map.Entry<String, Double>> it = actionQValue.entrySet().iterator();
        double max = -Double.MAX_VALUE;
        String action = null;
        HashMap<String, Double> maxActionValue = new HashMap<>();
        while (it.hasNext()) {
            Map.Entry<String, Double> pair = it.next();
            double value = pair.getValue();
            String key = pair.getKey();
            if (value > max) {
                max = value;
                action = key;
            }
        }
        maxActionValue.put(action, max);
        return maxActionValue;
    }

    private State explore(String actionName, State currentState) {
        switch (actionName) {
            case "up":
                return moveUp(currentState);
            case "right":
                return moveRight(currentState);
            case "down":
                return moveDown(currentState);
            default:
                return moveLeft(currentState);
        }
    }

    private State moveDown(State currentState) {
        State nextState;
        int height = currentState.actualX + 1;

        if (height <= gridWorld.length - 1) {
            nextState = gridWorld[height][currentState.getActualY()];
        } else {
            nextState = gridWorld[currentState.actualX][currentState.actualY];
        }
        if (blockingState(nextState)) {
            return currentState;
        }
        return nextState;
    }

    private State moveRight(State currentState) {
        int width = currentState.actualY + 1;

        State nextState;

        if (width <= gridWorld[0].length - 1) {
            nextState = gridWorld[currentState.actualX][width];


        } else {
            nextState = gridWorld[currentState.actualX][currentState.actualY];
        }

        if (blockingState(nextState)) {
            return currentState;
        }
        return nextState;
    }

    private State moveUp(State currentState) {
        int height = currentState.actualX - 1;

        State nextState;

        if (height >= 0) {
            nextState = gridWorld[height][currentState.actualY];
        } else {
            nextState = gridWorld[currentState.actualX][currentState.actualY];
        }

        if (blockingState(nextState)) {
            return currentState;
        }
        return nextState;
    }

    private boolean blockingState(State nextState) {
        return nextState.getName().equals("wall");
    }

    private State moveLeft(State currentState) {
        int width = currentState.actualY - 1;
        State nextState;

        if (width >= 0) {
            nextState = gridWorld[currentState.actualX][width];
        } else {
            nextState = gridWorld[currentState.actualX][currentState.actualY];
        }

        if (blockingState(nextState)) {
            return currentState;
        }
        return nextState;
    }

    private String getActionNameForRandom(int randomActionNumber) {
        switch (randomActionNumber) {
            case 1:
                return "up";
            case 2:
                return "down";
            case 3:
                return "left";
            default:
                return "right";
        }
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    private double getRandomProbability() {
        Random random = new Random();
        return random.nextDouble();
    }

}
