package Graph;

import java.util.*;

public class GraphClass {

    public static class TopologicalSort {
        private List<Integer>[] graph;
        private Map<Integer, State> states;

        private enum State {
            BLACK,
            WHITE,
            GREY
        }

        public TopologicalSort(int[][] graph) {
            this.graph = new List[graph.length];
            for (int i = 0; i < graph.length; i++)
                this.graph[i] = new ArrayList<>();
            for (int i = 0; i < graph.length; i++)
                for (int j : graph[i])
                    this.graph[i].add(j);

            states = new HashMap<>();
            for (int i = 0; i < graph.length; i++) {
                states.put(i, State.WHITE);
            }
        }

        public List<Integer> sort() {
            List<Integer> res = new ArrayList<>();
            while (states.containsValue(State.WHITE)) {
                int i = 0;
                for (var entry : states.entrySet()) {
                    if (entry.getValue() == State.WHITE) {
                        i = entry.getKey();
                        break;
                    }
                }
                if (!dfs(graph, res, i)) return null;
            }
            Collections.reverse(res);
            return res;
        }

        private boolean dfs(List<Integer>[] graph, List<Integer> res, int u) {
            if (states.get(u) == State.GREY) return false;
            if (states.get(u) == State.BLACK) return true;
            states.put(u, State.GREY);
            for (int v : graph[u])
                if (!dfs(graph, res, v)) return false;
            res.add(u);
            states.put(u, State.BLACK);
            return true;
        }
    }

    public static class Kosaraju {

        private List<Integer>[] graph;

        public Kosaraju(int[][] graph) {
            this.graph = new List[graph.length];
            for (int i = 0; i < graph.length; i++)
                this.graph[i] = new ArrayList<>();
            for (int i = 0; i < graph.length; i++)
                for (int j : graph[i])
                    this.graph[i].add(j);
        }

        public List<List<Integer>> run() {
            int n = graph.length;
            boolean[] used = new boolean[n];
            List<Integer> order = new ArrayList<>();
            for (int i = 0; i < n; i++)
                if (!used[i])
                    dfs(graph, used, order, i);

            List<Integer>[] reverseGraph = new List[n];
            for (int i = 0; i < n; i++)
                reverseGraph[i] = new ArrayList<>();
            for (int i = 0; i < n; i++)
                for (int j : graph[i])
                    reverseGraph[j].add(i);

            List<List<Integer>> components = new ArrayList<>();
            Arrays.fill(used, false);
            Collections.reverse(order);

            for (int u : order)
                if (!used[u]) {
                    List<Integer> component = new ArrayList<>();
                    dfs(reverseGraph, used, component, u);
                    components.add(component);
                }

            return components;
        }

        private void dfs(List<Integer>[] graph, boolean[] used, List<Integer> res, int u) {
            used[u] = true;
            for (int v : graph[u])
                if (!used[v])
                    dfs(graph, used, res, v);
            res.add(u);
        }
    }

    public static class EulerCycle {

        private List<Integer>[] graph;

        public EulerCycle(int[][] graph) {
            this.graph = new List[graph.length];
            for (int i = 0; i < graph.length; i++)
                this.graph[i] = new ArrayList<>();
            for (int i = 0; i < graph.length; i++)
                for (int j : graph[i])
                    this.graph[i].add(j);
        }

        private List<Integer>  directedCycle(int u) {
            int[] curEdge = new int[graph.length];
            List<Integer> res = new ArrayList<>();
            dfs(graph, curEdge, res, u);
            Collections.reverse(res);
            return res;
        }

        public void printEulerTour(int u) {
            var cycle = directedCycle(u);
            for (int i = 0; i < cycle.size() - 1; i++) {
                System.out.print(cycle.get(i) + "-" + cycle.get(i + 1) + " ");
            }
            System.out.println();
        }

        private void dfs(List<Integer>[] graph, int[] curEdge, List<Integer> res, int u) {
            while (curEdge[u] < graph[u].size()) {
                dfs(graph, curEdge, res, graph[u].get(curEdge[u]++));
            }
            res.add(u);
        }
    }

    public static class Fleury {

        private List<Integer>[] graph;
        private int count;

        public Fleury(int[][] graph) {
            count = graph.length;
            this.graph = new List[graph.length];
            for (int i = 0; i < graph.length; i++) {
                this.graph[i] = new ArrayList<>();
            }
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph[i].length; j++) {
                    addEdge(i,graph[i][j]);
                }
            }
        }

        private void addEdge(Integer u, Integer v)
        {
            graph[u].add(v);
            graph[v].add(u);
        }

        private void removeEdge(Integer u, Integer v)
        {
            graph[u].remove(v);
            graph[v].remove(u);
        }

        public void printEulerTour()
        {
            Integer u = 0;
            for (int i = 0; i < count; i++)
            {
                if (graph[i].size() % 2 == 1)
                {
                    u = i;
                    break;
                }
            }

            printEulerUtil(u);
            System.out.println();
        }

        private void printEulerUtil(Integer u)
        {

            for (int i = 0; i < graph[u].size(); i++)
            {
                Integer v = graph[u].get(i);

                if (isValidNextEdge(u, v))
                {
                    System.out.print(u + "-" + v + " ");
                    removeEdge(u, v);
                    printEulerUtil(v);
                }
            }
        }

        private boolean isValidNextEdge(Integer u, Integer v)
        {
            if (graph[u].size() == 1) {
                return true;
            }
            boolean[] isVisited = new boolean[count];
            var count1 = dfsCount(u, isVisited);
            removeEdge(u, v);
            isVisited = new boolean[count];
            var count2 = dfsCount(u, isVisited);

            addEdge(u, v);
            return count1 <= count2;
        }

        private int dfsCount(Integer v, boolean[] isVisited)
        {
            isVisited[v] = true;
            int count = 1;
            for (int adj : graph[v])
            {
                if (!isVisited[adj])
                {
                    count = count + dfsCount(adj, isVisited);
                }
            }
            return count;
        }
    }
}