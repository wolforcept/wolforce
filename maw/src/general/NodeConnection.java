package general;

public class NodeConnection {

	private Node node1, node2;
	private double strength;

	public NodeConnection(Node node1, Node node2) {

		this.node1 = node1;

		this.node2 = node2;

		this.strength = Math.random();

	}

	public Node getNode1() {
		return node1;
	}

	public Node getNode2() {
		return node2;
	}

	public double getStrength() {
		return strength;
	}

}
