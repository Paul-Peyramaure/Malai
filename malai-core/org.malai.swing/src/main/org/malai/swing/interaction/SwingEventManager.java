package org.malai.swing.interaction;

import org.malai.interaction.BasicEventManager;
import org.malai.interaction.EventProcessor;
import org.malai.swing.widget.MFrame;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A Swing event manager gathers Swing events produces by widgets and transfers them handlers.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/10/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class SwingEventManager extends BasicEventManager<Component> implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener,
						ActionListener, ItemListener, ChangeListener, DocumentListener, TreeSelectionListener, TreeExpansionListener {
	/** The ID used to identify the mouse. Will change when multi-device will be supported. */
	public static final int ID_MOUSE = 0;

	/** The ID used to identify the keyboard. Will change when multi-device will be supported. */
	public static final int ID_KB 	 = 1;

	/**
	 * The name of the property to set to map a document to its widget. For instance,
	 * if you want to get the text field that produced a document event you have to add
	 * a property to the field (field.getDocument().putProperty(OWNING_PROPERTY, field);) to
	 * get the widget in a DocumentEvent operation (Object owner = documentEvent.getDocument().getProperty("owner");).
	 */
	public static final String OWNING_PROPERTY = "malai_owner";

	/** A subset of the set 'handlers' corresponding to the swing Handlers. */
	private List<SwingEventProcessor> swingHandlers;


	/**
	 * Creates a event manager that gathers Swing events and transfers them to handlers.
	 * @since 0.1
	 */
	public SwingEventManager() {
		super();
		swingHandlers = null;
	}



	@Override
	public void attachTo(final Component comp) {
		if(comp!=null) {
			comp.addMouseListener(this);
			comp.addMouseMotionListener(this);
			comp.addMouseWheelListener(this);
			comp.addKeyListener(this);
			
			if(comp instanceof JTree) {
				JTree tree = (JTree) comp;
				tree.addTreeSelectionListener(this);
				tree.addTreeExpansionListener(this);
			}

			if(comp instanceof AbstractButton)
				((AbstractButton)comp).addActionListener(this);

			if(comp instanceof JTextComponent) {
				final JTextComponent field = (JTextComponent)comp;

				if(field.getDocument().getProperty(OWNING_PROPERTY)==field)
					field.getDocument().addDocumentListener(this);

				if(comp instanceof JTextField)
					((JTextField)comp).addActionListener(this);
			}

			if(comp instanceof ItemSelectable)
				((ItemSelectable)comp).addItemListener(this);

			if(comp instanceof JSpinner)
				((JSpinner)comp).addChangeListener(this);

			if(comp instanceof JTabbedPane)
				((JTabbedPane)comp).addChangeListener(this);
		}
	}



	@Override
	public void detachForm(final Component comp) {
		if(comp!=null) {
			comp.removeMouseListener(this);
			comp.removeMouseMotionListener(this);
			comp.removeMouseWheelListener(this);
			comp.removeKeyListener(this);

			if(comp instanceof AbstractButton)
				((AbstractButton)comp).removeActionListener(this);

			if(comp instanceof JTextField)
				((JTextField)comp).removeActionListener(this);

			if(comp instanceof ItemSelectable)
				((ItemSelectable)comp).removeItemListener(this);

			if(comp instanceof JSpinner)
				((JSpinner)comp).removeChangeListener(this);

			if(comp instanceof JTabbedPane)
				((JTabbedPane)comp).removeChangeListener(this);
			
			if(comp instanceof JTree) {
				JTree tree = (JTree) comp;
				tree.removeTreeSelectionListener(this);
				tree.removeTreeExpansionListener(this);
			}
		}
	}


	@Override
	public void addHandlers(final EventProcessor h) {
		super.addHandlers(h);
		if(h instanceof SwingEventProcessor) {
			if(swingHandlers==null) swingHandlers = new CopyOnWriteArrayList<>();
			swingHandlers.add((SwingEventProcessor)h);
		}
	}


	@Override
	public void removeHandler(final EventProcessor h) {
		super.removeHandler(h);
		if(h!=null && swingHandlers!=null && h instanceof SwingEventProcessor)
			swingHandlers.remove(h);
	}



	@Override
	public void mouseClicked(final MouseEvent e) {
		//
	}



	@Override
	public void mouseEntered(final MouseEvent e) {
		//
	}



	@Override
	public void mouseExited(final MouseEvent e) {
		//
	}



	@Override
	public void mousePressed(final MouseEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onPressure(button, x, y, ID_MOUSE, src);
	}



	@Override
	public void mouseReleased(final MouseEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onRelease(button, x, y, ID_MOUSE, src);
	}



	@Override
	public void keyPressed(final KeyEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty() || e.getKeyCode()==KeyEvent.VK_UNDEFINED) return;

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onKeyPressure(e.getKeyCode(), e.getKeyChar(), ID_KB, e.getSource());
	}



	@Override
	public void keyReleased(final KeyEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty() || e.getKeyCode()==KeyEvent.VK_UNDEFINED) return;

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onKeyRelease(e.getKeyCode(), e.getKeyChar(), ID_KB, e.getSource());
	}



	@Override
	public void keyTyped(final KeyEvent e) {
		//
	}



	@Override
	public void mouseDragged(final MouseEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onMove(button, x, y, true, ID_MOUSE, src);
	}



	@Override
	public void mouseMoved(final MouseEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onMove(button, x, y, false, ID_MOUSE, src);
	}



	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final int posX 		= e.getX();
		final int posY 		= e.getY();
		final Object src 	= e.getSource();
		final int direction = e.getWheelRotation();
		final int type 		= e.getScrollType();
		final int amount 	= e.getScrollAmount();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onScroll(posX, posY, direction, amount, type, ID_MOUSE, src);
	}


	@Override
	public void valueChanged(final TreeSelectionEvent event) {
		if(event==null || swingHandlers==null || swingHandlers.isEmpty()) return;
		
		final Object src = event.getSource();
		final TreePath[] changedPaths = event.getPaths();
		final boolean isSelectionAdded = event.isAddedPath();

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onTreeSelectionChanged(src, changedPaths, isSelectionAdded);
	}


	@Override
	public void treeExpanded(final TreeExpansionEvent event) {
		if(event==null || swingHandlers==null || swingHandlers.isEmpty()) return;
		
		final Object src = event.getSource();
		final TreePath pathExpanded = event.getPath();
		
		for(final SwingEventProcessor handler : swingHandlers)
			handler.onTreeExpanded(src, pathExpanded, true);
	}


	@Override
	public void treeCollapsed(final TreeExpansionEvent event) {
		if(event==null || swingHandlers==null || swingHandlers.isEmpty()) return;
		
		final Object src = event.getSource();
		final TreePath pathExpanded = event.getPath();
		
		for(final SwingEventProcessor handler : swingHandlers)
			handler.onTreeExpanded(src, pathExpanded, false);
	}


	@Override
	public void actionPerformed(final ActionEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		final Object src = e.getSource();

		if(src instanceof JMenuItem) {
			final JMenuItem mi = (JMenuItem) e.getSource();

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onMenuItemPressed(mi);
		}
		else if(src instanceof JCheckBox) {
			final JCheckBox cb = (JCheckBox) e.getSource();

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onCheckBoxModified(cb);
		}
		else if(src instanceof AbstractButton) {
			final AbstractButton ab = (AbstractButton)e.getSource();

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onButtonPressed(ab);
		}
		else if(src instanceof JTextComponent) {
			final JTextComponent tc = (JTextComponent)e.getSource();

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onTextChanged(tc);
		}
	}


	@Override
	public void itemStateChanged(final ItemEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		if(e.getItemSelectable() instanceof JComboBox) {
			final JComboBox<?> cb = (JComboBox<?>) e.getItemSelectable();

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onItemSelected(cb);
		}
	}


	@Override
	public void stateChanged(final ChangeEvent e) {
		if(e==null || swingHandlers==null || swingHandlers.isEmpty()) return;
		final Object src = e.getSource();

		if(src instanceof JSpinner) {
			final JSpinner spinner = (JSpinner)src;

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onSpinnerChanged(spinner);
		}
		else if(src instanceof JTabbedPane) {
			final JTabbedPane tabbedPanel = (JTabbedPane)src;

    		for(final SwingEventProcessor handler : swingHandlers)
				handler.onTabChanged(tabbedPanel);
		}
	}


	/**
	 * This pseudo-event is defined to manage pressure on the close button of frames.
	 * @param frame The frame closed. Must not be null.
	 * @since 0.2
	 */
	public void windowClosed(final Window frame) {
		if(frame==null || swingHandlers==null || swingHandlers.isEmpty()) return;

		for(final SwingEventProcessor handler : swingHandlers)
			handler.onWindowClosed(frame);
	}


	/**
	 * This helper factorises the code for the operations insertUpdate,
	 * removeUpdate, and changedUpdate of the DocumentEvent interface.
	 * @param event The document event produced by a widget.
	 */
	private void onDocumentEvent(final DocumentEvent event) {
		if(event==null || swingHandlers==null || swingHandlers.isEmpty()) return;
		
		final Object owner = event.getDocument().getProperty(OWNING_PROPERTY);

		if(owner instanceof JTextComponent) {
			final JTextComponent comp = (JTextComponent)owner;

			for(final SwingEventProcessor handler : swingHandlers)
				handler.onTextChanged(comp);
		}
	}


	@Override
	public void insertUpdate(final DocumentEvent event) {
		onDocumentEvent(event);
	}


	@Override
	public void removeUpdate(final DocumentEvent event) {
		onDocumentEvent(event);
	}


	@Override
	public void changedUpdate(final DocumentEvent event) {
		onDocumentEvent(event);
	}
}
