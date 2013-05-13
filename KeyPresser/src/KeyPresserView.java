import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class KeyPresserView extends JFrame {

  private JPanel contentPane;
  private JButton btnRecord;
  private JTextArea textArea;
  private JComboBox comboBox;
  private JButton btnStart;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          KeyPresserView frame = new KeyPresserView();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public KeyPresserView() {
    setTitle("KeyPresser");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 304, 469);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    contentPane.add(getBtnRecord());
    contentPane.add(getScrollPane());
    contentPane.add(getComboBox());
    contentPane.add(getBtnStart());
    contentPane.add(getStundeTimePanel());
    contentPane.add(getMinuteTimePanel());
    contentPane.add(getSekundeTimePanel());
    contentPane.add(getDeleteButton());
    loadDaten();
  }

  private JButton getBtnRecord() {
    if (btnRecord == null) {
      btnRecord = new JButton("Record");
      btnRecord.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          startRecording();
        }
      });
      btnRecord.setBounds(12, 406, 130, 24);
    }
    return btnRecord;
  }

  private List<KeyRecorder> recordesKeyStrokes = new SerArrayList<KeyRecorder>();
  private JScrollPane scrollPane;
  private TimePanel stundeTimePanel;
  private TimePanel minuteTimePanel;
  private TimePanel sekundeTimePanel;
  private JButton deleteButton;

  private Component getListerComponent() {
    return getTextArea();
  }

  private void startRecording() {
    final KeyRecorder record = new KeyRecorder();

    getBtnRecord().setEnabled(false);

    getListerComponent().requestFocus();

    final KeyAdapter myKeyAdapter = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent arg0) {
        super.keyPressed(arg0);
        record.record(arg0.getKeyCode());
        getTextArea().setText(record.getWithNewLines());
      }
    };

    getListerComponent().addKeyListener(myKeyAdapter);

    new Thread(new Runnable() {

      @Override
      public void run() {
        while (!record.sollAbbrechen()) {
        }
        getListerComponent().removeKeyListener(myKeyAdapter);

        getBtnRecord().setEnabled(true);
        recordesKeyStrokes.add(record);
        updateComboBox();
      }
    }).start();

  }

  private void updateComboBox() {
    DefaultComboBoxModel model = (DefaultComboBoxModel) getComboBox().getModel();
    model.removeAllElements();
    for (KeyRecorder key : recordesKeyStrokes) {
      model.addElement(key);
    }
    saveDaten();
  }

  private void saveDaten() {
    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("dropdown.obj")));
      os.writeObject(recordesKeyStrokes);
      os.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  private void loadDaten() {
    try {
      ObjectInputStream os = new ObjectInputStream(new FileInputStream(new File("dropdown.obj")));
      recordesKeyStrokes = (List<KeyRecorder>) os.readObject();
      os.close();
      updateComboBox();
    } catch (FileNotFoundException e) {
      System.out.println("Datei nicht vorhanden!");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private JTextArea getTextArea() {
    if (textArea == null) {
      textArea = new JTextArea();
    }
    return textArea;
  }

  private DefaultComboBoxModel createComboBoxModel() {
    return new DefaultComboBoxModel();
  }

  private JComboBox getComboBox() {
    if (comboBox == null) {
      comboBox = new JComboBox(createComboBoxModel());
      comboBox.setBounds(12, 12, 273, 22);
    }
    return comboBox;
  }

  private JButton getBtnStart() {
    if (btnStart == null) {
      btnStart = new JButton("Play");
      btnStart.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          starteKeyPressing();
        }
      });
      btnStart.setBounds(155, 406, 130, 24);
    }
    return btnStart;
  }

  private Calendar getTime() {
    Calendar then = GregorianCalendar.getInstance();
    then.set(Calendar.HOUR_OF_DAY, getStundeTimePanel().getZeitWert());
    then.set(Calendar.MINUTE, getMinuteTimePanel().getZeitWert());
    then.set(Calendar.SECOND, getSekundeTimePanel().getZeitWert());

    return then;
  }

  private void starteKeyPressing() {

    getBtnStart().setEnabled(false);

    final Calendar then = getTime();
    // then.add(Calendar.SECOND, 10);

    new Thread(new Runnable() {

      @Override
      public void run() {
        Calendar now = GregorianCalendar.getInstance();
        while (now.before(then)) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          now = GregorianCalendar.getInstance();
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) getComboBox().getModel();
        KeyRecorder record = (KeyRecorder) model.getSelectedItem();

        KeyPresser presser = new KeyPresser();

        for (Integer inte : record.getRecords()) {
          try {
            presser.presskKey(inte);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        getBtnStart().setEnabled(true);
      }
    }).start();
  }

  private JScrollPane getScrollPane() {
    if (scrollPane == null) {
      scrollPane = new JScrollPane();
      scrollPane.setBounds(12, 74, 273, 189);
      scrollPane.setViewportView(getTextArea());
    }
    return scrollPane;
  }

  private TimePanel getStundeTimePanel() {
    if (stundeTimePanel == null) {
      stundeTimePanel = new TimePanel("Stunde", 0, 23, GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY));
      stundeTimePanel.setBounds(12, 275, 90, 120);
    }
    return stundeTimePanel;
  }

  private TimePanel getMinuteTimePanel() {
    if (minuteTimePanel == null) {
      minuteTimePanel = new TimePanel("Minute", 0, 59, GregorianCalendar.getInstance().get(Calendar.MINUTE));
      minuteTimePanel.setBounds(104, 275, 90, 120);
    }
    return minuteTimePanel;
  }

  private TimePanel getSekundeTimePanel() {
    if (sekundeTimePanel == null) {
      sekundeTimePanel = new TimePanel("Sekunde", 0, 59, GregorianCalendar.getInstance().get(Calendar.SECOND));
      sekundeTimePanel.setBounds(195, 275, 90, 120);
    }
    return sekundeTimePanel;
  }

  private JButton getDeleteButton() {
    if (deleteButton == null) {
      deleteButton = new JButton("Delete");
      deleteButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          DefaultComboBoxModel model = (DefaultComboBoxModel) getComboBox().getModel();
          recordesKeyStrokes.remove(model.getSelectedItem());

          saveDaten();
          updateComboBox();
        }
      });
      deleteButton.setBounds(12, 40, 273, 22);

      new Thread(new Runnable() {

        @Override
        public void run() {
          while (true) {
            DefaultComboBoxModel model = (DefaultComboBoxModel) getComboBox().getModel();
            if (model.getSize() < 1 || model.getSelectedItem() == null) {
              deleteButton.setEnabled(false);
            } else {
              deleteButton.setEnabled(true);
            }
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }).start();

    }
    return deleteButton;
  }
}
