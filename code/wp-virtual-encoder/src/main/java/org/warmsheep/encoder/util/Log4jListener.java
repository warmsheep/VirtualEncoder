package org.warmsheep.encoder.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.xml.DOMConfigurator;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.util.LogEvent;
import org.jpos.util.LogListener;

/**
 * JPOS对Log4j的支持
 * 
 */
public class Log4jListener implements LogListener, Configurable {
	/**
	 * Log4j中的级别
	 */
	private Level level;
	/**
	 * Log4j中所对应的logger对象
	 */
	private String logName;

	public Log4jListener() {
		setLevel(Level.DEBUG_INT);
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public void setLevel(int level) {
		this.level = Level.toLevel(level);
	}

	public void setLevel(String level) {
		this.level = Level.toLevel(level);
	}

	public void close() {
	}

	/**
	 * 获取配置信息
	 */
	@Override
	public void setConfiguration(Configuration cfg)
			throws ConfigurationException {
		String config = cfg.get("config");
		long watch = cfg.getLong("watch");
		this.logName = cfg.get("log-name");
		if (watch == 0)
			watch = FileWatchdog.DEFAULT_DELAY;
		if ((config != null) && (!config.trim().equals(""))) {
			DOMConfigurator.configureAndWatch(config, watch);
		}
		setLevel(cfg.get("priority"));
	}

	/**
	 * 记录日志信息
	 */
	@Override
	public synchronized LogEvent log(LogEvent ev) {
		Logger logger = Logger.getLogger(this.logName);
		if (logger.isEnabledFor(level)) {
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				PrintStream p = new PrintStream(baos);
				ev.dump(p, "");
				logger.log(level, baos.toString());
			} finally {
				try {
					if (baos != null){
						baos.close();
					}
				} catch (IOException e) {
				}
			}
		}
		return ev;
	}
}
