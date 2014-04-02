package util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {
	public void test(){
	ArrayList<Object>list=new ArrayList<Object>();
	JSONObject json =new JSONObject();
	JSONObject json1 =new JSONObject();
	JSONObject json2 =new JSONObject();	
	try {
		//鍏堟坊鍔爗"ret":0}
		json.put("ret", 0);
		list.add(json);
		//娣诲姞鎴块棿A鏉＄洰
		json1.put("id", 1);
		json1.put("rate", 1);
		json1.put("name", "mbni");
		list.add(json1);
		//娣诲姞鎴块棿B鏉＄洰	
		json2.put("id", 2);
		json2.put("name","nimab");
		list.add(json2);
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	JSONArray arr = new JSONArray(list);
	//杈撳嚭JSON鏍煎紡
	System.out.println(arr);
	
	/////////////////////////////////////////////////////////////////////////
	//浠ヤ笅鏄В鏋恓son
	JSONArray arr1 = null;
	try {
		arr1 = new JSONArray(arr.toString());
		System.out.println("arr1.length();"+arr1.length());
		for (int i = 0; i < arr1.length(); i++) {
			JSONObject obj=(JSONObject) arr1.get(i);
			System.out.println(obj.toString());
		
		
		}
	} catch (JSONException e) {
			e.printStackTrace();
	}}
}
