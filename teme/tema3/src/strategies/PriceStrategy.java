package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.*;

public class PriceStrategy implements DistributorStrategy{
    private final String label = "PRICE";
    /**
     * Price strategy constructor
     */
    public PriceStrategy() {}

    /**
     * Price strategy implementation, prioritises price and quantity
     * in that order. Works by sorting the producers
     * list and then choosing them in the sorted order.
     * @param distributor
     * @param producers
     * @return a Map of producers id and their costs
     */
    @Override
    public Map<Integer, Double> chooseProducers(final Distributor distributor,
                                                final List<Producer> producers) {
        List<Producer> priceSortedList = new ArrayList<>(producers);
        Map<Integer, Double> producerCosts = new HashMap<>();

        priceSortedList.sort(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                 if (Double.valueOf(o1.getPriceKW())
                        .compareTo(Double.valueOf(o2.getPriceKW())) != 0) {
                    return Double.valueOf(o1.getPriceKW())
                            .compareTo(Double.valueOf(o2.getPriceKW()));
                    } else if (Double.valueOf(o1.getEnergyPerDistributor())
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
        for (int i = 0; i < priceSortedList.size(); i++) {
            if (energyQuantity >= distributor.getEnergyNeededKW()) {
                break;
            }
            energyQuantity += priceSortedList.get(i).getEnergyPerDistributor();
            int producerId = priceSortedList.get(i).getId();
            Double producerCost = priceSortedList.get(i).getEnergyPerDistributor()
                    * priceSortedList.get(i).getPriceKW();
            producerCosts.put(producerId, producerCost);

            //add the distributor to the producer's observer list
            priceSortedList.get(i).addObserverDistributor(distributor);
        }
        return producerCosts;
    }

    public String getLabel() {
        return label;
    }
}
