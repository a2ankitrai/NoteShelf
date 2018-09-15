package com.ank.noteshelf.aspect;

import static com.ank.noteshelf.resource.NsCommonConstant.COLON;
import static com.ank.noteshelf.resource.NsCommonConstant.START;
import static com.ank.noteshelf.resource.NsCommonConstant.END;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before(value = "execution (* com.ank.noteshelf.controller.*.*(..))")
	public void before(JoinPoint joinPoint) {
		StringBuffer logMessage = new StringBuffer();

		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(COLON);
		logMessage.append(joinPoint.getSignature().getName() + COLON + START);

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
		logMessage.append(COLON);
		logMessage.append(joinPoint.getSignature().getName() + COLON + END);

		if (logger.isInfoEnabled()) {
			logger.info(logMessage.toString());
		} else if (logger.isDebugEnabled()) {
			logger.debug(logMessage.toString());
		}
	}
}
