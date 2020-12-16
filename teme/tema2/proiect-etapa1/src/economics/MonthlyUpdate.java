package economics;

import entities.Consumer;

import java.util.ArrayList;
import java.util.List;

public class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<CostChange> costChanges;

    public MonthlyUpdate() {
        newConsumers = new ArrayList<>();
        costChanges = new ArrayList<>();
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
