package util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jTest {
	/**
	 * 纯xml字符串解析
	 * 
	 * @param strResult
	 */
	public static Document  getDocumentByString(String strResult) {
		Document documentCharge=null;
		try {
			 documentCharge = DocumentHelper.parseText(strResult);
			// 解析但节点示例
			// <return>
			// <result>0</result>
			// <sign>163500DE4E885A9468644423A3433C58</sign>
			// </return>
//			int result = Integer.parseInt(documentCharge.selectSingleNode(
//					"/return/result").getText());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return documentCharge;
	}
	/**
	 * xml文件解析
	 * @param fileAddress
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocumentByFileAddress(String fileAddress) throws DocumentException{
//		config/GlobalConfig.xml
		Document document;
		SAXReader saxReader = new SAXReader();
		document = saxReader.read(new File(fileAddress));
		return document;
	}
	/**
	 * XML文件传换为String字符串
	 * @return
	 */
	public static String xmlToString()
	{
		Document document;
		try {
			document = getDocumentByFileAddress("config/LoginRewardConfig.xml");
			return document.asXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		Document document=getDocumentByString("<SyncAppOrderReq xmlns="+"\"http://www.monternet.com/dsmp/schemas/\""+">"
+"<TransactionID>CSSP15667351</TransactionID>"+"<MsgType>SyncAppOrderReq</MsgType>"+
				"<Version>1.0.0</Version><Send_Address><DeviceType>200</DeviceType>"+
"<DeviceID>CSSP</DeviceID></Send_Address><Dest_Address><DeviceType>1002</DeviceType>"+
				"<DeviceID>f0_0</DeviceID></Dest_Address><OrderID>11140429171021538073</OrderID>"+
"<CheckID>0</CheckID><ActionTime>20140429171021</ActionTime><ActionID>1</ActionID><MSISDN></MSISDN>"+
				"<FeeMSISDN>1D7ECD7378E68CA3</FeeMSISDN><AppID>300008247532</AppID><PayCode>30000824753201</PayCode>"
+"<TradeID>C5A0057F859B596DEAF10E3327AF551A</TradeID><Price>200</Price><TotalPrice>200</TotalPrice>"+
				"<SubsNumb>1</SubsNumb><SubsSeq>1</SubsSeq><ChannelID>0000000000</ChannelID><ExData>10000045</ExData>"+
"<OrderType>0</OrderType><MD5Sign>6E2580DCCA75BDEB48D03C77FD697A53</MD5Sign><OrderPayment>1</OrderPayment></SyncAppOrderReq>"
);
		if(document==null)
		{
			System.out.println("11");
		}
		Element root = (Element) document.getRootElement();
		String otherSide=root.element("MD5Sign").getText();
		System.out.println(otherSide);
	}
    public static void writeXmlTest(){  
//        try {  
            //DocumentHelper提供了创建Document对象的方法  
            Document document = DocumentHelper.createDocument();  
            //添加节点信息  
            Element rootElement = document.addElement("SyncAppOrderResp");  
            //这里可以继续添加子节点，也可以指定内容  
            rootElement.setText("这个是module标签的文本信息");  
            Element element = rootElement.addElement("module");  
              
            Element nameElement = element.addElement("name");  
            Element valueElement = element.addElement("value");  
            Element descriptionElement = element.addElement("description");  
            nameElement.setText("名称");  
            nameElement.addAttribute("language", "java");//为节点添加属性值  
            valueElement.setText("值");  
            valueElement.addAttribute("language", "c#");  
            descriptionElement.setText("描述");  
            descriptionElement.addAttribute("language", "sql server");  
            System.out.println(document.asXML()); //将document文档对象直接转换成字符串输出  

            
            //dom4j提供了专门写入文件的对象XMLWriter，如果要保存写入的字符串xml格式文件，请取消注释下面的代码
//          Writer fileWriter = new FileWriter("c:\\module.xml");  
//            XMLWriter xmlWriter = new XMLWriter(fileWriter);  
//            xmlWriter.write(document);  
//            xmlWriter.flush();  
//            xmlWriter.close();  
//            System.out.println("xml文档添加成功！");  
//        } 
//        catch (IOException e) {  
//            e.printStackTrace();  
//        }  
    }  
    public static String writeXml(String TransactionID){  
//        System.out.println(AES.getMD5Str("11140429152613077968#0000000000#30000824753201#F5A12F2B2F319F93").toUpperCase());
//      try {  
          //DocumentHelper提供了创建Document对象的方法  
          Document document = DocumentHelper.createDocument();  
          //添加节点信息  
          Element rootElement = document.addElement("SyncAppOrderResp","http://www.monternet.com/dsmp/schemas/");
          Element elementTransactionID = rootElement.addElement("TransactionID");  
          elementTransactionID.setText(TransactionID);
          Element elementMsgType = rootElement.addElement("MsgType");  
          elementMsgType.setText("SyncAppOrderResp");
          Element elementVersion = rootElement.addElement("Version");  
          elementVersion.setText("1.0.0");
          Element elementhRet = rootElement.addElement("hRet");  
          elementhRet.setText("0");
         return document.asXML(); //将document文档对象直接转换成字符串输出  

          
          //dom4j提供了专门写入文件的对象XMLWriter，如果要保存写入的字符串xml格式文件，请取消注释下面的代码
//        Writer fileWriter = new FileWriter("c:\\module.xml");  
//          XMLWriter xmlWriter = new XMLWriter(fileWriter);  
//          xmlWriter.write(document);  
//          xmlWriter.flush();  
//          xmlWriter.close();  
//          System.out.println("xml文档添加成功！");  
//      } 
//      catch (IOException e) {  
//          e.printStackTrace();  
//      }  
  }  
}
