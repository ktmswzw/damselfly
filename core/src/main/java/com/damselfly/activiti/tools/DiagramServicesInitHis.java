package com.damselfly.activiti.tools;

import org.restlet.routing.Router;

import java.io.Serializable;

/**
 * Created by moxz on 2014/8/11.
 */
public class DiagramServicesInitHis  implements Serializable {
    public static void attachResources(Router router) {
        router.attach("/process-instance-his/{processInstanceId}/highlights", ProcessInstanceHighlightsResourceHis.class);
    }
}
