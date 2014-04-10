package client.role_upgrade;

import java.util.HashMap;

public class RoleUpgrade {
	public int id;
	public String name;
	public int max_level;
	//等级对应消耗
	public HashMap<Integer, Integer> map = new HashMap<>();
	public void count()
	{
		//30以下（包括30）使用金币
		map.put(1, 100);
		map.put(2, 300);
		map.put(3, 488);
		map.put(4, 588);
		map.put(5, 688);
		map.put(6, 800);
		map.put(7, 900);
		map.put(8, 1000);
		map.put(9, 1500);
		map.put(10, 2000);
		for (int i = 11; i < 31; i++) {
			map.put(i, 2500);
		}
		//30级以上20钻石
		if(max_level>30)
		{
			for (int i = 31; i < max_level+1; i++) {
				map.put(i, 20);
			}
		}
	}

}
