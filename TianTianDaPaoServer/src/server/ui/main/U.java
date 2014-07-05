package server.ui.main;

import org.eclipse.swt.widgets.Display;

import util.DateUtil;
import util.Statistics;

public class U {
	private static String str;
	static Runnable r=new Runnable() {
		
		@Override
		public void run() {
		
			DaPaoMain4321.text.append("================================================\r\n");
			DaPaoMain4321.text.append(DateUtil.getCurDate()+":"+str+"\r\n");
			
		}
	};
	static Runnable r1=new Runnable() {
		
		@Override
		public void run() {
			DaPaoMain4321.lblip.setText(Statistics.INDEPENDENT_IP_BY_DAY+"");
			DaPaoMain4321.lblip_1.setText(Statistics.INDEPENDENT_IP_TOTAL+"");
		}
	};
	public static void info(String str)
	{
//		DaPaoMain.text.append("================================================\r\n");
		System.out.println("================================================\r\n");
		System.out.println(DateUtil.getCurDate()+":"+str+"\r\n");
//		DaPaoMain.text.append(DateUtil.getCurDate()+":"+str+"\r\n");
	}
	public static void infoQueue(String str)
	{
//		U.str=str;
		System.out.println(str);
//		Display.getDefault().asyncExec(r);
	}
	public static void infoQueueIP()
	{
		
//		Display.getDefault().asyncExec(r1);
	}
	
}
