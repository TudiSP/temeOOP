import Utils.Utils;
import economics.Contract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;
import inputOutput.Input;

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

        for (int i = 0; i < nrOfTurns; i++) {
            for (Consumer consumer : consumers) {
                if(consumer.isBankrupt()) {

                }
            }
        }
    }
}
