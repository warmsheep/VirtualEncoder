package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;

/**
 * 响应报文给POS机
 * 
 * @author yzyue
 */
public class ResponseProcessor extends AbsActor implements AbortParticipant {

	/**
	 * 当所有的验证都通过的时候调用的方法
	 */
	@Override
	public int prepare(long id, Serializable serializable) {
		return this.prepareForAbort(id, serializable);
	}

	/**
	 * 当验证拒绝的时候调用的方法
	 */
	@Override
	public int prepareForAbort(long id, Serializable serializable) {
		Context context = (Context) serializable;
		try {
			ISOSource source = (ISOSource) context.get(TxnIC.SOURCE);
			ISOMsg msg = (ISOMsg) context.get(TxnIC.MSG_HSM);
			String respType = (String) context.get(TxnIC.RESULT_TYPE);
			String respCode = (String) context.get(TxnIC.RESULT_CODE);
			String respData = (String) context.get(TxnIC.RESULT_DATA);
			if (respCode == null || respCode.equals("")) {
				respCode = RespCodeIC.SUCCESS;
			}
			if (respData == null || respData.equals("")) {
				respData = "";
			}
			msg.set(0, msg.getString(0));
			msg.set(1, respType);
			msg.set(2, respCode + respData);
			source.send(msg);
		} catch (Exception e) {
			logger.error("响应报文给POS出现异常", e);
		}
		return PREPARED | NO_JOIN;
	}

}
