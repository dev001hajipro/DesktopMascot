import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by dev001hajipro on 2017/12/10.
 */
public class Mascot extends JPanel {
    private static final int ORIGIN_SIZE = 4;
    private static final int dx = (int)(6.4 * ORIGIN_SIZE);
    private static final int dy = (int)(2.0 * ORIGIN_SIZE);
    public JLabel label;
    // スプライトのポジションを維持。
    private int[] imageX = new int[4];
    private int[] imageY = new int[4];
    private int showIndex = 0;
    // 休止
    private int sleep;
    // 歩数
    private int step;
    // 方向
    private int direction;
    private Point point;
    private Random rand;

    private Dimension screenDimension;
    private int maxX;
    private int maxY;

    public Mascot(int screenWidth, int screenHeight) {
        rand = new Random();
        ImageIcon ii = new ImageIcon("r.png");
        int w = ii.getIconWidth();
        int h = ii.getIconHeight();
        Dimension characterDimension = new Dimension(w / 4, h / 4);
        this.screenDimension = new Dimension(screenWidth, screenHeight);
        maxX = this.screenDimension.width - characterDimension.width;
        maxY = this.screenDimension.height - characterDimension.height;

        point = new Point();
        setRandomPosition(point);

        for (int i = 0; i < 4; i++) {
            imageX[i] = characterDimension.width * -i;
        }
        for (int i = 0; i < 4; i++) {
            imageY[i] = characterDimension.height * -i;
        }
        label = new JLabel(ii);
        // JLabelのサイズとJPanelのサイズの意味。
        // 例えば、64pixelのキャラクターの歩行スプライト画像256x256の場合、
        // JLabelに256x256で貼り付ける。JPanelのサイズを64x64にして、
        // 一部分のみ表示するようにしている。つまりJPanelのサイズが表示サイズになっている。
        // JLabelのサイズは画像サイズで、この例の場合256x256。
        // label.setLocationでスプライト画像を移動して、キャラクターの歩行アニメーションを作る。
        label.setSize(w, h);
        this.setSize(characterDimension);

        add(label);
        setLayout(null);
        setOpaque(false); // 不透明=false、つまり透明
    }

    private int countupShowIndex() {
        showIndex++;
        if (showIndex >= 4) {
            showIndex = 0;
        }
        return showIndex;
    }

    public void move() {
        if (sleep > 0) {
            sleep--;
        } else {
            action();
        }
        repaint();
    }

    private void setRandomPosition(Point point) {
        point.x = rand.nextInt(maxX);
        point.y = rand.nextInt(maxY);
        setLocation(point);
    }

    private void toCenterPosition(Point point) {
        point.x = (int)Math.floor(maxX / 2);
        point.y = (int)Math.floor(maxY / 2);
        setLocation(point);
    }

    private void action() {
        if (step > 0) {
            moveAStep();
            step--;
        } else {
            stay();
            sleep = rand.nextInt(35); // 0 <= r < 5
            // 奇数だと一歩踏み出した画像になるので、歩行停止時に必ず偶数で静止させる。
            step = rand.nextInt(11) * 2;
            direction = rand.nextInt(4);
        }
    }

    private void moveAStep() {
        // 画像指定
        label.setLocation(imageX[countupShowIndex()], imageY[direction]);
        //setLocation(getX(), getY() + dy);
        //setBounds(0 + getInsets().left, 0 + getInsets().top, getPreferredSize().width, getPreferredSize().height);
        // スプライト指定
        switch (direction) {
            case 0: // down
                point.y += dy;
                if (point.y > maxY) {
                    point.y = maxY;
                    step = 0;
                }
                break;
            case 1: // left
                point.x -= dx;
                if (point.x < 0) {
                    point.x = 0;
                    step = 0;
                }
                break;
            case 2: // right
                point.x += dx;
                if (point.x > maxX) {
                    point.x = maxX;
                    step = 0;
                }
                break;
            default: // up
                point.y -= dy;
                if (point.y < 0) {
                    point.y = 0;
                    step = 0;
                }
                break;
        }
        setLocation(point);
    }

    // キャラクターの歩行を停止。
    // 壁に衝突したときに、歩行をすぐやめる。
    public void stay() {
        label.setLocation(imageX[0], imageY[direction]);
        // スプライトの0番目は、各方向を向いて停止している状態。
        showIndex = 0;
        step = 0;

        getParent().repaint();
    }
}
