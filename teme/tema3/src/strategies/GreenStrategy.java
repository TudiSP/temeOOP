package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.*;

public final class GreenStrategy implements DistributorStrategy{
    private final String label = "GREEN";
    /**
     * Green strategy constructor
     */
    public GreenStrategy() {}

    /**
     * Green strategy implementation, prioritises renewable energy,
     * price and quantity in that order. Works by sorting the producers
     * list and then choosing them in the sorted order.
     * @param distributor
     * @param producers
     * @return a Map of producers id and their costs
     */
    @Override
    public Map<Integer, Double> chooseProducers(final Distributor distributor,
                                                final List<Producer> producers) {
        List<Producer> greenSortedList = new ArrayList<>(producers);
        Map<Integer, Double> producerCosts = new HashMap<>();

        greenSortedList.sort(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                /*int o1val, o2val;
                //we translate boolean values to integers so it can
                //be easier to work with
                if(o1.getEnergyType().isRenewable()) {
                    o1val = 1;
                } else {
                    o1val = 0;
                }

                if(o2.getEnergyType().isRenewable()) {
                    o2val = 1;
                } else {
                    o2val = 0;
                }

                if (o1val - o2val != 0) {
                    return o1val - o2val;
                } else*/ if (Double.valueOf(o1.getPriceKW())
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

        greenSortedList.sort((a,b) -> {
            if (a.getEnergyType().isRenewable() == true && b.getEnergyType().isRenewable() == false)
                return -1;
            else if (a.getEnergyType().isRenewable() == false && b.getEnergyType().isRenewable() == true)
                return 1;
            else return 0;
        } );

        int energyQuantity = 0;
        for (int i = 0; i < greenSortedList.size(); i++) {
            // if energy need is satisfied then exit loop
            if (energyQuantity >= distributor.getEnergyNeededKW()) {
                break;
            }
            energyQuantity += greenSortedList.get(i).getEnergyPerDistributor();
            int producerId = greenSortedList.get(i).getId();
            //calculate cost per producer
            Double producerCost = greenSortedList.get(i).getEnergyPerDistributor()
                    * greenSortedList.get(i).getPriceKW();
            producerCosts.put(producerId, producerCost);

            //add the distributor to the producer's observer list
            greenSortedList.get(i).addObserverDistributor(distributor);
        }
        return producerCosts;
    }

    public String getLabel() {
        return label;
    }
}
