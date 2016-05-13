package org.warmsheep.encoder.actor.selector;

import java.io.Serializable;

import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.GroupSelector;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.ic.TxnIC;

/**
 * 业务验证选择器
 * 
 */
public class HsmSelector extends AbsActor implements GroupSelector {

	private static final String NOT_FOUND = "NOTFOUND";

	@Override
	public int prepare(long id, Serializable serializable) {
		return PREPARED | NO_JOIN;
	}

	/**
	 * 根据POS上送的第0域（交易类型）的信息选择对应的POS业务验证器组
	 */
	@Override
	public String select(long id, Serializable serializable) {
		Context context = (Context) serializable;
		ISOMsg reqMsg = (ISOMsg) context.get(TxnIC.MSG_HSM);
		String commandType = reqMsg.getString(1);
		logger.debug("消息类型[" + commandType + "]");
		String groupName = cfg.get(commandType, NOT_FOUND);
		if (groupName.equals(NOT_FOUND)) {
			logger.error("未知的消息类型[" + commandType + "]");
		} else {
			logger.debug("获取到业务处理组[" + groupName + "]");
		}
		return groupName;
	}

}
