package Utils;

import economics.Contract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;

import java.util.List;

public class Data {
    public static List<Consumer> consumers;
    public static List<Distributor> distributors;
    public static List<MonthlyUpdate> monthlyUpdates;
    public static List<Contract> contracts;

    public static void setConsumers(List<Consumer> consumers) {
        Data.consumers = consumers;
    }

    public static void setDistributors(List<Distributor> distributors) {
        Data.distributors = distributors;
    }

    public static void setMonthlyUpdates(List<MonthlyUpdate> monthlyUpdates) {
        Data.monthlyUpdates = monthlyUpdates;
    }

    public static void setContracts(List<Contract> contracts) {
        Data.contracts = contracts;
    }

    public static List<Consumer> getConsumers() {
        return consumers;
    }

    public static List<Distributor> getDistributors() {
        return distributors;
    }

    public static List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public static List<Contract> getContracts() {
        return contracts;
    }
}
