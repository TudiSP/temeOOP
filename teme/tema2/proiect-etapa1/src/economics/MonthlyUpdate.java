package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costChanges;

    public MonthlyUpdate() {
        newConsumers = new ArrayList<>();
        costChanges = new ArrayList<>();
    }
    public void updateMonth(List<Distributor> distributors,
                            List<Consumer> consumers, List<Consumer> allConsumers) {
        for (Consumer newConsumer : newConsumers) {
            consumers.add(newConsumer);
            allConsumers.add(newConsumer);
        }

        for (CostChange costChange : costChanges) {
            Distributor distributor = Utils
                    .idToDistributorSearch(distributors, costChange.getId());

            distributor.setInfrastuctureCost(costChange.getInfrastuctureCost());
            distributor.setProductionCost(costChange.getProductionCost());
        }
    }

    public static void updateSimulation(List<Distributor> distributors,
                                        List<Consumer> consumers,
                                        List<MonthlyUpdate> monthlyUpdates,
                                        List<Consumer> allConsumers) {
        for (MonthlyUpdate monthlyUpdate : monthlyUpdates) {
            monthlyUpdate.updateMonth(distributors, consumers, allConsumers);
        }
    }

    /**
     * add another new consumer to the newConsumer list
     * @param consumer
     */
    public void addNewConsumer(Consumer consumer) {
        newConsumers.add(consumer);
    }

    /**
     * add another costChange to the costChangeslist
     * @param costChange
     */
    public void addCostChange(CostChange costChange) {
        costChanges.add(costChange);
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<CostChange> getCostChanges() {
        return costChanges;
    }



    @Override
    public String toString() {
        return "MonthlyUpdate{"
                + "newConsumers=" + newConsumers.toString()
                + ", costChanges=" + costChanges.toString()
                + '}';
    }
}
