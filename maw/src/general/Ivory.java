package general;

import java.util.LinkedList;

public class Ivory {

	private LinkedList<Node> nodes;
	private LinkedList<NodeConnection> connections;

	public Ivory() {

		nodes = new LinkedList<Node>();
		connections = new LinkedList<NodeConnection>();

	}

	public void addNode(Node node) {

		nodes.add(node);

	}

	public LinkedList<Node> getNodesClone() {
		return new LinkedList<Node>(nodes);
	}

	public LinkedList<NodeConnection> getConnectionsClone() {
		return new LinkedList<NodeConnection>(connections);
	}

	public void connect(Node node1, Node node2) {

		NodeConnection conn = new NodeConnection(node1, node2);

		connections.add(conn);

	}

}
