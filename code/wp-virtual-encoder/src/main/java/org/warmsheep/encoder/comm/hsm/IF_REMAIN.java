package org.warmsheep.encoder.comm.hsm;

import java.io.IOException;
import java.io.InputStream;

import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.Interpreter;
import org.jpos.iso.LiteralInterpreter;
import org.jpos.iso.NullPadder;
import org.jpos.iso.NullPrefixer;
import org.jpos.iso.Padder;
import org.jpos.iso.Prefixer;

/**
 * 加密机的字段解析器（自定义）
 * 
 */
public class IF_REMAIN extends ISOFieldPackager {
	private Interpreter interpreter;
	private Padder padder;
	private Prefixer prefixer;

	public IF_REMAIN() {
		this.padder = NullPadder.INSTANCE;
		this.interpreter = LiteralInterpreter.INSTANCE;
		this.prefixer = NullPrefixer.INSTANCE;
	}

	public IF_REMAIN(Padder padder, Interpreter interpreter, Prefixer prefixer) {
		this.padder = padder;
		this.interpreter = interpreter;
		this.prefixer = prefixer;
	}

	public IF_REMAIN(int maxLength, String description, Padder padder, Interpreter interpreter, Prefixer prefixer) {
		super(maxLength, description);
		this.padder = padder;
		this.interpreter = interpreter;
		this.prefixer = prefixer;
	}

	public void setPadder(Padder padder) {
		this.padder = padder;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

	public void setPrefixer(Prefixer prefixer) {
		this.prefixer = prefixer;
	}

	public int getMaxPackedLength() {
		return this.prefixer.getPackedLength() + this.interpreter.getPackedLength(getLength());
	}

	private String makeExceptionMessage(ISOComponent c, String operation) {
		Object fieldKey = "unknown";
		if (c != null)
			try {
				fieldKey = c.getKey();
			} catch (Exception localException) {
			}
		return super.getClass().getName() + ": Problem " + operation + " field " + fieldKey;
	}

	public byte[] pack(ISOComponent c) throws ISOException {
		try {
			String data = (String) c.getValue();
			return ((String) c.getValue()).getBytes();
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "packing"), e);
		}
	}

	public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
		try {
			int len = prefixer.decodeLength(b, offset);
			if (len == -1) {
				len = b.length - getLength();
			} else if (getLength() > 0 && len > getLength()){
				throw new ISOException("Field length " + len + " too long. Max: " + getLength());
			}

			int lenLen = prefixer.getPackedLength();
			String unpacked = interpreter.uninterpret(b, offset + lenLen, len);
			c.setValue(unpacked);
			return lenLen + interpreter.getPackedLength(len);
		} catch (Exception e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}

	public void unpack(ISOComponent c, InputStream in) throws IOException, ISOException {
		try {
			int lenLen = this.prefixer.getPackedLength();
			int len;
			if (lenLen == 0)
				len = getLength();
			else {
				len = this.prefixer.decodeLength(readBytes(in, lenLen), 0);
			}
			int packedLen = this.interpreter.getPackedLength(len);
			String unpacked = this.interpreter.uninterpret(readBytes(in, packedLen), 0, len);
			c.setValue(unpacked);
		} catch (ISOException e) {
			throw new ISOException(makeExceptionMessage(c, "unpacking"), e);
		}
	}

	protected void checkLength(int len, int maxLength) throws IllegalArgumentException {
		if (len > maxLength)
			throw new IllegalArgumentException("Length " + len + " too long for " + super.getClass().getName());
	}
}