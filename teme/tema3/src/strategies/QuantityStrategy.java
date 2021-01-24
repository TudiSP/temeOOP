package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.*;

public class QuantityStrategy implements DistributorStrategy{
    private final String label = "QUANTITY";
    /**
     * Quantity strategy constructor
     */
    public  QuantityStrategy() {}

    /**
     * Quantity strategy implementation, prioritises  quantity.
     * Works by sorting the producers
     * list and then choosing them in the sorted order.
     * @param distributor
     * @param producers
     * @return a Map of producers id and their costs
     */
    @Override
    public Map<Integer, Double> chooseProducers(final Distributor distributor,
                                                final List<Producer> producers) {
        List<Producer> quantitySortedList = new ArrayList<>(producers);
        Map<Integer, Double> producerCosts = new HashMap<>();

        quantitySortedList.sort(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                 if (Double.valueOf(o1.getEnergyPerDistributor())
                        .compareTo(Double.valueOf(o2.getEnergyPerDistributor())) != 0) {
                    return -1 * Double.valueOf(o1.getEnergyPerDistributor())
                            .compareTo(Double.valueOf(o2.getEnergyPerDistributor()));
                } else if (Double.valueOf(o1.getId()).compareTo(Double.valueOf(o2.getId())) != 0) {
                    return Double.valueOf(o1.getId()).compareTo(Double.valueOf(o2.getId()));
                }
                return 0;
            }
        });

        int energyQuantity = 0;
        for (int i = 0; i < quantitySortedList.size(); i++) {
            if (energyQuantity >= distributor.getEnergyNeededKW()) {
                break;
            }
            energyQuantity += quantitySortedList.get(i).getEnergyPerDistributor();
            int producerId = quantitySortedList.get(i).getId();
            Double producerCost = quantitySortedList.get(i).getEnergyPerDistributor()
                    * quantitySortedList.get(i).getPriceKW();
            producerCosts.put(producerId, producerCost);

            //add the distributor to the producer's observer list
            quantitySortedList.get(i).addObserverDistributor(distributor);
        }
        return producerCosts;
    }

    public String getLabel() {
        return label;
    }
}
