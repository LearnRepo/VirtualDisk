package diskvirtual;

import java.io.IOException;

public class MainReadWrite {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			new MakeDisk("vd1.shd", 1, "GB");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		new AddFile().ReadDisk();
	}

}
