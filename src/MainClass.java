import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import SmallInteger;

public class MainClass {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CalculatorFrame frame = new CalculatorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
			}
		});
	}
}

class CalculatorFrame extends JFrame {
	public CalculatorFrame() {
		setTitle("Calculator");
		CalculatorPanel panel = new CalculatorPanel();
		add(panel);
		setSize(400, 500);
		// pack();
	}
}

class CalculatorPanel extends JPanel {
	public CalculatorPanel() {
		setLayout(new BorderLayout());

		// result = 0;
		lastCommand = "=";
		start = true;
		sing = true;
		inpNUM = true;
		begin = true;
		number = new SmallInteger("0");

		PText = new JPanel();
		PText.setLayout(new GridLayout(2, 1));

		label = new JLabel("");
		label.setPreferredSize(new Dimension(30, 30));
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		PText.add(label);

		display = new JLabel("0");
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setPreferredSize(new Dimension(30, 30));
		display.setFont(new Font("Arial", Font.PLAIN, 20));
		display.setForeground(new Color(0, 0, 255));
		PText.add(display);

		add(PText, BorderLayout.NORTH);

		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();

		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 4));

		addButton("<<", command);
		addButton("CE", command);
		addButton("C", command);
		addButton("^", command);

		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		addButton("/", command);

		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		addButton("*", command);

		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		addButton("-", command);

		addButton("0", insert);
		addButton("+/-", command);
		addButton("=", command);
		addButton("+", command);

		add(panel, BorderLayout.CENTER);
	}

	private void addButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		button.setBackground(new Color(255, 155, 170));
		button.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		panel.add(button);
	}

	private class InsertAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String input = event.getActionCommand();
			sing = true;
			if (lastCommand == ".") {
				begin = true;
			}
			if (inpNUM) {
				display.setText("");
				inpNUM = false;
			}
			if ((display.getText()).length() < 4)
				display.setText(display.getText() + input);
		}
	}

	private class CommandAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			switch (command) {
			case "←":
				str = display.getText();
				if (str.length() == 1) {
					display.setText("0");
					inpNUM = true;
				}
				else {
					str = str.substring(0, str.length() - 1);
					display.setText(str);
				}
				if (label.getText() == "") {
					number = new SmallInteger(display.getText());
				}
				break;

			case "CE":
				if (label.getText() == "") {
					start = true;
					begin = true;
				}
				display.setText("0");
				inpNUM = true;
				break;

			case "C":
				label.setText("");
				display.setText("0");
				inpNUM = true;
				start = true;
				begin = true;
				break;
			case "=":
				if (label.getText() != "" && !inpNUM) {
					calculate(display.getText());
					label.setText("");
					display.setText(number.toString());
					lastCommand = ".";
					inpNUM = true;
					start = true;
				}
				break;

			case "+/-":
				display.setText(((new SmallInteger(display.getText())).opposite()).toString());
				inpNUM = false;
				break;

			default:
				// if (sing) {
				if (command != lastCommand) {
					if (!sing) {
						str = label.getText();
						str = str.substring(0, str.length() - 1);
						label.setText(str);
					}
					if (start) {
						if (begin)
							number = new SmallInteger(display.getText());
						lastCommand = command;
						label.setText(label.getText() + display.getText() + command);
						start = false;
						inpNUM = true;
						begin = false;
					} else if (!start && inpNUM) {
						lastCommand = command;
						str = label.getText();
						str = str.substring(0, str.length() - 1);
						label.setText(label.getText() + command);
					} else if (!start && !inpNUM) {
						// label.setText(label.getText()+display.getText()+command);
						calculate(display.getText());
						label.setText(display.getText() + command);
						lastCommand = command;
						inpNUM = true;
						// start = true;
					}
					sing = false;
				}
				break;
			}
		}
	}

	public void calculate(String x) {
		if (lastCommand.equals("+")) {
			variable = new SmallInteger(x);
			number.add(variable);
		} else if (lastCommand.equals("-")) {
			number.subtract(new SmallInteger(x));
		} else if (lastCommand.equals("*")) {
			number.multiply(new SmallInteger(x));
		} else if (lastCommand.equals("/")) {
			number.divide(new SmallInteger(x));
		} else if (lastCommand.equals("^")) {
			number.pow(new SmallInteger(x));
		}
		display.setText(number.toString());
	}

	private JLabel display;
	private JLabel label;
	private SmallInteger number;
	private SmallInteger variable;
	private JPanel panel;
	private JPanel PText;
	private String lastCommand;
	private String str;
	private boolean start;
	private boolean inpNUM;
	private boolean begin;
	private boolean sing;
}
