package org.warmsheep.encoder.actor.validator;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;
import org.warmsheep.encoder.util.StringUtil;

public class CommonValid extends AbsActor{
	
	/**
	 * 验证POS上送的第4域（应用数据）的长度是否正确
	 */
	@Override
	public int prepare(long id, Serializable serializable) {
		Context context = (Context) serializable;
		ISOMsg reqMsg = (ISOMsg) context.get(TxnIC.MSG_HSM);
		logger.debug("验证报文的格式");
		String command = reqMsg.getString(1);
		String data = reqMsg.getString(2);
		
		if(StringUtils.isBlank(command) || StringUtils.isBlank(data)){
			logger.error(StringUtil.merge("数据验证失败!数据不能为空"));
			context.put(TxnIC.RESULT_CODE, RespCodeIC.FORMAT_ERROR);
			return ABORTED | NO_JOIN;
		} else {
			logger.debug(StringUtil.merge("数据验证通过"));
			return PREPARED | NO_JOIN;
		}
		
	}

}
