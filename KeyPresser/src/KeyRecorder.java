import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

public class KeyRecorder implements Serializable {

  private static final long serialVersionUID = 1L;
  private List<Integer> myRecord = new SerArrayList<Integer>();
  private int countEsc = 0;
  private int lastKeyPress = -1;

  public void record(int key) {
    if (key == KeyEvent.VK_ESCAPE && lastKeyPress == KeyEvent.VK_ESCAPE) {
      countEsc++;
    } else {
      countEsc = 0;
      if (!(key == KeyEvent.VK_ESCAPE)) {
        myRecord.add(key);
      }
    }
    lastKeyPress = key;
  }

  public boolean sollAbbrechen() {
    return countEsc >= 2;
  }

  public List<Integer> getRecords() {
    return myRecord;
  }

  @Override
  public String toString() {
    String retVal = "";

    for (Integer integer : myRecord) {
      retVal += integer + " ";
    }

    return retVal + "\n";
  }

  public String getWithNewLines() {
    String retVal = "";

    for (Integer integer : myRecord) {
      retVal += getKlartextForKey(integer) + "\n";
    }

    return retVal;
  }

  public String getKlartextForKey(int key) {
    switch (key) {
      case KeyEvent.VK_0:
        return "0";
      case KeyEvent.VK_1:
        return "1";
      case KeyEvent.VK_2:
        return "2";
      case KeyEvent.VK_3:
        return "3";
      case KeyEvent.VK_4:
        return "4";
      case KeyEvent.VK_5:
        return "5";
      case KeyEvent.VK_6:
        return "6";
      case KeyEvent.VK_7:
        return "7";
      case KeyEvent.VK_8:
        return "8";
      case KeyEvent.VK_9:
        return "9";
      case KeyEvent.VK_A:
        return "a";
      case KeyEvent.VK_B:
        return "b";
      case KeyEvent.VK_C:
        return "c";
      case KeyEvent.VK_D:
        return "d";
      case KeyEvent.VK_E:
        return "e";
      case KeyEvent.VK_F:
        return "f";
      case KeyEvent.VK_G:
        return "g";
      case KeyEvent.VK_H:
        return "h";
      case KeyEvent.VK_I:
        return "i";
      case KeyEvent.VK_J:
        return "j";
      case KeyEvent.VK_K:
        return "k";
      case KeyEvent.VK_L:
        return "l";
      case KeyEvent.VK_M:
        return "m";
      case KeyEvent.VK_N:
        return "n";
      case KeyEvent.VK_O:
        return "o";
      case KeyEvent.VK_P:
        return "p";
      case KeyEvent.VK_Q:
        return "q";
      case KeyEvent.VK_R:
        return "r";
      case KeyEvent.VK_S:
        return "s";
      case KeyEvent.VK_T:
        return "t";
      case KeyEvent.VK_U:
        return "u";
      case KeyEvent.VK_V:
        return "v";
      case KeyEvent.VK_W:
        return "w";
      case KeyEvent.VK_X:
        return "x";
      case KeyEvent.VK_Y:
        return "y";
      case KeyEvent.VK_Z:
        return "z";
      case KeyEvent.VK_F1:
        return "F1";
      case KeyEvent.VK_F2:
        return "F2";
      case KeyEvent.VK_F3:
        return "F3";
      case KeyEvent.VK_F4:
        return "F4";
      case KeyEvent.VK_F5:
        return "F5";
      case KeyEvent.VK_F6:
        return "F6";
      case KeyEvent.VK_F7:
        return "F7";
      case KeyEvent.VK_F8:
        return "F8";
      case KeyEvent.VK_F9:
        return "F9";
      case KeyEvent.VK_F10:
        return "F10";
      case KeyEvent.VK_F11:
        return "F11";
      case KeyEvent.VK_F12:
        return "F12";
      default:
        return "";
    }
  }

}
