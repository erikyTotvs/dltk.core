/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ui.actions;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.ui.callhierarchy.SearchUtil;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.dltk.internal.ui.search.SearchMessages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.search.ElementQuerySpecification;
import org.eclipse.dltk.ui.search.QuerySpecification;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;


/**
 * Finds declarations of the selected element in working sets.
 * The action is applicable to selections representing a Script element.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class FindDeclarationsInWorkingSetAction extends FindDeclarationsAction {

	private IWorkingSet[] fWorkingSet;

	/**
	 * Creates a new <code>FindDeclarationsInWorkingSetAction</code>. The action 
	 * requires that the selection provided by the site's selection provider is of type 
	 * <code>org.eclipse.jface.viewers.IStructuredSelection</code>. The user will be 
	 * prompted to select the working sets.
	 * 
	 * @param site the site providing context information for this action
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			IWorkbenchSite site) {
		this(toolkit, site, null);
	}

	/**
	 * Creates a new <code>FindDeclarationsInWorkingSetAction</code>. The action 
	 * requires that the selection provided by the site's selection provider is of type 
	 * <code>org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site			the site providing context information for this action
	 * @param workingSets	the working sets to be used in the search
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			IWorkbenchSite site, IWorkingSet[] workingSets) {
		super(toolkit, site);
		fWorkingSet= workingSets;
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor the Script editor
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			ScriptEditor editor) {
		this(toolkit, (AbstractDecoratedTextEditor) editor, null);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor the Script editor
	 * @param workingSets the working sets to be used in the search
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			ScriptEditor editor, IWorkingSet[] workingSets) {
		this(toolkit, (AbstractDecoratedTextEditor) editor, workingSets);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor the Script editor
	 * @since 5.3
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			AbstractDecoratedTextEditor editor) {
		this(toolkit, editor, null);
	}

	/**
	 * Note: This constructor is for internal use only. Clients should not call
	 * this constructor.
	 * 
	 * @param editor the Script editor
	 * @param workingSets the working sets to be used in the search
	 * @since 5.3
	 */
	public FindDeclarationsInWorkingSetAction(IDLTKLanguageToolkit toolkit,
			AbstractDecoratedTextEditor editor, IWorkingSet[] workingSets) {
		super(toolkit, editor);
		fWorkingSet= workingSets;
	}

	void init() {
		setText(SearchMessages.Search_FindDeclarationsInWorkingSetAction_label); 
		setToolTipText(SearchMessages.Search_FindDeclarationsInWorkingSetAction_tooltip); 
		setImageDescriptor(DLTKPluginImages.DESC_OBJS_SEARCH_DECL);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.FIND_DECLARATIONS_IN_WORKING_SET_ACTION);
		if (DLTKCore.DEBUG) {
			System.out.println("TODO: Add help support here.."); //$NON-NLS-1$
		}
	}

	QuerySpecification createQuery(IModelElement element) throws ModelException {
		DLTKSearchScopeFactory factory= DLTKSearchScopeFactory.getInstance();
		
		IWorkingSet[] workingSets= fWorkingSet;
		if (fWorkingSet == null) {
			workingSets= factory.queryWorkingSets();
			if (workingSets == null)
				return null;
		}
		SearchUtil.updateLRUWorkingSets(workingSets);
		IDLTKSearchScope scope= factory.createSearchScope(workingSets, true, getLanguageToolkit());
		String description= factory.getWorkingSetScopeDescription(workingSets, true);
		return new ElementQuerySpecification(element, getLimitTo(), scope, description);
	}

}
