package com.damselfly.activiti.tools;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by v on 2014/5/15.
 */
@Service
@Transactional
public class GlobalEventListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(GlobalEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                logger.debug("------------------------A job well done!");
                break;

            case ENGINE_CREATED:
                logger.debug("------------------------ENGINE_CREATED done!");
                break;

            case JOB_EXECUTION_FAILURE:
                logger.debug("------------------------A job has failed...");
                break;

            case VARIABLE_CREATED:
                ActivitiVariableEvent variableEvent = (ActivitiVariableEvent) event;
                logger.debug("------------------------创建了变量: " + variableEvent.getVariableName() + ", 值：" + variableEvent.getVariableValue());
                break;

            default:
                System.out.println("Event received: " + event.getType());
                logger.debug("------------------------Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return false;
    }
}
