import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize{
	Thread t;

	public Serialize(){

	}

	public void doSerialize(String s, Object object){

		Thread t = new Thread(null, null, "TT", 1000000) {
			@Override
			public void run() {
				try {
					FileOutputStream fileOut = new FileOutputStream(s + ".ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(object);
					out.close();
					fileOut.close();
					System.out.println("Serialized data is saved in " + s + ".ser");
				} catch(IOException i){
					i.printStackTrace();
				}
			}
		};	
		t.start();
	}

	public Object deserialize(String s){
		Object m = null;
		Thread t = new Thread(null, null, "TT", 1000000) {
			@Override
			public void run() {
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
			}
		};
			t.start();
			return m;
		}
	}
