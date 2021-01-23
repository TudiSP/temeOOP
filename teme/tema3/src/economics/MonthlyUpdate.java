package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store data about monthly changes
 * and holds methods for applying them
 */
public final class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costChanges;

    /**
     * initialises a new monthly update with new lists
     */
    public MonthlyUpdate() {
        newConsumers = new ArrayList<>();
        costChanges = new ArrayList<>();
    }

    /**
     * applies monthly updates to entities
     *
     * @param distributors
     * @param consumers
     * @param allConsumers
     */
    public void updateMonth(final List<Distributor> distributors,
                            final List<Consumer> consumers,
                            final List<Consumer> allConsumers) {
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

    /**
     * add another new consumer to the newConsumer list
     *
     * @param consumer
     */
    public void addNewConsumer(final Consumer consumer) {
        newConsumers.add(consumer);
    }

    /**
     * add another costChange to the costChangeslist
     *
     * @param costChange
     */
    public void addCostChange(final CostChange costChange) {
        costChanges.add(costChange);
    }

    @Override
    public String toString() {
        return "MonthlyUpdate{"
                + "newConsumers=" + newConsumers.toString()
                + ", costChanges=" + costChanges.toString()
                + '}';
    }
}
