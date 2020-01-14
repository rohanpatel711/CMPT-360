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

        private int minimumCostCalculator(int i, int j) {
            int minCost = -1;
            int max = Math.max(Buses[i].bus_start, Buses[j].bus_start);
            int min = Math.min(Buses[i].bus_end, Buses[j].bus_end);
            for (int a = max; a <= min; a++) {
                int newCost = 0;
                for (int b = i + 1; b < j; b++) {
                    Bus temp = Buses[b];
                    if ((a <= temp.bus_end) && (a >= temp.bus_start)) {
                        newCost++;
                        if ((newCost >= minCost) && (minCost >= 0)) {
                            break;
                        }
                    }
                }
                if ((minCost < 0) || (newCost < minCost)) {
                    minCost = newCost;
                    if (newCost == 0) {
                        break;
                    }
                }
            }
            return minCost;
        }
    }

    static class BusGenerator implements Iterator<Bus> {
        int max;
        int length, start;

        private BusGenerator(int max) {
            this.max = max;
            resetBus();
        }

        void resetBus() {
            length = 1;
            start = 0;
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

        @Override
        public boolean hasNext(){
            return ((length == max) && (start == 0)) || (length < max);
        }

    }

    private static class CaseGenerator implements Iterator<Solver> {
        Solver nextSolver;
        List<BusGenerator> singleBusGenerators;

        CaseGenerator(int noOfBus) {
            List<BusGenerator> listOfBuses = new ArrayList<>();
            for (int x = 0; x < noOfBus; x++) {
                listOfBuses.add(new BusGenerator(noOfBus));
            }
            singleBusGenerators = listOfBuses;
            nextSolver = new Solver(singleBusGenerators.stream().map(BusGenerator::next).filter(Objects::nonNull).toArray(Bus[]::new));
            nextSolver.connection(2147483647);
        }

        @Override
        public Solver next() {
            final Solver nextBus = nextSolver;
            if (nextBus.cost > 0) {
                nextSolver = findNext(nextBus.cost - 1);
            }
            return nextBus;
        }

        Solver findNext(final int maximumCost) {
            Bus[] nextBus = nextSolver.Buses.clone();
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
                nextBus[row] = singleBusGenerators.get(row).next();
                // Initialise the first and the last buses as we know they will be always full
                nextBus[singleBusGenerators.size() - 1].bus_start = 0;
                nextBus[singleBusGenerators.size() - 1].bus_end = singleBusGenerators.size() + 1;
                nextBus[0].bus_start = 0;
                nextBus[0].bus_end = singleBusGenerators.size() + 1;
                //Then start generating other configurations for n-2 buses as start and end bus are fixed
                //That is how we cancel our unnecessary configurations and generate important ones.
                for (int val = row + 1; val < singleBusGenerators.size() - 1; val++) {
                    singleBusGenerators.get(val).resetBus();
                    nextBus[val] = singleBusGenerators.get(val).next();
                }

                Solver temp = new Solver(nextBus);
                if (temp.connection(maximumCost)) {
                    return temp;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextSolver != null;
        }
    }

    static Solver findMinimumSolver(final int n) {
        final CaseGenerator caseGen = new CaseGenerator(n);
        Solver sol = caseGen.next();
        while ((sol.cost > 0) && caseGen.hasNext()) {
            final Solver sc = caseGen.next();
            if (sc.cost < sol.cost) {
                sol = sc;
            }
        }
        return sol;
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
        BusConnector.Solver s = findMinimumSolver(X);
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
