package com.damselfly.activiti.tools;

import org.activiti.engine.impl.persistence.entity.SuspensionState;

import java.io.Serializable;

/**
 * Created by v on 2014/7/16.
 */
public class ExecutionBean  implements Serializable {
    protected String id ;
    protected boolean isActive = true;
    protected boolean isScope = true;
    protected boolean isConcurrent = false;
    protected boolean isEnded = false;
    protected boolean isEventScope = false;

    protected String eventName;
    protected String businessKey;
    protected int executionListenerIndex = 0;

    protected int cachedEntityState;

    protected boolean deleteRoot;
    protected String deleteReason;
    protected boolean isOperating = false;
    protected int revision = 1;
    protected int suspensionState = SuspensionState.ACTIVE.getStateCode();

    protected String processDefinitionId;

    protected String activityId;
    protected String activityName;

    protected String processInstanceId;

    protected String parentId;

    protected String superExecutionId;

    protected boolean forcedUpdate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isScope() {
        return isScope;
    }

    public void setScope(boolean isScope) {
        this.isScope = isScope;
    }

    public boolean isConcurrent() {
        return isConcurrent;
    }

    public void setConcurrent(boolean isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }

    public boolean isEventScope() {
        return isEventScope;
    }

    public void setEventScope(boolean isEventScope) {
        this.isEventScope = isEventScope;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getExecutionListenerIndex() {
        return executionListenerIndex;
    }

    public void setExecutionListenerIndex(int executionListenerIndex) {
        this.executionListenerIndex = executionListenerIndex;
    }

    public int getCachedEntityState() {
        return cachedEntityState;
    }

    public void setCachedEntityState(int cachedEntityState) {
        this.cachedEntityState = cachedEntityState;
    }

    public boolean isDeleteRoot() {
        return deleteRoot;
    }

    public void setDeleteRoot(boolean deleteRoot) {
        this.deleteRoot = deleteRoot;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public boolean isOperating() {
        return isOperating;
    }

    public void setOperating(boolean isOperating) {
        this.isOperating = isOperating;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSuperExecutionId() {
        return superExecutionId;
    }

    public void setSuperExecutionId(String superExecutionId) {
        this.superExecutionId = superExecutionId;
    }

    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }
}
