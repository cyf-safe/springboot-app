package com.iokbl.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: TxAdviceInterceptor.java
 * @Package com.iokbl.config
 * @Description: 事务控制类
 * @author chenyufei
 * @date 2020-04-26
 * @version v1.0
 */
@Aspect
@Configuration
public class TxAdviceInterceptor {

	private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.iokbl.service.impl.*.*(..))";

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Bean
	public TransactionInterceptor txAdvice() {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

		/* 当前没有事务：只有select，非事务执行；有update，insert，delete操作，自动提交；
		 * 当前有事务：如果有update，insert，delete操作，支持当前事务 */
		RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
		readOnlyTx.setReadOnly(true);
		readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

		/* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
		RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
		requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		// requiredTx.setTimeout(TX_METHOD_TIMEOUT);
		Map<String, TransactionAttribute> txMap = new HashMap<>();
		txMap.put("add*", requiredTx);
		txMap.put("save*", requiredTx);
		txMap.put("insert*", requiredTx);
		txMap.put("update*", requiredTx);
		txMap.put("modify*", requiredTx);
		txMap.put("delete*", requiredTx);
		txMap.put("remove*", requiredTx);
		txMap.put("get*", readOnlyTx);
		txMap.put("select*", readOnlyTx);
		txMap.put("query*", readOnlyTx);
		source.setNameMap(txMap);

		TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
		return txAdvice;
	}

	@Bean
	public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, txAdvice);
	}

}