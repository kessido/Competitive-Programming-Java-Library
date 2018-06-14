package library;

import IO.InputReader;

import java.util.Arrays;

public class GraphExtention {

    public static int[][] parseGraph_n_e_array_of_of_u_v_noSelfLoops(final int n, final int[][] e, final boolean directed) {
        int[] edgesCount = new int[n];
        for (int[] i : e) {
            edgesCount[i[0]]++;
            if (!directed)
                edgesCount[i[1]]++;
        }
        int[][] graph = new int[n][];
        for (int i = 0; i < n; i++) {
            graph[i] = new int[edgesCount[i]];
        }
        for (int[] i : e) {
            graph[i[0]][--edgesCount[i[0]]] = i[1];
            if (!directed)
                graph[i[1]][--edgesCount[i[1]]] = i[0];
        }
        return graph;
    }

    public static int[][] parseGraph_n_m_and_m_lines_of_u_v_noSelfLoops(final int n, final int m, final InputReader in, final boolean zeroBasedIndex, final boolean directed) {
        int[][] e = in.NextIntMatrix(m, 2, zeroBasedIndex ? 0 : -1);
        return parseGraph_n_e_array_of_of_u_v_noSelfLoops(n, e, directed);
    }

    public static int[][] parseTree_n_and_n_1_lines_of_parent_index_of_the_ith_node(final int n, final InputReader in, final boolean zeroBasedIndex) {
        int[][] e = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) {
            e[i][0] = i + 1;
            e[i][1] = in.NextInt();
            if (!zeroBasedIndex) e[i][1]--;
        }
        return parseGraph_n_e_array_of_of_u_v_noSelfLoops(n, e, false);
    }

    public static int[][] parseTree_n_and_n_lines_with_m_and_then_m_children(final int n, final InputReader in, final boolean zeroBasedIndex) {
        int[][] e = new int[n - 1][2];
        int eIndex = 0;
        for (int i = 0; i < n; i++) {
            int m = in.NextInt();
            while (m-- != 0) {
                int u = in.NextInt();
                if (!zeroBasedIndex) u--;
                e[eIndex][0] = i;
                e[eIndex++][1] = u;
            }
        }
        return parseGraph_n_e_array_of_of_u_v_noSelfLoops(n, e, false);
    }

    public static int[][] getTree_Parent_QueueOrder_Depth_BFS(final int[][] tree) {
        final int n = tree.length;
        final int[] par = new int[n];
        final int[] depth = new int[n];
        final int[] q = new int[n];
        par[0] = -1;
        int qLength = 1;
        for (int i = 0; i < n; i++) {
            final int current = q[i];
            final int currentParent = par[current];
            final int nextDepth = depth[current] + 1;
            for (int j : tree[current]) {
                if (currentParent != j) {
                    par[j] = current;
                    depth[j] = nextDepth;
                    q[qLength++] = j;
                }
            }
        }
        return new int[][]{par, q, depth};
    }

    public static int[][] getTree_Parent_Appearance_FirstAppearances_Depth_BFS(final int[][] tree) {
        final int n = tree.length;
        final int[] par = new int[n];
        final int[] depth = new int[n];
        final int[] appearance = new int[1 + (n - 1) * 2];
        int[] firstAppearances = new int[n];
        Arrays.fill(firstAppearances, -1);
        firstAppearances[0] = 0;
        par[0] = -1;
        int[] currentChild = new int[n];
        int current = 0;
        for (int i = 0; i < appearance.length - 1; i++) {
            final int currentParent = par[current];
            if (firstAppearances[current] == -1) {
                firstAppearances[current] = i;
            }
            if (currentChild[current] < tree[current].length && tree[current][currentChild[current]] == currentParent) {
                currentChild[current]++;
            }
            if (currentChild[current] >= tree[current].length) {
                current = currentParent;
            } else {
                int nextCurrent = tree[current][currentChild[current]++];
                par[nextCurrent] = current;
                depth[nextCurrent] = depth[current] + 1;
                current = nextCurrent;
            }
            appearance[i + 1] = current;
        }
        return new int[][]{par, appearance, firstAppearances, depth};
    }

    public interface LCA {
        int getLCA(int a, int b);
    }

    private static class LCA_using_arrays implements LCA {
        final int[][] LCA;
        final int[] firstAppearance;
        final int[] depth;

        public LCA_using_arrays(final int[] appearances, final int[] firstAppearance, final int[] depth) {
            this.depth = depth;
            this.firstAppearance = firstAppearance;
            int LCALength = IntegerExtention.getNumberOfBits(appearances.length);
            LCA = new int[LCALength][];
            LCA[0] = appearances;
            for (int i = 1; i < LCALength; i++) {
                int size = 1 << i;
                int jumpSize = 1 << (i - 1);
                LCA[i] = new int[LCA[0].length - size + 1];
                for (int j = 0; j < LCA[i].length; j++) {
                    LCA[i][j] = min(LCA[i - 1][j], LCA[i - 1][j + jumpSize]);
                }
            }
        }

        private int min(int a, int b) {
            return (depth[a] < depth[b]) ? a : b;
        }

        @Override
        public int getLCA(int a, int b) {
            a = firstAppearance[a];
            b = firstAppearance[b];
            int l = Math.min(a, b), r = Math.max(a, b);
            int size = r - l + 1;
            int LCAIndex = IntegerExtention.getNumberOfBits(size) - 1;
            int sizeNeeded = 1 << LCAIndex;
            return min(LCA[LCAIndex][l], LCA[LCAIndex][r - sizeNeeded + 1]);
        }
    }


    public static LCA createLCA(int[][] tree) {
        int[][] treeAddon = getTree_Parent_Appearance_FirstAppearances_Depth_BFS(tree);
        return new LCA_using_arrays(treeAddon[1], treeAddon[2], treeAddon[3]);
    }
}
