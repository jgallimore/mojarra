/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates.
 * Copyright (c) 2018 Payara Services Limited.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.servlet30.el;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * A ViewScoped bean testing session invalidation functionality.
 */

@Named
@ViewScoped
public class ViewInvalidatedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Stores the text.
     */
    private String text;

    /**
     * Constructor.
     */
    public ViewInvalidatedBean() {
        this.text = "This is from the constructor";
    }

    /**
     * Post-construct.
     *
     */
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove("invalidated");
        text = "This is from the @PostConstruct";
    }

    /**
     * Pre-destroy
     */
    @PreDestroy
    public void destroy() {
        /*
         * For the purpose of the test we can actually ask for the current instance of
         * the FacesContext, because we trigger invalidating of the session through a
         * JSF page, however in the normal case of session invalidation this will NOT be
         * true. So this means that normally the
         *
         * @PreDestroy annotated method should not try to use
         * FacesContext.getCurrentInstance().
         */
        if (FacesContext.getCurrentInstance() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("invalidated", true);
        }
    }

    /**
     * Get the text.
     */
    public String getText() {
        return this.text;
    }
}
