package entities;

import statistics.MonthlyStats;

import java.util.ArrayList;
import java.util.List;

/**
 * holds data and methods for producers
 */
public final class Producer {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final double priceKW;
    private int energyPerDistributor;
    private List<Distributor> observerDistributors; // observers
    private List<MonthlyStats> monthlyStats;

    /**
     * creates and initialises a producer according to parameters
     * @param id
     * @param energyType
     * @param maxDistributors
     * @param priceKW
     * @param energyPerDistributor
     */
    public Producer(final int id, final String energyType, final int maxDistributors,
                    final double priceKW, final int energyPerDistributor) {
        this.id = id;
        switch (energyType) {
            case "WIND" -> this.energyType = EnergyType.WIND;
            case "NUCLEAR" -> this.energyType = EnergyType.NUCLEAR;
            case "HYDRO" -> this.energyType = EnergyType.HYDRO;
            case "SOLAR" -> this.energyType = EnergyType.SOLAR;
            default -> this.energyType = EnergyType.COAL;
        }
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.observerDistributors = new ArrayList<>();
        this.monthlyStats = new ArrayList<>();
    }

    /**
     * add a new observer to observerDistributor list
     * @param distributor
     */
    public void addObserverDistributor(final Distributor distributor) {
        observerDistributors.add(distributor);
    }

    /**
     * notify observers(distributors)
     */
    public void notifyMonthlyChange() {
        double newCost = energyPerDistributor * priceKW;
        for (Distributor distributor : observerDistributors) {
            distributor.update(id, newCost);
        }
    }

    public int getId() {
        return id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<Distributor> getObserverDistributors() {
        return observerDistributors;
    }

    public void setObserverDistributors(List<Distributor> observerDistributors) {
        this.observerDistributors = observerDistributors;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public void addMonthlyStats(final int month,
                                final List<Integer> distributorIdList) {
        monthlyStats.add(new MonthlyStats(month, distributorIdList));
    }

    public static void addMonthlyStatsAll (final List<Producer> producers,
                                           final int month) {
        for (Producer producer : producers) {
            List<Integer> distributorIdList = new ArrayList<>();
            for (Distributor distributor : producer.observerDistributors) {
                distributorIdList.add(distributor.getId());
            }
            producer.addMonthlyStats(month, distributorIdList);
        }
    }

    @Override
    public String toString() {
        return "Producer{"
                + "id=" + id
                + ", energyType=" + energyType
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }
}
