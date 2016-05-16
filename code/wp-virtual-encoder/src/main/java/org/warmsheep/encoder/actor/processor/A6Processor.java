package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.bean.A0CommandBean;
import org.warmsheep.encoder.constants.KeyConstants;
import org.warmsheep.encoder.constants.RespCmdType;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;
import org.warmsheep.encoder.security.util.OddEventCheckUtil;
import org.warmsheep.util.UUIDUitl;
import org.warmsheep.util.security.utils.EncryptUtil;


/**
 * A6指令处理器
 * 
 */
public class A6Processor extends AbsActor {

	
	@Override
	public int prepare(long id, Serializable serializable) {
		Context context = (Context) serializable;
		try {
			
			
			return PREPARED | NO_JOIN;
		} catch (Exception e) {
			logger.error("MS指令处理出现异常", e);
			context.put(TxnIC.RESULT_CODE, RespCodeIC.OTHER_ERROR);
			return ABORTED | NO_JOIN;
		}
	}
	
}
