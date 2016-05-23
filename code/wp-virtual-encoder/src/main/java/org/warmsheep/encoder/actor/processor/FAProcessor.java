package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.bean.FACommandBean;
import org.warmsheep.encoder.constants.KeyConstants;
import org.warmsheep.encoder.constants.RespCmdType;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;
import org.warmsheep.encoder.security.util.EncryptUtil;
import org.warmsheep.encoder.security.util.OddEventCheckUtil;


/**
 * FA指令处理器
 * 
 */
public class FAProcessor extends AbsActor {

	
	@Override
	public int prepare(long id, Serializable serializable) {
		Context context = (Context) serializable;
		try {
			ISOMsg reqMsg = (ISOMsg) context.get(TxnIC.MSG_HSM);
			String header = reqMsg.getString(0);
			String commandType = reqMsg.getString(1);
			String requestData = reqMsg.getString(2);
			
			FACommandBean faCommandBean = FACommandBean.build(header, commandType, requestData);
			
			String encryptKey = KeyConstants.ZPK_001;
			
			//解析主密钥明文
			String zmkCipher = null;
			if(faCommandBean.getZmkCipher().substring(0,1).equalsIgnoreCase("X")){
				zmkCipher = faCommandBean.getZmkCipher().substring(1);
			} else {
				zmkCipher = faCommandBean.getZmkCipher();
			}
			String zmkClearText = EncryptUtil.desDecryptToHex(zmkCipher, KeyConstants.ZMK_000);
			//明文进行奇偶校验
			zmkClearText = ISOUtil.hexString(OddEventCheckUtil.parityOfOdd(ISOUtil.hex2byte(zmkClearText), 0));
			
			//解析工作密钥明文
			String keyOnZmk = null;
			//双倍长密钥
			if(faCommandBean.getKeyOnZmk().substring(0,1).equalsIgnoreCase("X")){
				keyOnZmk = faCommandBean.getKeyOnZmk().substring(1);
			} else {
				keyOnZmk = faCommandBean.getKeyOnZmk();
			}
			String keyClearText = EncryptUtil.desDecryptToHex(keyOnZmk, zmkClearText);
			//明文进行奇偶校验
			keyClearText = ISOUtil.hexString(OddEventCheckUtil.parityOfOdd(ISOUtil.hex2byte(keyClearText), 0));
			
			//转加密
			String keyOnLmk = EncryptUtil.desEncryptHexString(keyClearText, encryptKey);
			if(keyOnLmk.length() == 32){
				
			}
			String checkValue = EncryptUtil.desEncryptHexString("0000000000000000", keyClearText);
			
			if(StringUtils.isNotBlank(keyOnLmk)){
				context.put(TxnIC.RESULT_TYPE, RespCmdType.FB);
				context.put(TxnIC.RESULT_CODE, RespCodeIC.SUCCESS);
				context.put(TxnIC.RESULT_DATA, keyOnLmk.toUpperCase() + checkValue.toUpperCase());
			} else {
				context.put(TxnIC.RESULT_TYPE, RespCmdType.FB);
				//TODO待实现
				context.put(TxnIC.RESULT_CODE, RespCodeIC.FORMAT_ERROR);
			}
			
			return PREPARED | NO_JOIN;
		} catch (Exception e) {
			logger.error("MS指令处理出现异常", e);
			context.put(TxnIC.RESULT_CODE, RespCodeIC.OTHER_ERROR);
			return ABORTED | NO_JOIN;
		}
	}
	
}
