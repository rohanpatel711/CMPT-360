import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//****************
//bus_start: READ ONLY
//****************

class BusConnector {
    static class Bus {
        int bus_start;
        int bus_end;

        private Bus(final int bus_start, final int bus_end) {
            this.bus_start = bus_start;
            this.bus_end = bus_end;
        }
    }

    public static class Solver {
        public final Bus[] Buses;
        public int cost = 2147483647; // Highest value for a Integer.


        private Solver(final Bus[] Buses) {
            this.Buses = Buses;
        }

        private boolean connection(final int maximumCost) {
            int totalCost = 0;
            int i = 0;
            int j;
            while(i < (Buses.length - 1)){
                j = i + 1;
                while(j < Buses.length){
                    final int cost = minimumCostCalculator(i, j);
                    j++;
                    if (cost >= 0 && cost <= maximumCost) {
                        totalCost += cost;
                        continue;
                    }
                    return false;
                }
                i++;
            }
            cost = totalCost;
            return true;
        }

        private int minimumCostCalculator(final int i, final int j) {
            int minConnectionCost = -1;
            for (int k = Math.max(Buses[i].bus_start, Buses[j].bus_start); k <=
                    Math.min(Buses[i].bus_end, Buses[j].bus_end); k++) {
                // calculate cost for connectioning at column k
                int newCost = 0;
                for (int l = i + 1; l < j; l++) {
                    final Bus midBus = Buses[l];
                    if ((k >= midBus.bus_start) && (k <= midBus.bus_end)) {
                        newCost++;
                        if ((minConnectionCost >= 0) && (newCost >= minConnectionCost)) {
                            break;
                        }
                    }
                }
                if ((minConnectionCost < 0) || (newCost < minConnectionCost)) {
                    minConnectionCost = newCost;
                    if (newCost == 0) {
                        break;
                    }
                }
            }
            return minConnectionCost;
        }
    }

    private static class BusGenerator implements Iterator<Bus> {
        private final int max;
        private int length, start;

        private BusGenerator(final int max) {
            this.max = max;
            resetBus();
        }

        @Override
        public boolean hasNext() {
            return (length < max) || ((length == max)
                    && (start == 0));
        }

        @Override
        public Bus next() {
            final Bus nextBus = new Bus(start, start + length);
            start++;
            if ((start + length) > max) {
                start = 0;
                length++;
            }
            return nextBus;
        }
        void resetBus() {
            length = 1;
            start = 0;
        }
    }

    private static class CaseGenerator implements Iterator<Solver> {
        private Solver next;
        private final List<BusGenerator> singleBusGenerators;

        private CaseGenerator(final int BusNums) {
            List<BusGenerator> list = new ArrayList<>();
            for (int x = 0; x < BusNums; x++) {
                list.add(new BusGenerator(BusNums));
            }
            singleBusGenerators = list;

            List<Bus> nextBus = singleBusGenerators.stream()
                    .map(BusGenerator::next)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            next = new Solver(nextBus.toArray(new Bus[0]));
            next.connection(2147483647);
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Solver next() {
            final Solver nextBus = next;
            if (nextBus.cost > 0) {
                next = findNext(nextBus.cost - 1);
            }
            return nextBus;
        }

        private Solver findNext(final int maximumCost) {
            Bus[] Bus = next.Buses.clone();
            while (true) {
                int row = -1;
                for (int val = singleBusGenerators.size() - 1; val >= 0; val--) {
                    if (singleBusGenerators.get(val).hasNext()) {
                        row = val;
                        break;
                    }
                }
                if (row < 0) {
                    return null;
                }
                Bus[row] = singleBusGenerators.get(row).next();
                Bus[singleBusGenerators.size() - 1].bus_start = 0;
                Bus[singleBusGenerators.size() - 1].bus_end = singleBusGenerators.size() + 1;
                Bus[0].bus_start = 0;
                Bus[0].bus_end = singleBusGenerators.size() + 1;
                for (int val = row + 1; val < singleBusGenerators.size() - 1; val++) {
                    singleBusGenerators.get(val).resetBus();
                    Bus[val] = singleBusGenerators.get(val).next();
                }

                Solver Solver = new Solver(Bus);
                if (Solver.connection(maximumCost)) {
                    return Solver;
                }
            }
        }
    }

    public static Solver findMinSolver(final int n) {
        final CaseGenerator gen = new CaseGenerator(n);
        Solver s = gen.next();
        while (gen.hasNext() && (s.cost > 0)) {
            final Solver sc = gen.next();
            if (sc.cost < s.cost) {
                s = sc;
            }
        }
        return s;
    }
}
public class Quantum extends BusConnector{
//****************
//END: READ ONLY
//****************

// YOU CAN DEFINE YOUR OWN FUNCTIONS HERE IF YOU REALLY NEED ONE

//****************
//START: READ ONLY
//****************
    /**
     * @param X : The number of buses
     * @return The cost of minimum crossing configuration with X buses
     */
    public static int cost(int X) {
//****************
//END: READ ONLY
//****************

        //WRITE YOUR NSID: rsp502

        //Start: edit and write your code here
        BusConnector.Solver s = findMinSolver(X);
        System.out.println("Problem: " + X + " -> " + "Cost: " + s.cost + "\n");
        System.out.println("Optimal Solver would be:");
        Bus[] Bus =s.Buses;
        int i = 1;
        for (Bus value : Bus) {
            System.out.print(i + "-> Start:" + value.bus_start);
            System.out.print(" End:" + value.bus_end);
            System.out.println("");
            i++;
        }
        System.out.println("-----------------------------------------");
        return s.cost;
    }
//****************
//START: READ ONLY
//****************
    /**
     * Main Function.
     */
    public static void main(String[] args) {

        BufferedReader reader;
        File file = new File("output.txt");
        int X = 0;
        String line;
        try {
            reader = new BufferedReader(new FileReader("Quantum.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            while(true){
                line = reader.readLine();
                if(line == null) break;
                X = Integer.parseInt(line);
                writer.write(cost(X) + "\n");
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate input file.");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
