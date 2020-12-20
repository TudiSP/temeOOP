package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.Iterator;
import java.util.List;

public class DelayedContract extends Contract{

    public DelayedContract(int consumerId, int distributorId, int consumerTax,
                           int distributorRevenue, int contractLength) {
        super(consumerId, distributorId, consumerTax, distributorRevenue, contractLength);
    }

    public void applyDelay(Consumer consumer, Distributor distributor, Contract contract) {
        if (consumer.getBudget() - consumerTax - contract.consumerTax < 0) {
            consumer.setBankruptStatus(true);
        } else {
            consumer.setBudget(consumer.getBudget() - consumerTax);
            distributor.setBudget(distributor.getBudget() + consumerTax);
        }

        monthsLeft--;
    }

    public static void applyDelays(final List<Consumer> consumers,
                                      final List<Distributor> distributors,
                                      List<Contract> contracts,
                                      List<DelayedContract> delayedContracts) {

        Iterator<DelayedContract> i = delayedContracts.iterator();
        while (i.hasNext()){
            DelayedContract delayedContract = i.next();

            Contract contract = Utils.consumerIdToContractSearch(contracts, delayedContract.consumerId);
            Consumer consumer = Utils.idToConsumerSearch(consumers, delayedContract.consumerId);
            Distributor distributor = Utils.idToDistributorSearch(distributors, delayedContract.distributorId);
            delayedContract.applyDelay(consumer, distributor, contract);

            i.remove();
            }


    }

}
