/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.compiler;

/**
 * @since 2.0
 */
public interface ISourceElementRequestorExtension extends
		ISourceElementRequestor {

	int MODE_UNKNOWN = 0;
	int MODE_STRUCTURE = 1;
	int MODE_INDEX = 2;

	int getMode();

}