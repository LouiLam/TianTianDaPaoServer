package server.ui.main;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;

public class Main extends ApplicationWindow {

	/**
	 * Create the application window,
	 */
	public Main() {
		super(null);
		createActions();
		addCoolBar(SWT.FLAT);
		addMenuBar();
		addStatusLine();

	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setBounds(10, 0, 716, 388);

		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u9996\u9875");

		// Browser browser = new Browser(tabFolder, SWT.NONE);
		// browser.setUrl("www.baidu.com");
		// tabItem.setControl(browser);
		Composite compositeMax = new Composite(tabFolder, SWT.NONE);
		compositeMax.setLayout(new FillLayout(SWT.VERTICAL));

		Composite composite = new Composite(compositeMax, SWT.NONE);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		composite.setLayout(fl_composite);
		Label order = new Label(composite, SWT.NONE);
		order.setText("\u6628\u65E5\u65B0\u589E\u8BA2\u5355\uFF1A");// ��������������

		Label value = new Label(composite, SWT.NONE);
		value.setText("0");// 0

		Composite composite_1 = new Composite(compositeMax, SWT.NONE);

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(10, 4, 54, 12);
		lblNewLabel.setText("\u7528\u6237id:");

		Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		lblNewLabel_1.setBounds(189, 4, 54, 12);
		lblNewLabel_1.setText("\u7528\u6237\u6635\u79F0\uFF1A");

		Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
		lblNewLabel_2.setBounds(10, 22, 54, 12);
		lblNewLabel_2.setText("\u53EF\u7528\u8D44\u91D1");

		Label label = new Label(composite_1, SWT.NONE);
		label.setBounds(189, 22, 54, 12);
		label.setText("\u51BB\u7ED3\u8D44\u91D1");

		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.setBounds(355, 17, 72, 22);
		btnNewButton.setText("\u7ACB\u5373\u5145\u503C");

		Button button = new Button(composite_1, SWT.NONE);
		button.setBounds(448, 17, 72, 22);
		button.setText("\u7ACB\u5373\u63D0\u73B0");

		Composite composite_2 = new Composite(compositeMax, SWT.NONE);

		Button btnNewButton_1 = new Button(composite_2, SWT.NONE);
		btnNewButton_1.setBounds(0, 0, 72, 22);
		btnNewButton_1.setText("\u6211\u60F3\u627E\u9AD8\u624B");

		Button btnNewButton_2 = new Button(composite_2, SWT.NONE);
		btnNewButton_2.setBounds(269, 0, 72, 22);
		btnNewButton_2.setText("\u6211\u8981\u63A5\u8BA2\u5355");

		Composite composite_3 = new Composite(compositeMax, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group group = new Group(composite_3, SWT.NONE);
		group.setText("\u53D1\u5E03\u8005\u6D41\u7A0B");
		group.setLayout(new FillLayout(SWT.VERTICAL));
		
		Button btnNewButton_3 = new Button(group, SWT.NONE);
		btnNewButton_3.setText("1.\u6635\u79F0\u4FEE\u6539");
		
		Button btnNewButton_4 = new Button(group, SWT.NONE);
		btnNewButton_4.setText("2.\u5BFB\u627E\u5458\u5DE5");
		
		Button btnNewButton_5 = new Button(group, SWT.NONE);
		btnNewButton_5.setText("3.\u589E\u52A0\u5458\u5DE5");
		
		Button btnNewButton_6 = new Button(group, SWT.NONE);
		btnNewButton_6.setText("4.\u53D1\u5355\u7BA1\u7406");
		
		Button btnNewButton_7 = new Button(group, SWT.NONE);
		btnNewButton_7.setText("5.\u53D1\u5E03\u8BA2\u5355");

		Group group_1 = new Group(composite_3, SWT.NONE);
		group_1.setText("\u53D1\u5E03\u8005");

		Group group_2 = new Group(composite_3, SWT.NONE);
		group_2.setText("\u63A5\u5355\u8005\u6D41\u7A0B");

		Group group_3 = new Group(composite_3, SWT.NONE);
		group_3.setText("\u63A5\u5355\u8005");
		tabItem.setControl(compositeMax);

		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u4E2A\u4EBA\u8BBE\u7F6E");

		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("\u53D1\u5355\u7BA1\u7406");

		TabItem tabItem_3 = new TabItem(tabFolder, SWT.NONE);
		tabItem_3.setText("\u8D22\u52A1\u7BA1\u7406");

		TabItem tabItem_4 = new TabItem(tabFolder, SWT.NONE);
		tabItem_4.setText("\u63A5\u5355\u7BA1\u7406");

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
			Main window = new Main();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
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
		newShell.setText("\u6211\u662F\u738B\u8005");
		newShell.addShellListener(new ShellAdapter() {
			// public void shellClosed(ShellEvent e) {
			//
			// MessageBox mb = new MessageBox(getParentShell(),
			// SWT.ICON_QUESTION | SWT.OK);
			// mb.setMessage("\u6CE8\u518C\u6210\u529F");// ע��ɹ�
			// mb.setText("Confirm Exit");
			// mb.setMessage("Are you sure you want to exit?");
			// mb.open();
			// }
		});
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(744, 460);
	}
}
