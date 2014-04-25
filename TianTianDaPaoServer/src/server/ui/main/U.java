package server.ui.main;

import org.eclipse.swt.widgets.Display;

import util.DateUtil;
import util.Statistics;

public class U {
	private static String str;
	static Runnable r=new Runnable() {
		
		@Override
		public void run() {
		
			DaPaoMain.text.append("================================================\r\n");
			DaPaoMain.text.append(DateUtil.getCurDate()+":"+str+"\r\n");
			
		}
	};
	static Runnable r1=new Runnable() {
		
		@Override
		public void run() {
			DaPaoMain.lblip.setText(Statistics.INDEPENDENT_IP_BY_DAY+"");
			DaPaoMain.lblip_1.setText(Statistics.INDEPENDENT_IP_TOTAL+"");
		}
	};
	public static void info(String str)
	{
		DaPaoMain.text.append("================================================\r\n");
		DaPaoMain.text.append(DateUtil.getCurDate()+":"+str+"\r\n");
	}
	public static void infoQueue(String str)
	{
		U.str=str;
		Display.getDefault().asyncExec(r);
	}
	public static void infoQueueIP()
	{
		Display.getDefault().asyncExec(r1);
	}
	
}
