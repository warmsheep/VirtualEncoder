package org.warmsheep.encoder.ic;
/**
 * 事务管理中的对象参数信息
 * @author yzyue
 *
 */
public interface TxnIC {
	
	/**
	 * <pre>
	 * 一、基本的常量
	 * 		该部分的常量，是在配置文件[02_txnmgr.xml]中配置的；
	 * 如果更改了配置文件中的相应内容，则此常量亦需要做修改。
	 * 		包括：
	 * 		1、SPACE
	 * 				用于对应配置文件中的space节点。
	 * 		2、QUEUE
	 * 				用于对应配置文件中的queue节点。
	 * </pre>
	 */
	public static final String SPACE = "tspace:hsm";
	public static final String QUEUE = "txnmgr"; 
	
	/**
	 * <pre>
	 * 二、经过Listener接口之后，产生的常量
	 * 		包括：
	 * 		1、MSG_TMS
	 * 				POS上送的报文对象----ISOMsg实例
	 * 		3、SOURCE
	 * 				返回报文给POS的处理对象-----ISOSource实例
	 * </pre>
	 */
	public static final String MSG_HSM = "REQUEST";
	public static final String SOURCE = "ISOSOURCE";
	
	/**
	 * <pre>
	 * 三、BEAN对象
	 * 		包括:
	 * 		1、TERM
	 * 				终端信息----------------------TermBean实例
	 * </pre>
	 */
	public static final String ENCODE_BODY = "ENCODE_BODY";
	
	/**
	 * <pre>
	 * 四、其它的常量信息
	 * 		包括以下常量：
	 * 		1、REFNO
	 * 			系统参考号			--	--	---  String类型
	 * 		2、RESULT_CODE
	 * 			TMS响应码	    	--	--	---  String类型
	 * </pre>
	 */
	public static final String RESULT_TYPE = "RESULTTYPE";
	public static final String RESULT_CODE = "RESPCODE";
	public static final String RESULT_DATA = "RESPDATA";

}
