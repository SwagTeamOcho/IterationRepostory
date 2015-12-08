import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize {

	public Serialize(){
		
	}
	
	public void doSerialize(String s, Object o){
		try {
			FileOutputStream fileOut = new FileOutputStream(s + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + s + ".ser");
		} catch(IOException i){
			i.printStackTrace();
		}
	}
	
	public Object deSerialize(String s){
		Object m = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(s + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			m = in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();

		}catch(ClassNotFoundException c)
		{
			System.out.println("Map class not found");
			c.printStackTrace();

		}
		return m;
	}
}
