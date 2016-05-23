package org.warmsheep.encoder.actor.processor;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.transaction.Context;
import org.warmsheep.encoder.actor.AbsActor;
import org.warmsheep.encoder.bean.FKCommandBean;
import org.warmsheep.encoder.constants.KeyConstants;
import org.warmsheep.encoder.constants.RespCmdType;
import org.warmsheep.encoder.ic.RespCodeIC;
import org.warmsheep.encoder.ic.TxnIC;
import org.warmsheep.encoder.security.util.EncryptUtil;
import org.warmsheep.encoder.security.util.OddEventCheckUtil;


/**
 * FK指令处理器
 * 
 */
public class FKProcessor extends AbsActor {

	
	@Override
	public int prepare(long id, Serializable serializable) {
		Context context = (Context) serializable;
		try {
			
			ISOMsg reqMsg = (ISOMsg) context.get(TxnIC.MSG_HSM);
			String header = reqMsg.getString(0);
			String commandType = reqMsg.getString(1);
			String requestData = reqMsg.getString(2);
			
			FKCommandBean fkCommandBean = FKCommandBean.build(header, commandType, requestData);
			
			String encryptKey = null;
			if(fkCommandBean.getKeyType().equals("1")){
				encryptKey = KeyConstants.ZAK_008;
			} else if(fkCommandBean.getKeyType().equals("0")){
				encryptKey = KeyConstants.ZEK_00A;
			}
			
			
			//解析主密钥明文
			String zmkCipher = null;
			if(fkCommandBean.getZmkCipher().substring(0,1).equalsIgnoreCase("X")){
				zmkCipher = fkCommandBean.getZmkCipher().substring(1);
			} else {
				zmkCipher = fkCommandBean.getZmkCipher();
			}
			String zmkClearText = EncryptUtil.desDecryptToHex(zmkCipher, KeyConstants.ZMK_000);
			//明文进行奇偶校验
			zmkClearText = ISOUtil.hexString(OddEventCheckUtil.parityOfOdd(ISOUtil.hex2byte(zmkClearText), 0));
			
			//解析工作密钥明文
			String keyOnZmk = null;
			//双倍长密钥
			if(fkCommandBean.getKeyOnZmk().substring(0,1).equalsIgnoreCase("X")){
				keyOnZmk = fkCommandBean.getKeyOnZmk().substring(1);
			} else {
				keyOnZmk = fkCommandBean.getKeyOnZmk();
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
				context.put(TxnIC.RESULT_TYPE, RespCmdType.FL);
				context.put(TxnIC.RESULT_CODE, RespCodeIC.SUCCESS);
				context.put(TxnIC.RESULT_DATA, keyOnLmk.toUpperCase() + checkValue.toUpperCase());
			} else {
				context.put(TxnIC.RESULT_TYPE, RespCmdType.FL);
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
