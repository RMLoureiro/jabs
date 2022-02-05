package jabs.scenario;

import de.siegmar.fastcsv.writer.CsvWriter;
import jabs.network.BlockchainNetwork;
import jabs.network.EthereumGlobalBlockchainNetwork;
import jabs.node.nodes.BlockchainNode;

import static jabs.event.EventFactory.createBlockGenerationEvents;
import static jabs.event.EventFactory.createTxGenerationEvents;

public class NormalEthereumNetworkScenario extends AbstractScenario {
    long simulationTime = 0;

    private final int numOfMiners;
    private final int numOfNonMiners;
    private final long simulationStopTime;
    private final long txGenerationRate;
    private final long blockGenerationRate;

    public NormalEthereumNetworkScenario(long seed, CsvWriter outCSV, int numOfMiners, int numOfNonMiners, long simulationStopTime, long txGenerationRate, long blockGenerationRate) {
        super(seed, outCSV);
        this.numOfMiners = numOfMiners;
        this.numOfNonMiners = numOfNonMiners;
        this.simulationStopTime = simulationStopTime;
        this.txGenerationRate = txGenerationRate;
        this.blockGenerationRate = blockGenerationRate;
    }

    @Override
    public void createNetwork() {
        this.network = new EthereumGlobalBlockchainNetwork(random);
    }

    @Override
    protected void insertInitialEvents() {
        createTxGenerationEvents(simulator, random, network, ((int) (simulationStopTime*txGenerationRate)), (long)(1000/txGenerationRate));
        createBlockGenerationEvents(simulator,random, (BlockchainNetwork) network, ((int) (simulationStopTime*blockGenerationRate)), (long)(1000/blockGenerationRate));
    }

    @Override
    public boolean simulationStopCondition() {
        if (simulator.getCurrentTime() - 10000 >= simulationTime) {
            simulationTime = simulator.getCurrentTime();
            System.out.printf("\rsimulation time: %s, number of already seen blocks for miner 0: %s\n",
                    simulationTime,
                    ((BlockchainNode) network.getAllNodes().get(0)).numberOfAlreadySeenBlocks());
        }
        return (simulator.getCurrentTime() > simulationStopTime*1000);
    }

    @Override
    public void finishSimulation() {

    }

    @Override
    protected boolean csvOutputConditionBeforeEvent() {
        return false;
    }

    @Override
    protected boolean csvOutputConditionAfterEvent() {
        return false;
    }

    @Override
    protected String[] csvHeaderOutput() {
        return new String[0];
    }

    @Override
    protected String[] csvLineOutput() {
        return new String[0];
    }
}
