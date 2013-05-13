import java.awt.AWTException;
import java.awt.Robot;

public class KeyPresser {

  private Robot robot;

  private Robot getRobot() {
    if (robot == null) {
      try {
        robot = new Robot();
      } catch (AWTException e) {
        e.printStackTrace();
      }
    }

    return robot;
  }

  public void presskKey(int key) {
    getRobot().keyPress(key);
    System.out.println("PressKey: " + key);
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    getRobot().keyRelease(key);

    try {
      Thread.sleep(450);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
