package org.sdk6.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.sdk6.data.types.Strings;
import org.sdk6.tools.OSTools;

public class Swing implements GraphicUser {

	/**
	 * Constructor of Swing class.
	 */
	public Swing() {
	}

	/**
	 * Constructor of Swing class.
	 * 
	 * @param enableSystemLookAndFeel Enables native look and feel if operating
	 *                                system is supported.
	 */
	public Swing(boolean enableSystemLookAndFeel) {
		if (enableSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Set JFrame or JDialog to the center of screen
	 *
	 * @param component The JFrame or JDialog.
	 */
	public void setWindowCenter(Component component) {
		JFrame frame = null;
		JDialog dialog = null;

		if (component instanceof JFrame) {
			frame = (JFrame) component;
		} else if (component instanceof JDialog) {
			dialog = (JDialog) component;
		}

		if (!Objects.isNull(frame)) {
			frame.pack();
			frame.setLocationRelativeTo(null);
		}

		if (!Objects.isNull(dialog)) {
			dialog.pack();
			dialog.setLocationRelativeTo(null);
		}
	}

	/**
	 * Make JFrame or JDialog sensitive to ESC key and close by ESC.
	 *
	 * @param component The JFrame or JDialog.
	 */
	public void enableWindowEscClose(Component component) {
		JFrame tmpFrame = null;
		JDialog tmpDialog = null;

		if (component instanceof JFrame) {
			tmpFrame = (JFrame) component;
		} else if (component instanceof JDialog) {
			tmpDialog = (JDialog) component;
		}

		final JFrame frame = tmpFrame;
		final JDialog dialog = tmpDialog;

		if (!Objects.isNull(tmpFrame)) {
			frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");

			frame.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}

		if (!Objects.isNull(dialog)) {
			dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");

			dialog.getRootPane().getActionMap().put("Cancel", new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
		}
	}

	/**
	 * Make JFrame or JDialog movable from anywhere on window except some
	 * components.
	 *
	 * @param component The JFrame or JDialog.
	 */
	public void makeWindowMovable(Component component) {
		Point point = new Point();

		JFrame tmpFrame = null;
		JDialog tmpDialog = null;

		if (component instanceof JFrame) {
			tmpFrame = (JFrame) component;
		} else if (component instanceof JDialog) {
			tmpDialog = (JDialog) component;
		}

		final JFrame frame = tmpFrame;
		final JDialog dialog = tmpDialog;

		if (!Objects.isNull(frame)) {
			frame.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					point.x = e.getX();
					point.y = e.getY();
				}
			});

			frame.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					Point p = frame.getLocation();
					frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			});
		}

		if (!Objects.isNull(dialog)) {
			dialog.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					point.x = e.getX();
					point.y = e.getY();
				}
			});

			dialog.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					Point p = dialog.getLocation();
					dialog.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			});
		}
	}

	/**
	 * Gets component position in ([Width, Height]) format.
	 *
	 * @param c The component in the form or dialog.
	 * @return Component position as String.
	 */
	public Object getComponentPosition(Component c, String outputFormat) {
		Dimension dimension = c.getSize();

		if (outputFormat.equalsIgnoreCase("string")) {
			return "[" + dimension.getWidth() + "," + dimension.getHeight() + "]";
		} else if (outputFormat.equalsIgnoreCase("dimension")) {
			return dimension;
		}

		return null;
	}

	@Override
	public void limitTextField(JTextField field, int limit) {
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (field.getText().length() == limit) {
					e.consume();
				} else if (field.getText().length() > limit) {
					field.setText(new Strings().getEmptyString());
				}
			}
		});
	}

	@Override
	public void setTextFieldInputType(JTextField field, String type) {
		field.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				Strings strings = new Strings();

				if (type.equalsIgnoreCase("number")) {
					if (!strings.isNumber(String.valueOf(e.getKeyChar()))) {
						e.consume();
					}
				} else if (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("text")) {
					if (!strings.isText(String.valueOf(e.getKeyChar()))) {
						e.consume();
					}
				}
			}
		});
	}

	@Override
	public boolean printTextArea(JTextArea area, String title, String footer) {
		try {
			if (!Objects.isNull(area)) {
				return area.print(new MessageFormat(title), new MessageFormat(footer));
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Clears text fields, labels and combo boxes in a form, dialog or panel.
	 * 
	 * @param window        The window or panel component are in it.
	 * @param component     Specify the component to search and clear items.
	 * @param setBlackColor True if you want to set component background color to
	 *                      black.
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	public void clearWindowComponents(Component window, String component, boolean setBlackColor) {

		if (window instanceof JFrame) {
			Arrays.asList(((JFrame) window).getContentPane().getComponents()).forEach(cmp -> {
				if (component.equals("field")) {
					if (cmp instanceof JTextField) {
						((JTextField) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("label")) {
					if (cmp instanceof JLabel) {
						((JLabel) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("combo")) {
					if (cmp instanceof JComboBox) {
						((JComboBox) cmp).setSelectedIndex(0);

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				}
			});
		} else if (window instanceof JDialog) {
			Arrays.asList(((JDialog) window).getComponents()).forEach(cmp -> {
				if (component.equals("field")) {
					if (cmp instanceof JTextField) {
						((JTextField) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("label")) {
					if (cmp instanceof JLabel) {
						((JLabel) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("combo")) {
					if (cmp instanceof JComboBox) {
						((JComboBox) cmp).setSelectedIndex(0);

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				}
			});
		} else if (window instanceof JPanel) {
			Arrays.asList(((JPanel) window).getComponents()).forEach(cmp -> {
				if (component.equals("field")) {
					if (cmp instanceof JTextField) {
						((JTextField) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("label")) {
					if (cmp instanceof JLabel) {
						((JLabel) cmp).setText(new Strings().getEmptyString());

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				} else if (component.equals("combo")) {
					if (cmp instanceof JComboBox) {
						((JComboBox) cmp).setSelectedIndex(0);

						if (setBlackColor) {
							cmp.setForeground(Color.black);
						}
					}
				}
			});
		}
	}

	/**
	 * Clears all JList items.
	 *
	 * @param list The JList object.
	 */
	public void clearList(JList list) {
		DefaultListModel model = (DefaultListModel) list.getModel();
		model.removeAllElements();
		list.setModel(model);
	}

	@Override
	public void addToList(JList list, String[] items) {
		for (String item : items) {
			addToList(list, item);
		}
	}

	@Override
	public void addToList(JList list, String item) {
		try {
			if (!new Strings().isNullOrEmpty(item)) {
				ListModel listModel = list.getModel();
				DefaultListModel defaultListModel = new DefaultListModel();

				for (int j = 0; j < listModel.getSize(); j++) {
					defaultListModel.addElement(listModel.getElementAt(j));
				}

				defaultListModel.addElement(item);
				list.setModel(defaultListModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Color showColorDialog(Component c, String title, Color color) {
		return JColorChooser.showDialog(c, title, Color.CYAN);
	}

	@Override
	public void setComponentDirection(Component c, String direction) {
		if (!Objects.isNull(c)) {
			switch (direction.toLowerCase()) {
			case "left":
				c.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				break;
			case "right":
				c.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				break;
			}
		}
	}

	@Override
	public void setComponentsDirection(Component[] components, String direction) {
		for (Component c : components) {
			setComponentDirection(c, direction);
		}
	}

	@Override
	public void setWindowComponentsDirection(Component window, String direction) {
		if (window instanceof JFrame) {
			Arrays.asList(((JFrame) window).getComponents()).forEach((cmp) -> {
				setComponentDirection(cmp, direction);
			});
		} else if (window instanceof JDialog) {
			Arrays.asList(((JDialog) window).getComponents()).forEach((cmp) -> {
				setComponentDirection(cmp, direction);
			});
		} else if (window instanceof JPanel) {
			Arrays.asList(((JPanel) window).getComponents()).forEach((cmp) -> {
				setComponentDirection(cmp, direction);
			});
		}
	}

	@Override
	public void initializeForm(Component component, boolean popupMenu, String language, Font font) {
		if (component instanceof JFrame || component instanceof JDialog) {
			setWindowCenter(((JFrame) component));
			makeWindowMovable(((JFrame) component));

			if (component instanceof JFrame) {
				if (popupMenu) {
					Arrays.asList(((JFrame) component).getContentPane().getComponents()).forEach(cmp -> {
						if (cmp instanceof JTextField) {
							setTextFieldPopupMenu(((JTextField) cmp), language, font);
						} else if (cmp instanceof JPanel) {
							Arrays.asList(((JPanel) cmp).getComponents()).forEach(item -> {
								if (item instanceof JTextField) {
									setTextFieldPopupMenu(((JTextField) item), language, font);
								} else if (item instanceof JTextArea) {
									setTextAreaPopupMenu(((JTextArea) cmp), language, font);
								}
							});
						} else if (cmp instanceof JTextArea) {
							setTextAreaPopupMenu(((JTextArea) cmp), language, font);
						}
					});
				}
			} else if (component instanceof JDialog) {
				if (popupMenu) {
					Arrays.asList(((JDialog) component).getContentPane().getComponents()).forEach(cmp -> {
						if (cmp instanceof JTextField) {
							setTextFieldPopupMenu(((JTextField) cmp), language, font);
						} else if (cmp instanceof JPanel) {
							Arrays.asList(((JPanel) cmp).getComponents()).forEach(item -> {
								if (item instanceof JTextField) {
									setTextFieldPopupMenu(((JTextField) item), language, font);
								} else if (item instanceof JTextArea) {
									setTextAreaPopupMenu(((JTextArea) cmp), language, font);
								}
							});
						} else if (cmp instanceof JTextArea) {
							setTextAreaPopupMenu(((JTextArea) cmp), language, font);
						}
					});
				}
			}
		}
	}

	@Override
	public void setTextFieldPopupMenu(JTextField field, String language, Font font) {
		JPopupMenu pop = new JPopupMenu();

		OSTools os = new OSTools();
		Strings strings = new Strings();

		final JMenuItem miCopy;
		final JMenuItem miCut;
		final JMenuItem miPaste;
		final JMenuItem miDelete;
		final JMenuItem miClear;
		final JMenuItem miSelectAll;
		final JMenuItem miDateTime;

		if (language.equalsIgnoreCase("persian")) {
			miCopy = new JMenuItem("کپی کردن متن");
			miCut = new JMenuItem("بریدن متن");
			miPaste = new JMenuItem("الحاق متن");
			miDelete = new JMenuItem("حذف کردن");
			miClear = new JMenuItem("پاک کردن");
			miSelectAll = new JMenuItem("انتخاب همه");
			miDateTime = new JMenuItem("ناریخ / زمان");

			setComponentsDirection(
					new Component[] { miCopy, miCut, miPaste, miDelete, miClear, miSelectAll, miDateTime }, "right");
		} else if (language.equalsIgnoreCase("english")) {
			miCopy = new JMenuItem("Copy");
			miCut = new JMenuItem("Cut");
			miPaste = new JMenuItem("Paste");
			miDelete = new JMenuItem("Delete");
			miClear = new JMenuItem("Clear");
			miSelectAll = new JMenuItem("Select all");
			miDateTime = new JMenuItem("Date / time");
		} else {
			return;
		}

		miCopy.addActionListener((ActionEvent ae) -> {
			os.copyText(field.getSelectedText());
		});

		miCut.addActionListener((ActionEvent ae) -> {
			os.copyText(field.getSelectedText());

			if (field.getText().equals(field.getSelectedText())) {
				field.setText(strings.getEmptyString());
			} else {
				int index = field.getSelectionStart();
				String begin = field.getText().substring(0, index),
						end = field.getText().substring(index + field.getSelectedText().length());

				field.setText(begin + end);
			}
		});

		miPaste.addActionListener((ActionEvent ae) -> {
			if (strings.isNullOrEmpty(field.getSelectedText())) {
				field.setText(field.getText().concat(os.pasteClipboard()));
			} else {
				int index = field.getSelectionStart();
				String begin = field.getText().substring(0, index),
						end = field.getText().substring(index + field.getSelectedText().length());

				field.setText(begin + os.pasteClipboard() + end);
			}
		});

		miDelete.addActionListener((ActionEvent ae) -> {
			if (field.getText().equals(field.getSelectedText())) {
				field.setText(strings.getEmptyString());
			} else {
				int index = field.getSelectionStart();
				String begin = field.getText().substring(0, index),
						end = field.getText().substring(index + field.getSelectedText().length());

				field.setText(begin + end);
			}
		});

		miClear.addActionListener((ActionEvent ae) -> {
			field.setText(strings.getEmptyString());
		});

		miSelectAll.addActionListener((ActionEvent ae) -> {
			field.selectAll();
		});

		miDateTime.addActionListener((ActionEvent ae) -> {
			field.setText(field.getText().concat(os.getTimeDate()));
		});

		miCopy.setFont(font);
		miCut.setFont(font);
		miPaste.setFont(font);
		miDelete.setFont(font);
		miClear.setFont(font);
		miSelectAll.setFont(font);
		miDateTime.setFont(font);

		pop.add(miCopy);
		pop.add(miCut);
		pop.add(miPaste);
		pop.add(miDelete);
		pop.add(miClear);
		pop.add(miSelectAll);
		pop.add(miDateTime);

		field.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (strings.isNullOrEmpty(field.getSelectedText())) {
						miCopy.setEnabled(false);
						miCut.setEnabled(false);
						miDelete.setEnabled(false);
					} else {
						miCopy.setEnabled(true);
						miCut.setEnabled(true);
						miDelete.setEnabled(true);
					}

					if (strings.isNullOrEmpty(field.getText())) {
						miClear.setEnabled(false);
						miSelectAll.setEnabled(false);
					} else {
						miClear.setEnabled(true);
						miSelectAll.setEnabled(true);
					}

					if (!field.isEditable()) {
						miCut.setVisible(false);
						miDelete.setVisible(false);
						miDateTime.setVisible(false);
						miClear.setVisible(false);
						miPaste.setVisible(false);
					} else {
						miCut.setVisible(true);
						miDelete.setVisible(true);
						miDateTime.setVisible(true);
						miClear.setVisible(true);
						miPaste.setVisible(true);
					}

					pop.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void setTextAreaPopupMenu(JTextArea area, String language, Font font) {
		JPopupMenu pop = new JPopupMenu();

		OSTools os = new OSTools();
		Strings strings = new Strings();

		final JMenuItem miCopy;
		final JMenuItem miCut;
		final JMenuItem miPaste;
		final JMenuItem miDelete;
		final JMenuItem miClear;
		final JMenuItem miSelectAll;
		final JMenuItem miDateTime;

		if (language.equalsIgnoreCase("persian")) {
			miCopy = new JMenuItem("کپی کردن متن");
			miCut = new JMenuItem("بریدن متن");
			miPaste = new JMenuItem("الحاق متن");
			miDelete = new JMenuItem("حذف کردن");
			miClear = new JMenuItem("پاک کردن");
			miSelectAll = new JMenuItem("انتخاب همه");
			miDateTime = new JMenuItem("ناریخ / زمان");

			setComponentsDirection(
					new Component[] { miCopy, miCut, miPaste, miDelete, miClear, miSelectAll, miDateTime }, "right");
		} else if (language.equalsIgnoreCase("english")) {
			miCopy = new JMenuItem("Copy");
			miCut = new JMenuItem("Cut");
			miPaste = new JMenuItem("Paste");
			miDelete = new JMenuItem("Delete");
			miClear = new JMenuItem("Clear");
			miSelectAll = new JMenuItem("Select all");
			miDateTime = new JMenuItem("Date / time");
		} else {
			return;
		}

		miCopy.addActionListener((ActionEvent ae) -> {
			os.copyText(area.getSelectedText());
		});

		miCut.addActionListener((ActionEvent ae) -> {
			os.copyText(area.getSelectedText());

			if (area.getText().equals(area.getSelectedText())) {
				area.setText(strings.getEmptyString());
			} else {
				int index = area.getSelectionStart();
				String begin = area.getText().substring(0, index),
						end = area.getText().substring(index + area.getSelectedText().length());

				area.setText(begin + end);
			}
		});

		miPaste.addActionListener((ActionEvent ae) -> {
			if (strings.isNullOrEmpty(area.getSelectedText())) {
				area.setText(area.getText().concat(os.pasteClipboard()));
			} else {
				int index = area.getSelectionStart();
				String begin = area.getText().substring(0, index),
						end = area.getText().substring(index + area.getSelectedText().length());

				area.setText(begin + os.pasteClipboard() + end);
			}
		});

		miDelete.addActionListener((ActionEvent ae) -> {
			if (area.getText().equals(area.getSelectedText())) {
				area.setText(strings.getEmptyString());
			} else {
				int index = area.getSelectionStart();
				String begin = area.getText().substring(0, index),
						end = area.getText().substring(index + area.getSelectedText().length());

				area.setText(begin + end);
			}
		});

		miClear.addActionListener((ActionEvent ae) -> {
			area.setText(strings.getEmptyString());
		});

		miSelectAll.addActionListener((ActionEvent ae) -> {
			area.selectAll();
		});

		miDateTime.addActionListener((ActionEvent ae) -> {
			area.setText(area.getText().concat(os.getTimeDate()));
		});

		miCopy.setFont(font);
		miCut.setFont(font);
		miPaste.setFont(font);
		miDelete.setFont(font);
		miClear.setFont(font);
		miSelectAll.setFont(font);
		miDateTime.setFont(font);

		pop.add(miCopy);
		pop.add(miCut);
		pop.add(miPaste);
		pop.add(miDelete);
		pop.add(miClear);
		pop.add(miSelectAll);
		pop.add(miDateTime);

		area.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (strings.isNullOrEmpty(area.getSelectedText())) {
						miCopy.setEnabled(false);
						miCut.setEnabled(false);
						miDelete.setEnabled(false);
					} else {
						miCopy.setEnabled(true);
						miCut.setEnabled(true);
						miDelete.setEnabled(true);
					}

					if (strings.isNullOrEmpty(area.getText())) {
						miClear.setEnabled(false);
						miSelectAll.setEnabled(false);
					} else {
						miClear.setEnabled(true);
						miSelectAll.setEnabled(true);
					}

					if (!area.isEditable()) {
						miCut.setVisible(false);
						miDelete.setVisible(false);
						miDateTime.setVisible(false);
						miClear.setVisible(false);
						miPaste.setVisible(false);
					} else {
						miCut.setVisible(true);
						miDelete.setVisible(true);
						miDateTime.setVisible(true);
						miClear.setVisible(true);
						miPaste.setVisible(true);
					}

					pop.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void setTableColumnsWidth(JTable table, int index, int width) {
		TableColumn column = table.getColumnModel().getColumn(index);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setPreferredWidth(width);
	}

	@Override
	public void setComponentIcon(Component component, String iconPath, int x, int y) {
		try {
			ImageIcon i = new ImageIcon((getClass().getResource(iconPath)));
			Image image = i.getImage();

			Image newimg = image.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
			i = new ImageIcon(newimg);

			if (!Objects.isNull(component)) {
				if (component instanceof JMenuItem) {
					((JMenuItem) component).setIcon(i);
				} else if (component instanceof JButton) {
					((JButton) component).setIcon(i);
				} else if (component instanceof JLabel) {
					((JLabel) component).setIcon(i);
				} else if (component instanceof JToggleButton) {
					((JToggleButton) component).setIcon(i);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeFromList(JList list, int index) {
		((DefaultListModel) list.getModel()).remove(index);
	}
}
