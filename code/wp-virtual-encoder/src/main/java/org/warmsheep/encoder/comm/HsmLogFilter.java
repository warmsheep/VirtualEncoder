package org.warmsheep.encoder.comm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;
import org.jpos.util.LogEvent;
import org.warmsheep.encoder.ic.LogIC;

/**
 * 过滤器，记录通讯日志信息
 * 
 */
public class HsmLogFilter implements ISOFilter, Configurable {

	/**
	 * 配置信息
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
	public ISOMsg filter(ISOChannel channel, ISOMsg msg, LogEvent evt)
			throws VetoException {
		StringBuffer info = new StringBuffer();
		try {
			ISOPackager packager = msg.getPackager();
			String transType = msg.getString(1);
			info.append("=================== ");
			info.append(cfg.get(transType, "未知交易类型[" + transType + "]"));
			info.append(" ===================\r\n");
			info.append("报文信息\r\n");
			for (int i = 0; i < 3; ++i) {
				if (msg.hasField(i)) {
					byte[] b = msg.getBytes(i);
					String str = msg.getString(i);
					info.append("[");
					info.append(ISOUtil.padleft(Integer.toString(i), 3, '0'));
					info.append("] [");
					info.append(ISOUtil.padleft(Integer.toString(b.length), 4, '0'));
					info.append("] [");
					info.append(str);
					info.append("] [");
					info.append(packager.getFieldDescription(msg, i));
					info.append("]\r\n");
					b = null;
				}
			}
			byte[] data = msg.pack();
			info.append("十六进制信息\r\n");
			info.append(ISOUtil.hexdump(data));
			data = null;
			logger.info(info.toString());
		} catch (Exception e) {
			logger.error(info.toString(), e);
		}
		return msg;
	}

}
