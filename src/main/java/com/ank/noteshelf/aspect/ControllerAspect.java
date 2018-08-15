package com.ank.noteshelf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ank.noteshelf.resource.NsCommonConstant;

@Aspect
@Component
public class ControllerAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before(value = "execution (* com.ank.noteshelf.controller.*.*(..))")
	public void before(JoinPoint joinPoint) {
		StringBuffer logMessage = new StringBuffer();

		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(NsCommonConstant.COLON);
		logMessage.append(joinPoint.getSignature().getName() + NsCommonConstant.COLON + NsCommonConstant.START);

		if (logger.isInfoEnabled()) {
			logger.info(logMessage.toString());
		} else if (logger.isDebugEnabled()) {
			logger.debug(logMessage.toString());
		}

	}

	@After(value = "execution (* com.ank.noteshelf.controller.*.*(..))")
	public void after(JoinPoint joinPoint) {

		StringBuffer logMessage = new StringBuffer();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(NsCommonConstant.COLON);
		logMessage.append(joinPoint.getSignature().getName() + NsCommonConstant.COLON + NsCommonConstant.END);

		if (logger.isInfoEnabled()) {
			logger.info(logMessage.toString());
		} else if (logger.isDebugEnabled()) {
			logger.debug(logMessage.toString());
		}
	}
}
