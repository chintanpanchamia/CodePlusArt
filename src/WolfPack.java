import java.util.ArrayList;

public class WolfPack 
{
	ArrayList<Wolf> pack;
	WolfPack()
	{
		pack = new ArrayList<Wolf>();
		this.fillPack();
	}
	
	void fillPack()
	{
		
	}
	
	void run()
	{
		for(Wolf x: pack)
		{
			x.run();
		}
	}
	
	void follow()
	{
		
	}
}
