package QLearning;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmmodi on 11/19/2015.
 */
public class Main {

    public static void main(String[] args) {
        int rows = 5;
        int columns = 4;

        State[][] gridWorld = new State[rows][columns];

        System.out.println("Learning for Reward = -1");
        int reward = -1;
        initializeWorld(gridWorld, reward);
        learn(gridWorld);
        printGrid(gridWorld);


        System.out.println("Learning for Reward = 0");
        reward = 0;
        initializeWorld(gridWorld, reward);
        learn(gridWorld);
        printGrid(gridWorld);

    }

    private static void printRow(State[] s) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|                                                                                                                                                  |");
        System.out.printf("             %f                |             %f                |             %f              |             %f                ", s[0].getActionQValue().get("up"), s[1].getActionQValue().get("up"), s[2].getActionQValue().get("up"), s[3].getActionQValue().get("up"));
        System.out.println("");
        System.out.printf("   %f             %f      | %f             %f     |   %f             %f        |   %f     %f", s[0].getActionQValue().get("left"), s[0].getActionQValue().get("right"), s[1].getActionQValue().get("left"), s[1].getActionQValue().get("right"), s[2].getActionQValue().get("left"), s[2].getActionQValue().get("right"), s[3].getActionQValue().get("left"), s[3].getActionQValue().get("right"));
        System.out.println("");
        System.out.printf("             %f                |             %f                |             %f                |             %f                ", s[0].getActionQValue().get("down"), s[1].getActionQValue().get("down"), s[2].getActionQValue().get("down"), s[3].getActionQValue().get("down"));
        System.out.println("");
        System.out.println("|                                                                                                                                                  |");
        System.out.print("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
    }

    private static void printGrid(State[][] gridWorld) {
        for (int i = 0; i < gridWorld.length; i++) {
            printRow(gridWorld[i]);
            System.out.print(" ");
        }
        System.out.println(" ");
    }


    private static Map<String, Double> getStringDoubleMap() {
        Map<String, Double> actionValueMap = new HashMap<>();
        actionValueMap.put("up", 0.0);
        actionValueMap.put("down", 0.0);
        actionValueMap.put("left", 0.0);
        actionValueMap.put("right", 0.0);
        return actionValueMap;
    }

    private static void printReward(State[][] gridWorld) {
        for (int i = 0; i < gridWorld.length; i++) {
            for (int j = 0; j < gridWorld[0].length; j++) {
                System.out.print(gridWorld[i][j].getReward());
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    private static void learn(State[][] gridWorld) {
        LearningAlgorithm learningAlgorithm = new LearningAlgorithm(gridWorld);

        learningAlgorithm.learnQValues(4, 0);
    }


    private static void initializeWorld(State[][] gridWorld, int reward) {

        for (int i = 0; i < gridWorld.length; i++) {
            for (int j = 0; j < gridWorld[0].length; j++) {
                if (i == 0 && j == 3) {
                    gridWorld[i][j] = new State(10, j, gridWorld.length - i - 1, getStringDoubleMap(), i, j, "goal");
                } else if (i == 1 && j == 1) {
                    gridWorld[i][j] = new State(-50, j, gridWorld.length - i - 1, getStringDoubleMap(), i, j, "pit");
                } else if (i == 3 && j == 1 || i == 3 && j == 3) {
                    gridWorld[i][j] = new State(0, j, gridWorld.length - i - 1, getStringDoubleMap(), i, j, "wall");
                } else {
                    gridWorld[i][j] = new State(reward, j, gridWorld.length - i - 1, getStringDoubleMap(), i, j, "start");
                }
            }

        }
    }

}
