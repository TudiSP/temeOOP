package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store data about monthly changes
 * and holds methods for applying them
 */
public final class MonthlyUpdate {
    private List<Consumer> newConsumers;
    private List<DistributorChange> distributorChanges;
    private List<ProducerChange> producerChanges;

    /**
     * initialises a new monthly update with new lists
     */
    public MonthlyUpdate() {
        newConsumers = new ArrayList<>();
        distributorChanges = new ArrayList<>();
        producerChanges = new ArrayList<>();
    }

    /**
     * applies monthly updates to entities
     *
     * @param distributors
     * @param consumers
     * @param allConsumers
     */
    public void updateMonth(final List<Distributor> distributors,
                            final List<Producer> producers,
                            final List<Consumer> consumers,
                            final List<Consumer> allConsumers) {
        for (Consumer newConsumer : newConsumers) {
            consumers.add(newConsumer);
            allConsumers.add(newConsumer);
        }

        for (DistributorChange distributorChange : distributorChanges) {
            Distributor distributor = Utils
                    .idToDistributorSearch(distributors, distributorChange.getId());

            distributor.setInfrastuctureCost(distributorChange.getInfrastuctureCost());
        }

        for (ProducerChange producerChange : producerChanges) {
            Producer producer = Utils
                    .idToProducerSearch(producers, producerChange.getId());

            producer.setEnergyPerDistributor(producerChange.getEnergyPerDistributor());
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
     * add another distributorChange to the distributorChanges list
     *
     * @param distributorChange
     */
    public void addDistributorChange(final DistributorChange distributorChange) {
        distributorChanges.add(distributorChange);
    }

    /**
     * add another producerChange to the producerChanges list
     * @param producerChange
     */
    public void addProducerChange(final ProducerChange producerChange) {
        producerChanges.add(producerChange);
    }

    @Override
    public String toString() {
        return "MonthlyUpdate{"
                + "newConsumers=" + newConsumers.toString()
                + ", costChanges=" + distributorChanges.toString()
                + '}';
    }
}
