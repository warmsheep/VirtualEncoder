package org.warmsheep.encoder.ic;

public interface RespCodeIC {

	public static final String SUCCESS = "00";//正常响应
	public static final String FORMAT_ERROR = "30";//格式有误
	public static final String VALIDATE_ERROR = "27";//校验错
	public static final String OTHER_ERROR = "28";//未知错误
}
