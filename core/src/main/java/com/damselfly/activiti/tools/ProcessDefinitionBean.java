package com.damselfly.activiti.tools;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

import java.io.Serializable;

/**
 * Created by v on 2014/7/11.
 */
public class ProcessDefinitionBean   implements Serializable {

    public String id;
    public String name;
    public boolean suspended;
    protected String key;
    protected int revision = 1;
    protected int version;
    protected String category;
    protected String deploymentId;
    protected String resourceName;
    protected String tenantId = ProcessEngineConfiguration.NO_TENANT_ID;
    protected Integer historyLevel;
    protected String diagramResourceName;
    protected boolean isGraphicalNotationDefined;
    protected boolean hasStartFormKey;
    protected int suspensionState = SuspensionState.ACTIVE.getStateCode();
    protected boolean isIdentityLinksInitialized = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isIdentityLinksInitialized() {
        return isIdentityLinksInitialized;
    }

    public void setIdentityLinksInitialized(boolean isIdentityLinksInitialized) {
        this.isIdentityLinksInitialized = isIdentityLinksInitialized;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getHistoryLevel() {
        return historyLevel;
    }

    public void setHistoryLevel(Integer historyLevel) {
        this.historyLevel = historyLevel;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public boolean isGraphicalNotationDefined() {
        return isGraphicalNotationDefined;
    }

    public void setGraphicalNotationDefined(boolean isGraphicalNotationDefined) {
        this.isGraphicalNotationDefined = isGraphicalNotationDefined;
    }

    public boolean isHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }
}
