package org.hymer.sensitivewords;

import java.util.Set;



/**
 * 敏感词过滤对外提供方法类
 * 
 * @author hymer
 * 
 */
public class FinderUtil {

	public static final char ADD_FLAG = '+'; // 新增标记
	public static final char REMOVE_FLAG = '-'; // 删除标记



	/**
	 * 
	 * 找出文本中的敏感词
	 * 
	 * @author hymer
	 * @param text
	 * @return
	 */
	public static Set<String> find(String text) {
		return Finder.find(text);
	}

	/**
	 * 替换文本中的敏感词
	 * 
	 * @param text
	 *            含敏感词的文本
	 * @return
	 */
	public static String replace(String text) {
		return Finder.replace(text);
	}

	/**
	 * 替换文本中的敏感词
	 * 
	 * @param text
	 *            含敏感词的文本
	 * @param replacement
	 *            替换字符
	 * @return
	 */
	public static String replace(String text, Character replacement) {
		return Finder.replace(text, replacement);
	}

	/**
	 * 
	 * 过滤文本，并标记出敏感词，默认使用HTML中红色font标记
	 * 
	 * @author hymer
	 * @param text
	 * @return
	 */
	public static String filter(String text) {
		return Finder.filter(text);
	}

	/**
	 * 
	 * 过滤文本，并标记出敏感词
	 * 
	 * @author hymer
	 * @param text
	 * @param startTag
	 * @param endTag
	 * @return
	 */
	public static String filter(String text, String startTag, String endTag) {
		return Finder.filter(text, startTag, endTag);
	}

}
