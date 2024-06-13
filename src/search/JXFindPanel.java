package search;

/*
 * $Id: JXFindPanel.java,v 1.1 2019/04/10 00:29:52 jfatallo Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.search.Searchable;

import tablemodel.modelSearch;

import components._JScrollPane;
import components._JTableMain;

/**
 * {@code JXFindPanel} is a basic find panel suitable for use in dialogs. It
 * offers case-sensitivity, wrapped searching, and reverse searching.
 * 
 * @author unascribed from JDNC
 * @author Jeanette Winzenburg
 */
public class JXFindPanel extends AbstractPatternPanel {

	private static final long serialVersionUID = 1L;
	
	public static final String FIND_NEXT_ACTION_COMMAND = "findNext";
	public static final String FIND_PREVIOUS_ACTION_COMMAND = "findPrevious";

	protected Searchable searchable;

	protected JCheckBox wrapCheck;
	protected JCheckBox backCheck;
	private boolean initialized;
	
	
	private JXPanel pnlNorth;
	private JComboBox cmbSearch;

	private _JScrollPane scrollCenter;
	private _JTableMain tblSearch;
	private modelSearch modelSearch = new modelSearch();

	private JXPanel pnlSouth;

	/**
	 * Default constructor for the find panel. Constructs panel not targeted to
	 * any component.
	 */
	public JXFindPanel(JXTable table, int column) {
		this(null, table, column);
	}

	/**
	 * Construct search panel targeted to specific <code>Searchable</code> component.
	 *
	 * @param searchible Component where search widget will try to locate and select
	 *                   information using methods of the <code>Searchible</code> interface.
	 */
	public JXFindPanel(Searchable searchable, JXTable table, int column) {
		tblSearch = (_JTableMain) table;
		
		setName(getUIString(SEARCH_TITLE));
		setSearchable(searchable);
		initActions();
	}

	/**
	 * Sets the Searchable targeted of this find widget.
	 * Triggers a search with null pattern to release the old
	 * searchable, if any.
	 * 
	 * @param searchable Component where search widget will try to locate and select
	 *                   information using methods of the {@link Searchable Searchable} interface.
	 */
	public void setSearchable(Searchable searchable) {
		if ((this.searchable != null) && this.searchable.equals(searchable)) return;
		Searchable old = this.searchable;
		if (old != null) {
			old.search((Pattern) null);
		}
		this.searchable = searchable;
		getPatternModel().setFoundIndex(-1);
		firePropertyChange("searchable", old, this.searchable);
	}

	/**
	 * Notifies this component that it now has a parent component.
	 * When this method is invoked, the chain of parent components is
	 * set up with <code>KeyboardAction</code> event listeners.
	 */
	@Override
	public void addNotify() {
		init();
		super.addNotify();
	}

	/**
	 * Initializes component and its listeners and models.
	 */ 
	protected void init() {
		if (initialized) return;
		initialized = true;
		initComponents();
		build();
		bind();
	}

	//------------------ support synch the model <--> components


	/**
	 * Configure and bind components to/from PatternModel.
	 */
	@Override
	protected void bind() {
		super.bind();
		getActionContainerFactory().configureButton(wrapCheck, getAction(PatternModel.MATCH_WRAP_ACTION_COMMAND), null);
		getActionContainerFactory().configureButton(backCheck, getAction(PatternModel.MATCH_BACKWARDS_ACTION_COMMAND), null);
		wrapCheck.setSelected(true);
	}


	/**
	 * called from listening to empty property of PatternModel.
	 * 
	 * this implementation calls super and additionally synchs the 
	 * enabled state of FIND_NEXT_ACTION_COMMAND, FIND_PREVIOUS_ACTION_COMMAND
	 * to !empty.
	 */
	@Override
	protected void refreshEmptyFromModel() {
		super.refreshEmptyFromModel();
		boolean enabled = !getPatternModel().isEmpty();
		getAction(FIND_NEXT_ACTION_COMMAND).setEnabled(enabled);
		getAction(FIND_PREVIOUS_ACTION_COMMAND).setEnabled(enabled);
	}

	//--------------------- action callbacks
	/**
	 * Action callback for Find action.
	 * Find next/previous match using current setting of direction flag.
	 * 
	 */
	@Override
	public void match() {
		doFind();
	}

	/**
	 * Action callback for FindNext action.
	 * Sets direction flag to forward and calls find.
	 */
	public void findNext() {
		getPatternModel().setBackwards(false);
		doFind();
	}

	/**
	 * Action callback for FindPrevious action.
	 * Sets direction flag to previous and calls find.
	 */
	public void findPrevious() {
		getPatternModel().setBackwards(true);
		doFind();
	}

	/**
	 * Common standalone method to perform search. Used by the action callback methods 
	 * for Find/FindNext/FindPrevious actions. Finds next/previous match using current 
	 * setting of direction flag. Result is being reporred using showFoundMessage and 
	 * showNotFoundMessage methods respectively.
	 *
	 * @see #match
	 * @see #findNext
	 * @see #findPrevious
	 */
	protected void doFind() {
		if (searchable == null)
			return;
		int foundIndex = doSearch();
		boolean notFound = (foundIndex == -1) && !getPatternModel().isEmpty();
		if (notFound) {
			if (getPatternModel().isWrapping()) {
				notFound = doSearch() == -1;
			}
		}
		if (notFound) {
			showNotFoundMessage();
		} else {
			showFoundMessage();
		}
	}

	/**
	 * Performs search and returns index of the next match.
	 *
	 * @return Index of the next match in document.
	 */
	protected int doSearch() {
		int foundIndex = searchable.search(getPatternModel().getPattern(), getPatternModel().getFoundIndex(), getPatternModel().isBackwards());
		getPatternModel().setFoundIndex(foundIndex);
		return getPatternModel().getFoundIndex();
	}

	/**
	 * Report that suitable match is found.
	 */
	protected void showFoundMessage() {

	}

	/**
	 * Report that no match is found.
	 */
	protected void showNotFoundMessage() {
		JOptionPane.showMessageDialog(this, getUIString("notFound"));
	}


	//-------------- dynamic Locale support



	@Override
	protected void updateLocaleState(Locale locale) {
		super.updateLocaleState(locale);
		setName(getUIString(SEARCH_TITLE, locale));
	}

	//-------------------------- initial


	/**
	 * creates and registers all "executable" actions.
	 * Meaning: the actions bound to a callback method on this.
	 */
	@Override
	protected void initExecutables() {
		getActionMap().put(FIND_NEXT_ACTION_COMMAND, createBoundAction(FIND_NEXT_ACTION_COMMAND, "findNext"));
		getActionMap().put(FIND_PREVIOUS_ACTION_COMMAND, createBoundAction(FIND_PREVIOUS_ACTION_COMMAND, "findPrevious"));
		super.initExecutables();
	}



	//----------------------------- init ui

	/** 
	 * Create and initialize components.
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
		wrapCheck = new JCheckBox();
		backCheck = new JCheckBox();
	}

	/**
	 * Compose and layout all the subcomponents.
	 */
	protected void build() {
		
		

		//XXX pnlMain = new JXPanel();
		setLayout(new BorderLayout(5, 5));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JXPanel();
			add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			{
				cmbSearch = new JComboBox(new DefaultComboBoxModel(modelSearch.COLUMNS));
				pnlNorth.add(cmbSearch, BorderLayout.WEST);
			}
			{
				pnlNorth.add(searchField, BorderLayout.CENTER);
				//searchField.registerKeyboardAction(getAction(FIND_NEXT_ACTION_COMMAND), KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				//searchField.registerKeyboardAction(getAction(FIND_PREVIOUS_ACTION_COMMAND), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			}
		}
		{
			scrollCenter = new _JScrollPane();
			add(scrollCenter, BorderLayout.CENTER);
			scrollCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollCenter.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblSearch.clearSelection();
				}
			});
			{
				scrollCenter.setViewportView(tblSearch);
			}
		}
		{
			pnlSouth = new JXPanel();
			add(pnlSouth, BorderLayout.SOUTH);
		}
	
		
		
		
		/*Box lBox = new Box(BoxLayout.LINE_AXIS); 
		lBox.add(searchLabel);
		lBox.add(new JLabel(":"));
		lBox.add(new JLabel("  "));
		lBox.setAlignmentY(Component.TOP_ALIGNMENT);
		Box rBox = new Box(BoxLayout.PAGE_AXIS); 
		rBox.add(searchField);
		rBox.add(matchCheck);
		rBox.add(wrapCheck);
		rBox.add(backCheck);
		rBox.setAlignmentY(Component.TOP_ALIGNMENT);

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		add(lBox);
		add(rBox);*/
	}

}