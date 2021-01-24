import economics.Contract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import inputOutput.Input;
import inputOutput.Output;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //initializations
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
        final int nrOfTurns;

        //input data parsing
        nrOfTurns = Input.parseInputFromFile(args[0], consumers, distributors,
                producers, monthlyUpdates);


        List<Contract> contracts = new ArrayList<>();

        //Create and run the simulation
        Simulation simulation = Simulation.getInstance();
        simulation.runSimulation(consumers, distributors, producers,
                contracts, monthlyUpdates, nrOfTurns);


        //generate output and write it to file
        Output.writeToOutput(args[1], consumers, distributors, producers, contracts);

    }
}
