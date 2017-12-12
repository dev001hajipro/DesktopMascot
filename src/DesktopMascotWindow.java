import javax.swing.*;
import java.awt.*;

/**
 * Created by dev001hajipro on 2017/12/10.
 */
public class DesktopMascotWindow extends JFrame {
    private Mascot mascot;
    /**
     * 半透明のWindowを常にトップに表示する。
     */
    public DesktopMascotWindow() {
        setTitle("デスクトップマスコット");
        setUndecorated(true);
        System.out.printf("resolution %d DPI\n", Toolkit.getDefaultToolkit().getScreenResolution());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        System.out.printf("width: %d, height: %d\n",
                gd.getDisplayMode().getWidth(),
                gd.getDisplayMode().getHeight());

        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        setSize(screenWidth, screenHeight);
        setLocation(0, 0);
        setAlwaysOnTop(true);
        setBackground(new Color(250, 0,250, 0)); // ひとまず半透明
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ×ボタンでアプリ終了

        mascot = new Mascot(screenWidth, screenHeight);
        getContentPane().setLayout(null);
        getContentPane().add(mascot);

//        System.out.printf("insets.left:%d", this.getInsets().left);
//        mascot.setBounds(0, 0, mascot.getPreferredSize().width, mascot.getPreferredSize().height);

        setVisible(true);

        start();
    }

    public void start() {
        Timer timer = new Timer(100, e -> {
            mascot.move();
        });
        timer.start();
    }
}
