import Graph.GraphClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Graph.GraphClass.*;

public class Main {
    public static void main(String[] args) {
        int[][] a = {
                {2,4,5},
                {},
                {},
                {0,1,4,5},
                {1},
                {1},
        };
        int[][] b = {
                {1},
                {2,3},
                {3},
                {0,1}
        };

        System.out.println("Kosaraju: ");
        {
            System.out.println("for b: ");
            var components = new Kosaraju(b).run();
            int i = 1;
            for (var component : components) {
                System.out.println("id " + i + ": " + component);
                i++;
            }
            System.out.println("for a: ");
            components = new Kosaraju(a).run();
            i = 1;
            for (var component : components) {
                System.out.println("id " + i + ": " + component);
                i++;
            }
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Tarjan: ");
        {
            System.out.println("for a: ");
            var sorted = new TopologicalSort(a).sort();
            System.out.println(sorted);
            System.out.println("for b: ");
            sorted = new TopologicalSort(b).sort();
            System.out.println(sorted);
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Euler: ");
        {
            new EulerCycle(b).printEulerTour(0);
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Flury: ");
        {
            new Fleury(b).printEulerTour();
        }
        System.out.println("----------------------------------------------------");
    }
}
