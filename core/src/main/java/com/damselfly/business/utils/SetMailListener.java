package com.damselfly.business.utils;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by v on 2014/8/27.
 */
@Repository
public class SetMailListener  implements ExecutionListener {
    private static final long serialVersionUID = -5843788965690601792L;
    private String taskId;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        IdentityService identityService = execution.getEngineServices().getIdentityService();
        String applyUserId = (String) execution.getVariable("applyUserId");
        User user = identityService.createUserQuery().userId(applyUserId).singleResult();
        String to = user.getEmail();
        String send = execution.getEngineServices().getProcessEngineConfiguration().getMailServerUsername();
        execution.setVariableLocal("sender", send);
        execution.setVariableLocal("to", to);
        String userName = user.getFirstName() + " " + user.getLastName();
        execution.setVariableLocal("name", userName);
        String dealResult = (String) execution.getVariable("dealResult");
        execution.setVariableLocal("dealResult",dealResult);
    }

}
