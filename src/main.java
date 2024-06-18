import javax.swing.*;
import java.awt.*;

/*
*入口启动类
 */
public class main {

    public static void main(String[] args) {
        //窗口
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("生命游戏");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.X_AXIS));

        //游戏视图
        GameView gameView = new GameView();

        //操作视图
        JPanel opsView = new JPanel();
        opsView.setLayout(new BoxLayout(opsView,BoxLayout.Y_AXIS));
        JButton btnStart = new JButton("开始");
        JButton btnStop = new JButton("停止");
        JButton btnClear = new JButton("清空");
        opsView.add(btnStart);
        opsView.add(btnStop);
        opsView.add(btnClear);
        btnStart.addActionListener(e -> gameView.start());
        btnStop.addActionListener(e -> gameView.stop());
        btnClear.addActionListener(e -> gameView.clear());

        //组装
        frame.add(gameView);
        frame.add(opsView);
        frame.pack();
        frame.setVisible(true);

        //将窗口放在屏幕正中
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int width = (int)(screenSize.getWidth() -  frame.getWidth())/2;
        int height = (int)(screenSize.getHeight() - frame.getHeight())/2;
        frame.setLocation(width,height);

        //游戏更新线程

        Thread thread = new Thread(gameView);
        thread.start();

    }
}
