Author: Tudor Hermenean, 324CD

    Description of the overall function of the project:

        In the main method located in the Main class the class Input parses the input from
        the JSON files and populates various lists of various objects.

        The data is then fed to the "runSimulation(...)"" method from the Simulation class where
        all the simulation runs and the data is being handled.

        The simulation starts with some initialisations where "elligible..." lists are formed to
        keep track of which entities are not bankrupt and out of the simulation. The rest of the
        simulation is all commented in the code and their functionalities explained. It simulates
        an energetic distribution network and the order of action is as follows:
            0.Dissolve expired contracts
            1.Make monthly changes
            2.Pay consumers
            3.Renew contracts or create new ones based on new offers
            4.Apply contracts or debts to entities and dissolve contracts
            with bankrupt consumers
            5.Tax distributors
            6.Remove bankrupt Consumers and Distributors from the simulation
            and dissolve contracts with bankrupt distributors

        When the simulation ends and changes were made to the data, it is then sent to an output
        method in the Output class which writes it back into JSON format.

    Factory patterns used in:
        - ConsumerFactory class
        - CostChangeFactory class
        - DistributorFactory class
        - MonthlyUpdateFactory class

    Singleton patterns used in:
        - Input class
        - Output class
        - Simulation class

    Nomenclature:
        - "dissolve a contract" - means to remove it from the simulation by removing it from the list
        - "pay consumers" - means to add their monthly income to their budget
        - "tax the distributors" - means to subtract their monthly expenses from their budget
        - "consumer tax"(in the context of a contract) - refers to the amount of money a consumer has
        signed to pay in a contract
        - "sign a contract" - means to create a contract between a consumer and a distributor

