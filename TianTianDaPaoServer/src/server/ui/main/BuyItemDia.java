package server.ui.main;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class BuyItemDia extends Dialog {
	private Text id_text;
	private Text pwd_text;
	private Text pwdok_text;
	private Text text_3;
	private Text text_4;
	private Text text_5;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public BuyItemDia(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM);
	}

	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("\u6CE8\u518C");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		Table table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setBounds(10, 10, 582, 237);
		Font font = new Font(parent.getDisplay(), "Arial", 12, SWT.NORMAL);
		table.setFont(font);
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setWidth(415);
		TableColumn column1 = new TableColumn(table, SWT.NONE);
		column1.setWidth(80);
		TableColumn column2 = new TableColumn(table, SWT.NONE);
		column2.setWidth(80);
//		gridLayout.numColumns = 3;

		
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pwd_text.getText() == null
						|| pwd_text.getText().length() == 0
						|| pwdok_text.getText() == null
						|| pwdok_text.getText().length() == 0) {
					MessageBox mb = new MessageBox(
							BuyItemDia.this.getParentShell(), SWT.ICON_INFORMATION
									| SWT.OK);
					mb.setMessage("\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");// ���벻��Ϊ��
					mb.open();
					return;
				}
				if (pwdok_text.getText().equals(pwd_text.getText())) {
					httpPost();
				} else {
					MessageBox mb = new MessageBox(
							BuyItemDia.this.getParentShell(), SWT.ICON_INFORMATION
									| SWT.OK);
					mb.setMessage("\u5bc6\u7801\u524d\u540e\u4e0d\u4e00\u81f4");// ����ǰ��һ��
					mb.open();
				}
			}
		});
		button.setText("\u6CE8\u518C");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("\u53D6\u6D88");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(680, 322);
	}

	public void httpPost() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"http://www.zgmllm.com/yxlm/member/reg_new.php?dopost=regbase&step=1");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userid", "111"));
		nvps.add(new BasicNameValuePair("userpwd", "111"));
		nvps.add(new BasicNameValuePair("userpwdok", "111"));
		nvps.add(new BasicNameValuePair("email", "111"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse httppHttpResponse2 = httpClient
					.execute(httpPost);
			System.out.println(httppHttpResponse2.getStatusLine());
			System.out.println(EntityUtils.toString(httppHttpResponse2
					.getEntity()));
			MessageBox mb = new MessageBox(BuyItemDia.this.getParentShell(),
					SWT.ICON_INFORMATION | SWT.OK);
			mb.setMessage("\u6CE8\u518C\u6210\u529F");// ע��ɹ�
			mb.open();
			httppHttpResponse2.close();
			httpClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
