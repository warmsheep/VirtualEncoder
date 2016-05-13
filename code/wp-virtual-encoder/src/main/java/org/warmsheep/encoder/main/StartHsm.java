package org.warmsheep.encoder.main;

import java.util.TimeZone;

import org.jpos.q2.Q2;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 * 	启动HSM前置程序 
 * 		仅用于开发阶段,生产环境直接使用Q2进行启动
 * </pre>
 * 
 */
public class StartHsm {

	/**
	 * 程序的启动入口
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:cfg/spring/spring-config.xml");
		System.out.println(args);
		Q2 q2 = (Q2) context.getBean("Q2");
		q2.start();
	}

}
