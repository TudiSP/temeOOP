import Utils.Utils;
import economics.Contract;
import economics.DelayedContract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;
import inputOutput.Input;
import inputOutput.Output;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //initializations
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
        int nrOfTurns;

        //input data parsing
        nrOfTurns = Input.parseInputFromFile(args[0], consumers, distributors, monthlyUpdates);

        //initialise the simulation by making first contracts
        List<Contract> contracts = Contract.initialiseContracts(consumers, distributors);

        //Create and run the simulation
        Simulation simulation = Simulation.getInstance();
        simulation.runSimulation(consumers, distributors, contracts, monthlyUpdates, nrOfTurns);

        Output.writeToOutput(args[0], args[1], consumers, distributors, contracts);

    }
}
