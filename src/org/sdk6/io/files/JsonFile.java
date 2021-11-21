package org.sdk6.io.files;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.sdk6.swing.Tables;
import org.sdk6.tools.OSTools;

public class JsonFile extends FileUtils {

	private boolean nextLine;

	public JsonFile(String path) {
		super(path);
	}

	public boolean isNextLine() {
		return nextLine;
	}

	public JsonFile setNextLine(boolean nextLine) {
		this.nextLine = nextLine;
		return this;
	}

	@SuppressWarnings("unchecked")
	public boolean export(JTable table, String path, boolean start) throws IOException {

		try {
			Tables tbl = new Tables();
			TextFile tf = new TextFile(path);
			StringBuilder sb = new StringBuilder("[");

			for (int i = 0; i < tbl.countRowsColumns(table, "r"); i++) {
				JSONObject object = new JSONObject();

				for (int j = 0; j < tbl.countRowsColumns(table, "c"); j++) {
					Object obj = table.getValueAt(i, j);

					if (obj instanceof JLabel) {
						object.put(table.getColumnName(j), ((JLabel) obj).getText());
					} else if (obj instanceof JButton) {
						object.put(table.getColumnName(j), ((JButton) obj).getText());
					} else if (obj instanceof JTextField) {
						object.put(table.getColumnName(j), ((JTextField) obj).getText());
					} else if (obj instanceof JTextField) {
						object.put(table.getColumnName(j), ((JTextField) obj).getText());
					} else {
						object.put(table.getColumnName(j), obj);
					}
				}

				sb.append(object.toJSONString());

				if (nextLine) {
					sb.append(System.lineSeparator());
				}
			}

			tf.write(sb.toString() + "]");

			if (start) {
				new OSTools().openFile(path);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}