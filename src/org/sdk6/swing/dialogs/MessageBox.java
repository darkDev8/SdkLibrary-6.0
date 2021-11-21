package org.sdk6.swing.dialogs;

import javax.swing.*;

import org.sdk6.data.structures.StringList;
import org.sdk6.swing.Swing;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class MessageBox {

	private Font buttonFont;
	private Font messageFont;

	private Color backgroundColor;
	private Color foregroundColor;

	private String title;
	private String message;

	private String yesButtonText;
	private String noButtonText;
	private String cancelButtonText;

	private int messageType;

	private Queue<String> buttons;

	private JFrame frame;
	private Icon icon;

	/**
	 * Constructor of MessageBox class.
	 * 
	 * @param buttons The buttons in the message box.
	 * @param message The message want to show in the message box.
	 */
	public MessageBox(String[] buttons, String message) {
		super();
		initDefaultValues();

		for (String button : buttons) {
			this.buttons.add(button);
		}

		this.message = message;
	}

	/**
	 * Initialize dialog values for start.
	 *
	 */
	private void initDefaultValues() {
		title = "No title";

		buttonFont = new Font("Tahoma", Font.PLAIN, 16);
		messageFont = new Font("Tahoma", Font.PLAIN, 16);

		backgroundColor = Color.WHITE;
		foregroundColor = Color.BLACK;

		yesButtonText = "Yes";
		noButtonText = "No";
		cancelButtonText = "Cancel";

		messageType = JOptionPane.WARNING_MESSAGE;
		buttons = new LinkedList<>();
	}

	/**
	 * Get message box buttons font.
	 *
	 * @return Message box buttons font.
	 */
	public Font getButtonFont() {
		return buttonFont;
	}

	/**
	 * Set message box buttons font.
	 *
	 * @param buttonFont Message box button font
	 */
	public MessageBox setButtonFont(Font buttonFont) {
		this.buttonFont = buttonFont;
		return this;
	}

	/**
	 * Get message box label font.
	 *
	 * @return Message box label font.
	 */
	public Font getMessageFont() {
		return messageFont;
	}

	/**
	 * Set message box label font.
	 *
	 * @param messageFont Message box label font
	 */
	public MessageBox setMessageFont(Font messageFont) {
		this.messageFont = messageFont;
		return this;
	}

	/**
	 * Get message background color.
	 *
	 * @return Message box background color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Set message box background color.
	 *
	 * @param backgroundColor Message box background color.
	 */
	public MessageBox setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	/**
	 * Get message box window title.
	 *
	 * @return Message box window title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set message box window title.
	 *
	 * @param title Message box window title.
	 */
	public MessageBox setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Get message box message.
	 *
	 * @return Message box message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set message box message.
	 *
	 * @param message Message box message.
	 */
	public MessageBox setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * Get message box Yes/No buttons text.
	 *
	 * @return Message box Yes/No buttons text.
	 */
	public String getYesButtonText() {
		return yesButtonText;
	}

	/**
	 * Set message box Yes/No buttons text.
	 *
	 * @param yesButtonText Message box Yes/No buttons text.
	 */
	public MessageBox setYesButtonText(String yesButtonText) {
		this.yesButtonText = yesButtonText;
		return this;
	}

	/**
	 * Get message box No button text.
	 *
	 * @return Message box No button text.
	 */
	public String getNoButtonText() {
		return noButtonText;
	}

	/**
	 * Set message box No button font.
	 *
	 * @param noButtonText Message box No button font.
	 */
	public MessageBox setNoButtonText(String noButtonText) {
		this.noButtonText = noButtonText;
		return this;
	}

	/**
	 * Get message box cancel button text.
	 *
	 * @return Message box cancel button text.
	 */
	public String getCancelButtonText() {
		return cancelButtonText;
	}

	/**
	 * Set message box cancel button text.
	 *
	 * @param cancelButtonText Message box cancel button text.
	 */
	public MessageBox setCancelButtonText(String cancelButtonText) {
		this.cancelButtonText = cancelButtonText;
		return this;
	}

	/**
	 * Get message box message type.
	 *
	 * @return Message box message type.
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * Set message box message type.
	 *
	 * @param messageType Message box message type.
	 */
	public MessageBox setMessageType(int messageType) {
		this.messageType = messageType;
		return this;
	}

	/**
	 * Get parent component.
	 *
	 * @return Current parent component.
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Set parent component.
	 *
	 * @param frame New parent component.
	 */
	public MessageBox setFrame(JFrame frame) {
		this.frame = frame;
		return this;
	}

	/**
	 * Get message box icon.
	 *
	 * @return The message box icon.
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Set message box new icon.
	 *
	 * @param icon The new icon.
	 */
	public MessageBox setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Get message box foreground color.
	 *
	 * @param foregroundColor The message box foreground color.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Set message box foreground color.
	 *
	 * @param foregroundColor The new color.
	 */
	public MessageBox setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
		return this;
	}

	/**
	 * Adds new button to message dialog.
	 *
	 * @param value New button value.
	 * @return Message box object
	 */
	public MessageBox addButton(String value) {

		return this;
	}

	/**
	 * Remove a button from message dialog.
	 *
	 * @param value Value to remove button.
	 * @return Message box object
	 */
	public MessageBox removeButton(String value) {
		buttons.remove(value);
		return this;
	}

	/**
	 * Set the message box buttons.
	 * 
	 * @param buttons The new buttons for message box.
	 * @return The current MessageBox object class.
	 */
	public MessageBox setButtons(String[] buttons) {
		this.buttons.clear();

		for (String button : buttons) {
			this.buttons.add(button);
		}

		return this;
	}

	/**
	 * Get the message box buttons.
	 * 
	 * @return The message box buttons.
	 */
	public String[] getButtons() {
		return buttons.toArray(new String[0]);
	}

	/**
	 * Show the message box with default configuration.
	 *
	 * @return The result of selection button (0,1,2,3,4,5,...)
	 */
	public int show() {
		return show(title, message, "none");
	}

	/**
	 * Show the message box with default configuration.
	 *
	 * @param title   The title of message box.
	 * @param message The content message of box.
	 * @param type    The message box type.
	 * @return The result of selection button (0,1,2,3,4,5,...)
	 */
	public int show(String title, String message, String type) {
		UIManager.put("OptionPane.background", backgroundColor);
		UIManager.put("OptionPane.messageForeground", foregroundColor);
		UIManager.put("Panel.background", backgroundColor);

		UIManager.put("OptionPane.buttonFont", buttonFont);
		UIManager.put("OptionPane.messageFont", messageFont);

		UIManager.put("OptionPane.yesButtonText", yesButtonText);
		UIManager.put("OptionPane.noButtonText", noButtonText);
		UIManager.put("OptionPane.cancelButtonText", cancelButtonText);

		switch (type.toLowerCase()) {
		case "error":
			setMessageType(JOptionPane.ERROR_MESSAGE);
			break;

		case "warn":
		case "warning":
			setMessageType(JOptionPane.WARNING_MESSAGE);
			break;

		case "info":
		case "information":
			setMessageType(JOptionPane.INFORMATION_MESSAGE);
			break;

		case "none":
		case "null":
			setMessageType(JOptionPane.NO_OPTION);
			break;
		}

		return JOptionPane.showOptionDialog(frame, message, title, messageType, messageType, icon, buttons.toArray(),
				null);
	}

	public int showWarning(String title, String message) {
		return show(title, message, "warn");
	}

	public int showError(String title, String message) {
		return show(title, message, "error");
	}

	public int showInformation(String title, String message) {
		return show(title, message, "info");
	}
}
