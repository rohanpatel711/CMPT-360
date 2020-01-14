import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BarConnections {

    static class Bar {
        final int start, end;

        private Bar(final int s, final int e) {
            start = s;
            end = e;
        }
    }

    private static class Solution {
        private final Bar[] bars;
        private int cost = Integer.MAX_VALUE;

        private Solution(final Bar[] bars) {
            this.bars = bars;
        }

        private boolean connect(final int maxcost) {
            int sumcost = 0;
            for (int i = 0; i < (bars.length - 1); i++) {
                for (int j = i + 1; j < bars.length; j++) {
                    final int pairCost = minCostBetween(i, j);
                    if (pairCost >= 0 && pairCost <= maxcost) {
                        sumcost += pairCost;
                        continue;
                    }
                    return false;
                }
            }
            cost = sumcost;
            return true;
        }

        private int minCostBetween(final int i, final int j) {
            int minConnectionCost = -1;
            for (int k = Math.max(bars[i].start, bars[j].start); k <=
                    Math.min(bars[i].end, bars[j].end); k++) {
                // calculate cost for connecting at column k
                int newcost = 0;
                for (int l = i + 1; l < j; l++) {
                    final Bar midbar = bars[l];
                    if ((k >= midbar.start) && (k <= midbar.end)) {
                        newcost++;
                        if ((minConnectionCost >= 0) && (newcost >=
                                minConnectionCost)) {
                            break;
                        }
                    }
                }
                if ((minConnectionCost < 0) || (newcost <
                        minConnectionCost)) {
                    minConnectionCost = newcost;
                    if (newcost == 0) {
                        break;
                    }
                }
            }
            return minConnectionCost;
        }
    }

    private static class SingleBarGenerator implements Iterator<Bar> {
        private final int MAX_POSITION;
        private int length, start;

        private SingleBarGenerator(final int maxPosition) {
            this.MAX_POSITION = maxPosition;
            reset();
        }

        @Override
        public boolean hasNext() {
            return (length < MAX_POSITION) || ((length == MAX_POSITION)
                    && (start == 0));
        }

        @Override
        public Bar next() {
            final Bar result = new Bar(start, start + length);
            start++;
            if ((start + length) > MAX_POSITION) {
                start = 0;
                length++;
            }
            return result;
        }

        public void reset() {
            length = 1;
            start = 0;
        }
    }

    private static class SolutionGenerator implements Iterator<Solution> {
        private Solution next;
        private final List<SingleBarGenerator> singleBarGenerators;

        private SolutionGenerator(final int barNums) {
            final int maxPositions = barNums;
            List<SingleBarGenerator> list = new ArrayList<>();
            for (int x = 0; x < barNums; x++) {
                list.add(new SingleBarGenerator(maxPositions));
            }
            singleBarGenerators = list;
            // initialize first
            List<Bar> nextBars = singleBarGenerators.stream()
                    .map(SingleBarGenerator::next)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            next = new Solution(nextBars.toArray(new Bar[0]));
            next.connect(Integer.MAX_VALUE);

        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Solution next() {
            final Solution result = next;
            if (result.cost > 0) {
                next = findNext(result.cost - 1);
            }
            return result;
        }

        private Solution findNext(final int maxcost) {
            final Bar[] bars = next.bars.clone();
            while (true) {
                int nextrow = -1;
                for (int i = singleBarGenerators.size() - 1; i >= 0; i--) {
                    if (singleBarGenerators.get(i).hasNext()) {
                        nextrow = i;
                        break;
                    }
                }
                if (nextrow < 0) {
                    return null;
                }
                bars[nextrow] = singleBarGenerators.get(nextrow).next();
                for (int j = nextrow + 1; j < singleBarGenerators.size(); j++) {
                    singleBarGenerators.get(j).reset();
                    bars[j] = singleBarGenerators.get(j).next();
                }

                Solution solution = new Solution(bars);
                if (solution.connect(maxcost)) {
                    return solution;
                }
            }
        }
    }

    private static Solution findMinSolution(final int n) {
        final SolutionGenerator gen = new SolutionGenerator(n);
        Solution s = gen.next();
        while (gen.hasNext() && (s.cost > 0)) {
            final Solution sc = gen.next();
            if (sc.cost < s.cost) {
                s = sc;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        int n = 6;
        final Solution s = findMinSolution(n);
        Bar[] bar = s.bars;
        for (Bar value : bar) {
            System.out.print("Start:" + value.start);
            System.out.print(" End:" + value.end);
            System.out.print("|");
        }
        System.out.println();
        System.out.println("Problem: " + n + " Solution: " + s.cost);

    }
}