import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TimePanel extends JPanel {
  private JButton plusButton;
  private JTextField zahlTextField;
  private JButton minusButton;
  private JLabel ueberschrift;
  private Integer min = 0;
  private Integer max = 1;
  private Integer akt = 0;

  public TimePanel() {
    this.setBounds(90, 90, 90, 120);
    setLayout(null);
    add(getPlusButton());
    add(getZahlTextField());
    add(getMinusButton());
    add(getUeberschrift());

    initalize();
  }

  public Integer getZeitWert() {
    return akt;
  }

  private void initalize() {
    getZahlTextField().setText(akt.toString());
    updateZahlFeld();
    validate();
    repaint();
  }

  public TimePanel(String ueberschrift, int min, int max, int init) {
    this();
    this.ueberschrift.setText(ueberschrift);
    this.min = min;
    this.max = max;
    this.akt = init;
    initalize();
  }

  private JButton getPlusButton() {
    if (plusButton == null) {
      plusButton = new JButton("+");
      plusButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          if (akt == max) {
            akt = min;
          } else {
            akt++;
          }
          updateZahlFeld();
        }
      });
      plusButton.setBounds(25, 20, 40, 30);
    }
    return plusButton;
  }

  private void updateZahlFeld() {
    this.getZahlTextField().setText(akt.toString());
  }

  private JTextField getZahlTextField() {
    if (zahlTextField == null) {
      zahlTextField = new JTextField();
      // zahlTextField.addPropertyChangeListener(new PropertyChangeListener() {
      // public void propertyChange(PropertyChangeEvent arg0) {
      // if (!"".equals(getZahlTextField().getText())) {
      // int parseInt = Integer.parseInt(getZahlTextField().getText());
      // if (!(min <= parseInt && parseInt <= max)) {
      // getZahlTextField().setText(akt.toString());
      // } else {
      // akt = parseInt;
      // updateZahlFeld();
      // }
      // }
      // }
      // });
      zahlTextField.setHorizontalAlignment(SwingConstants.CENTER);
      zahlTextField.setBounds(25, 54, 40, 30);
      zahlTextField.setColumns(10);

    }
    return zahlTextField;
  }

  private JButton getMinusButton() {
    if (minusButton == null) {
      minusButton = new JButton("-");
      minusButton.setBounds(25, 90, 40, 30);
      minusButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          if (akt == min) {
            akt = max;
          } else {
            akt--;
          }
          updateZahlFeld();
        }
      });
    }
    return minusButton;
  }

  private JLabel getUeberschrift() {
    if (ueberschrift == null) {
      ueberschrift = new JLabel("");
      ueberschrift.setHorizontalAlignment(SwingConstants.CENTER);
      ueberschrift.setBorder(BorderFactory.createEmptyBorder());
      ueberschrift.setBounds(0, 0, 90, 20);
    }
    return ueberschrift;
  }
}
