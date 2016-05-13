package org.warmsheep.encoder.comm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.ic.LogIC;
import org.warmsheep.encoder.ic.TxnIC;

/**
 * 监听器,业务处理的入口点
 * 
 * @author yzyue
 */
public class HsmRequestListener implements ISORequestListener, Configurable {
	/**
	 * 配置对象
	 */
	private Configuration cfg;
	/**
	 * 日志记录对象
	 */
	private Log logger = LogFactory.getLog(LogIC.HSM);

	@Override
	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		this.cfg = cfg;
	}

	@Override
	public boolean process(ISOSource source, ISOMsg msg) {
		try {
			Context context = new Context();
			context.put(TxnIC.MSG_HSM, msg);// 保存ISOMsg对象
			context.put(TxnIC.SOURCE, source);// 保存ISOSource对象
			Space space = SpaceFactory.getSpace(TxnIC.SPACE);
			long timeout = cfg.getLong("timeout", 30000);
			space.out(TxnIC.QUEUE, context, timeout);
		} catch (Exception e) {
			logger.error("处理TMS交易出现异常", e);
		}
		return true;
	}

}
