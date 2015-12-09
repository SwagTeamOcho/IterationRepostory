package test;
import src.*;
public class TestNodes {

	//Test map nodes
    Node one = new Node(1, 9, "Exit");
    Node two = new Node(3, 9);
    Node three = new Node(3, 8, "101");
    Node four = new Node(9, 9);
    Node five = new Node(9, 8, "102");
    Node six = new Node(15, 9);
	Node seven = new Node (15, 8, "103");
	Node eight = new Node (19, 9);
	Node nine = new Node (19, 8, "104");
	Node ten = new Node (26, 9);
	Node eleven = new Node (26, 8, "104");
	Node twelve = new Node (28, 9);
	Node thirteen = new Node (28, 1, "Exit");
	Node fourteen = new Node (28, 19);
	Node fifteen = new Node (26, 19);
	Node sixteen = new Node (26, 18, "105");
	Node seventeen = new Node (19, 19);
	Node eighteen = new Node (19, 18, "105");
	Node nineteen = new Node (9, 19);
	Node twenty = new Node (3, 19);
	Node twentyone = new Node (3, 18, "107");
	Node twentytwo = new Node (9, 15);
	Node twentythree = new Node (11, 15, "106");
    
    //Test map edges
    Edge a = new Edge (one, two, 2);
    Edge b = new Edge (two, three, 1);
    Edge c = new Edge (two, four, 6);
    Edge d = new Edge (four, five, 1);
    Edge e = new Edge (four, six, 6);
    Edge f = new Edge (six, seven, 1);
    Edge g = new Edge (six, eight, 4);
    Edge h = new Edge (eight, nine, 1);
    Edge i = new Edge (eight, ten, 7);
    Edge j = new Edge (ten, eleven, 1);
    Edge k = new Edge (ten, twelve, 2);
    Edge l = new Edge (twelve, thirteen, 8);
    Edge m = new Edge (twelve, fourteen, 10);
    Edge n = new Edge (fourteen, fifteen, 2);
    Edge o = new Edge (fifteen, sixteen, 1);
    Edge p = new Edge (fifteen, seventeen, 7);
    Edge q = new Edge (seventeen, eighteen, 1);
    Edge r = new Edge (seventeen, nineteen, 10);
    Edge s = new Edge (nineteen, twenty, 6);
    Edge t = new Edge (twenty, twentyone, 1);
    Edge u = new Edge (nineteen, twentytwo, 4);
    Edge v = new Edge (twentytwo, twentythree, 2);
    Edge w = new Edge (twentytwo, four, 6);
    
    
}