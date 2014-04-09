package client.buy_items;

public class BuyItems {
	public int id;
	public String des;
	//数据库对应字段名
	public String value;
	//消耗
	public long consume_ugold,consume_ucharge,consume_score,consume_diamond;
@Override
public String toString() {
	return "id:"+id+",des:"+des+",value:"+value;
}
}
