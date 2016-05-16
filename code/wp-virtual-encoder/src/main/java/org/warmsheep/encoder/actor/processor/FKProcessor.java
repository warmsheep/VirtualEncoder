package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;


/**
 * FK指令处理器
 * 
 */
public class FKProcessor extends AbsActor {

	
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
