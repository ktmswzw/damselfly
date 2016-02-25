package com.damselfly.business.utils;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Repository;

/**
 * Created by v on 2014/8/29.
 */
@Repository
public class ResetMailListener   implements ExecutionListener {


    private static final long serialVersionUID = -4001434917427006793L;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String send = execution.getEngineServices().getProcessEngineConfiguration().getMailServerUsername();
        execution.setVariableLocal("sender", send);
        String to = (String) execution.getVariable("email");
        execution.setVariableLocal("to", to);
        String resetUrl = (String) execution.getVariable("resetUrl");
        execution.setVariableLocal("resetUrl",resetUrl);
    }

}
