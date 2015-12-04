import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
public class Djikstra  {

	
	public LinkedList<Node> navigate(Node start, Node goal){
		if(start == null || goal == null){
			throw new IllegalArgumentException();
		}
		
		Comparator<Node> comparator = new NodeComparator();
		Node current;
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(100, comparator);
		start.setPriority(0);
		frontier.add(start);
		LinkedList<Node> temp = new LinkedList<Node>();
		LinkedList<Node> path = new LinkedList<Node>();
		if(start.equals(goal)){
			path.add(start);
			return path;
			}
		LinkedList<Node> possibleNodes = new LinkedList<Node>();
		HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();
		HashMap<Node, Integer> costSoFar = new HashMap<Node, Integer>();
		cameFrom.put(start, null);
		costSoFar.put(start, 0);
		
		while (!(frontier.isEmpty())){
			current = frontier.poll(); // possibly use remove
			if(current.equals(goal)){
				break;
			}
			possibleNodes = current.getPossibleNodes();
			Node next;
			for(int i = 0; i < possibleNodes.size(); i++){
				
				next = possibleNodes.get(i);
				Integer newCost = costSoFar.get(current) + current.getCost(next);
				if ((!costSoFar.containsKey(next)) ||  (newCost < costSoFar.get(next))){
					//costSoFar.remove(next);
					costSoFar.put(next, newCost);
					next.setPriority(newCost);
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
			
		}
		current = goal;
		temp.add(current);
		if(current != null){System.out.println("current not null");}
		if(start != null){System.out.println("start not null");}
		
		while (!(current.equals(start))){
			if(cameFrom.get(current) == null){break;}
			current = cameFrom.get(current);
			temp.add(current);
		}
		
		
		
		for(int i = temp.size() - 1; i >= 0; i--){
			path.add(temp.get(i));
		}
		
		
		
		
		return path;
	}
	
	public LinkedList<Node> nearestBathroom(Node start){
		if(start == null){
			throw new IllegalArgumentException();
		}
		
		Comparator<Node> comparator = new NodeComparator();
		Node current = null;
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(100, comparator);
		start.setPriority(0);
		frontier.add(start);
		LinkedList<Node> temp = new LinkedList<Node>();
		LinkedList<Node> path = new LinkedList<Node>();
		if(start.getType() == (NodeType.MBATHROOM) || start.getType() == (NodeType.FBATHROOM)){
			path.add(start);
			return path;
			}
		LinkedList<Node> possibleNodes = new LinkedList<Node>();
		HashMap<Node, Node> cameFrom = new HashMap<Node, Node>();
		HashMap<Node, Integer> costSoFar = new HashMap<Node, Integer>();
		cameFrom.put(start, null);
		costSoFar.put(start, 0);
		
		while (!(frontier.isEmpty())){
			current = frontier.poll(); // possibly use remove
			if(current.getType() == (NodeType.MBATHROOM) || current.getType() == (NodeType.FBATHROOM)){
				break;
			}
			possibleNodes = current.getPossibleNodes();
			Node next;
			for(int i = 0; i < possibleNodes.size(); i++){
				
				next = possibleNodes.get(i);
				Integer newCost = costSoFar.get(current) + current.getCost(next);
				if ((!costSoFar.containsKey(next)) ||  (newCost < costSoFar.get(next))){
					//costSoFar.remove(next);
					costSoFar.put(next, newCost);
					next.setPriority(newCost);
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
			
		}

		temp.add(current);
		while (!(current.equals(start))){
			if(cameFrom.get(current) == null){break;}
			current = cameFrom.get(current);
			temp.add(current);
		}
		
		
		
		for(int i = temp.size() - 1; i >= 0; i--){
			path.add(temp.get(i));
		}
		
		
		
		
		return path;
	}
	
	public static int getDistance(LinkedList<Node> path){
		if(path.size() <= 1){
			throw new NoPathException("NO WAY");
		}
		System.out.println("path size = " + path.size());
		int total = 0;
		int nextCost = 0;
		for(int i = 0; i < path.size() - 1; i++){
			nextCost = path.get(i).getCost(path.get(i+1));
			System.out.println("nextCost = " + nextCost);
			if(nextCost<0){
				throw new NoPathException("NO WAY");
			}
			total+=nextCost;
		}
		return total;
	}
	

	
	public String gpsInstructions (LinkedList<Node> path){
		Node previous, current, next;
		String result = "";
		int counter = 0;
		String direction;
		if (path.size() == 0 || path == null){
			return "No path";
		}
		else if (path.size() == 1){
			return "You are already at your destination";
		}
		
		else{
			current = path.removeFirst();
			next = path.removeFirst();
			counter +=1;
			result += counter + ". " + "Walk " + current.getCost(next) + "\n";
		while(path.size() > 0){
			previous = current;
			current = next;
			next = path.removeFirst();
//			double a = Math.sqrt((Math.pow(next.getX() - previous.getX(),2)) + (Math.pow(next.getY() - previous.getY(),2)));
//			double b = Math.sqrt((Math.pow(next.getX() - current.getX(),2)) + (Math.pow(next.getY() - current.getY(),2)));
//			double c = Math.sqrt((Math.pow(current.getX() - previous.getX(),2)) + (Math.pow(current.getY() - previous.getY(),2)));
//			int angle = (int) Math.toDegrees(Math.acos((b*b + c*c - a*a)/(2*b*c)));
			int angle =  (int) Math.toDegrees((Math.atan2(next.getY()-current.getY(),next.getX()-current.getX()) -  Math.atan2(current.getY()-previous.getY(),current.getX()-previous.getX())));
			//result += "Angle = " + angle + " \n";
			if(angle < -65 && angle >  -115){
				counter +=1;
				result += counter + ". " +"Take a left and "; 
			}
			else if(angle > 65 && angle <  115){
				counter +=1;
				result += counter + ". " +"Take a right and "; 
			}
			else if(angle < 65 && angle > 10){
				counter +=1;
				result += counter + ". " +"Take a slight right and "; 
			}
			else if(angle < -10 && angle > -65){
				counter +=1;
				result += counter + ". " +"Take a slight left and ";
			}
			else if (angle == 180 || angle == -180){
				counter +=1;
				result += counter + ". " +"retrace your steps and contact whoever wrote this code, cause he screwed up, then ";
			}
			else if(angle > 115){
				counter +=1;
				result += counter + ". " +"Take a hard right and ";
			}
			else if(angle < -115){
				counter +=1;
				result += counter + ". " +"Take a hard left and ";
			}
			else if(angle < 10 && angle > -10){
				counter +=1;
				result += counter + ". " +"Keep straight and ";
			}
			result += "walk " + current.getCost(next) + " \n";
		}
		counter +=1;
		result += counter + ". " + "You have reached your destination \n";
		
		return result;
		}
	}
}