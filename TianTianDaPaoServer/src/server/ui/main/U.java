package server.ui.main;

import org.eclipse.swt.widgets.Display;

import util.DateUtil;

public class U {
	private static String str;
	static Runnable r=new Runnable() {
		
		@Override
		public void run() {
			DaPaoMain.text.append("================================================\r\n");
			DaPaoMain.text.append(DateUtil.getCurDate()+":"+str+"\r\n");
			
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
	
}
