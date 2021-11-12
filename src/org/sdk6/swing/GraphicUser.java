package org.sdk6.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.print.PrinterException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public interface GraphicUser {

	/**
	 * Limit text field characters.
	 *
	 * @param field JTextField object.
	 * @param limit Number of max characters.
	 */
	void limitTextField(JTextField field, int limit);

	/**
	 * Set text field input type to be string or number.
	 *
	 * @param field JTextField object.
	 * @param type  Input type, string or number.
	 */
	void setTextFieldInputType(JTextField field, String type);

	/**
	 * Prints text area.
	 *
	 * @param area   The JTextArea object.
	 * @param title  The page title.
	 * @param footer The page footer.
	 * @return True if print was successful and false if not.
	 */
	boolean printTextArea(JTextArea area, String title, String footer);

	/**
	 * Adds multiple items to the list.
	 *
	 * @param list  The JList object.
	 * @param items The items you want to add.
	 */
	void addToList(JList list, String[] items);

	/**
	 * Adds single item to the list.
	 *
	 * @param list The JList object.
	 * @param item The item you want to add.
	 */
	void addToList(JList list, String item);

	/**
	 * Removes an elements from JList by element index.
	 *
	 * @param list  The JList object.
	 * @param index The element index.
	 */
	void removeFromList(JList list, int index);

	/**
	 * Show a color dialog and choose a color.
	 *
	 * @param c     Parent component.
	 * @param title The title of color dialog window.
	 * @param color The first color you want to set after window opens.
	 * @return The selected color and null if not.
	 */
	Color showColorDialog(Component c, String title, Color color);

	/**
	 * Set component direction.
	 *
	 * @param c         The component you want to set direction.
	 * @param direction The direction(right/left).
	 */
	void setComponentDirection(Component c, String direction);

	/**
	 * Set components direction.
	 *
	 * @param components The components you want to set direction.
	 * @param direction  The direction(right/left).
	 */
	void setComponentsDirection(Component[] components, String direction);

	/**
	 * Set all components direction in a window or panel.
	 *
	 * @param window    The window or panel.
	 * @param direction The direction(right/left).
	 */
	void setWindowComponentsDirection(Component window, String direction);

	/**
	 * Initialize window items, makes them in the center of screen and make them
	 * movable including pop up menu for text fields.
	 * 
	 * @param component The JFrame or JDialog component.
	 * @param popupMenu Enable pop up menu menu for JTextFields in the parent
	 *                  component.
	 * @param language  The pop up menu menu language(English/Persian).
	 * @param font      The font of pop up menu menu.
	 */
	void initializeForm(Component component, boolean popupMenu, String language, Font font);

	/**
	 * Set the text area pop up menu.
	 * 
	 * @param field    The JTextField component.
	 * @param language The pop up menu language.
	 */
	 void setTextAreaPopupMenu(JTextArea area, String language, Font font);

	/**
	 * Set the text field pop up menu.
	 * 
	 * @param field    The JTextField component.
	 * @param language The pop up menu language.
	 * @param font     The font of pop up menu.
	 */
	void setTextFieldPopupMenu(JTextField field, String language, Font font);

	/**
	 * Set the JTable columns width.
	 * 
	 * @param table The JTable object.
	 * @param index The table index.
	 * @param width The width of JTable column.
	 */
	void setTableColumnsWidth(JTable table, int index, int width);

	/**
	 * Set component icon.
	 * 
	 * @param component The component want to set icon.
	 * @param iconPath  The icon path from hard disk.
	 * @param x         The width of icon.
	 * @param y         The hieght of icon.
	 */
	void setComponentIcon(Component component, String iconPath, int x, int y);
}
