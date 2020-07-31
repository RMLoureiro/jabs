package main.java.message;

import main.java.data.PBFTVote;
import main.java.node.nodes.Node;

public class VoteMessage<V extends PBFTVote> extends Message {
    private final V vote;

    public VoteMessage(int size, Node from, Node to, V vote, V vote1) {
        super(size, from, to);
        this.vote = vote1;
    }

    public V getVote() {
        return vote;
    }
}
