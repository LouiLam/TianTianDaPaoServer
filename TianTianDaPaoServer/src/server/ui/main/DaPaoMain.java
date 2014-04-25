package server.ui.main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.LoginMain;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.FileUtil;
import org.eclipse.swt.widgets.Label;

public class DaPaoMain extends ApplicationWindow {

	/**
	 * Create the application window,
	 */
	public DaPaoMain() {
		super(null);
		setShellStyle(SWT.CLOSE | SWT.TITLE);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();
	}

	Text pwd_text;
	Combo combo;
	Button btntxt ;
	public static Text text;
	public static Label lblip,lblip_1;
	private Label label;
	private Label label_1;
	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

	
		

		final Button setup = new Button(container, SWT.MULTI);
		setup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoginMain.main(null);
				
			
				setup.setEnabled(false);
				setup.setSelection(false);
//				btntxt.setEnabled(false);
//				setEnabled(false);
//				if (combo.getText() == null
//						|| combo.getText().trim().length() == 0
//						|| pwd_text.getText() == null
//						|| pwd_text.getText().trim().length() == 0) {
//					MessageBox mb = new MessageBox(Login.this.getShell(),
//							SWT.ICON_INFORMATION | SWT.OK);
//					mb.setMessage("\u5e10\u53f7\u6216\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");
//					mb.open();
//					return;
//				}
//				httpPost();
//				Display.getCurrent().dispose();
//				Main main = new Main();
//				main.setBlockOnOpen(true);
//				main.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		setup.setBounds(426, 365, 86, 42);
		setup.setText("启动服务器");
		
		text = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI| SWT.V_SCROLL);
		text.setBounds(0, 0, 626, 349);
		
		 btntxt = new Button(container, SWT.TOGGLE);
		btntxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
       
            try {
            	FileUtil.logWriteUtil(text.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
          
			}
		});
		btntxt.setText("写输出到txt");
		btntxt.setBounds(10, 365, 72, 42);
		
		Button button = new Button(container, SWT.TOGGLE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				
			}
		});
		button.setText("清空输出");
		button.setBounds(88, 365, 86, 42);
		
		Button button_1 = new Button(container, SWT.TOGGLE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					LoginMain.ReloadConfig();
				} catch (DocumentException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setText("重新载入配置文件");
		button_1.setBounds(518, 365, 108, 42);
		
		 lblip = new Label(container, SWT.NONE);
		lblip.setBounds(268, 365, 115, 12);
		lblip.setText("当天独立ip访问人数");
		
		 lblip_1 = new Label(container, SWT.NONE);
		lblip_1.setText("累计独立ip访问人数");
		lblip_1.setBounds(268, 395, 115, 12);
		
		label = new Label(container, SWT.NONE);
		label.setBounds(180, 365, 82, 12);
		label.setText("当天访问人数：");
		
		label_1 = new Label(container, SWT.NONE);
		label_1.setBounds(180, 395, 82, 12);
		label_1.setText("累计访问人数：");

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DaPaoMain window = new DaPaoMain();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("天天打炮服务端");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(733, 492);
	}

	public void httpPost() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"http://www.zgmllm.com/yxlm/member/index_do.php?fmdo=login&dopost=login");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userid", combo.getText()));
		nvps.add(new BasicNameValuePair("pwd", pwd_text.getText()));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse httppHttpResponse2 = httpClient
					.execute(httpPost);
//			System.out.println(httppHttpResponse2.getStatusLine()
//					.getStatusCode() == HttpStatus.SC_OK);
			if (httppHttpResponse2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String entity = EntityUtils.toString(httppHttpResponse2
						.getEntity());
//				System.out.println(entity.indexOf("<html>"));
//				System.out.println(entity);
//				int end = entity.indexOf("<html>");
//				System.out.println(entity.substring(0, end));
				MessageBox mb = new MessageBox(DaPaoMain.this.getShell(),
						SWT.ICON_INFORMATION | SWT.OK);
				mb.setMessage(entity);// ���ص�¼�ַ��ɷ���˴�����
				mb.open();
			}
			else
			{
				MessageBox mb = new MessageBox(DaPaoMain.this.getShell(),
				SWT.ICON_INFORMATION | SWT.OK);
				mb.setMessage("error!StatusCode:"+httppHttpResponse2.getStatusLine().getStatusCode());// http״̬�����
				mb.open();
			}
			httppHttpResponse2.close();
			httpClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
