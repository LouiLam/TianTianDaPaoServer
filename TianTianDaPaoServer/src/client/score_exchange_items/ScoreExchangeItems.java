package client.score_exchange_items;

public class ScoreExchangeItems {
	public int id;
	public String des;
	//数据库对应字段名
	public String value;
	//消耗积分
	public int consume;
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "id:"+id+",des:"+des+",value:"+value+",consume:"+consume;
}
}
