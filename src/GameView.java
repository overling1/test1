import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
*游戏视图
 */

public class GameView extends JPanel implements Runnable{

    private static final int UNIT = 30;
    public int row = 26;
    public int col = 40;

    //算上间隙的宽
    public int fullWidth = col * UNIT + 1 * (col - 1);
    //算上间隙的高
    public int fullHeight = row * UNIT + 1 * (row - 1);

    public int[][] items = new int[row][col];

    public boolean isUpdate = false;
    public GameView(){
        setPreferredSize(new Dimension(fullWidth,fullHeight));
        //监听鼠标移动事件
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //左键添加
                if (SwingUtilities.isLeftMouseButton(e)){
                    setLife(e.getX(),e.getY(),1);
                }
                //右键移除
                if (SwingUtilities.isRightMouseButton(e)){
                    setLife(e.getX(),e.getY(),0);
                }

            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        //鼠标单击
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleLife(e.getX(),e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
    public void setLife(int x, int y, int flag){
        int r = y / (UNIT + 1);
        int c = x / (UNIT + 1);
        r = r < 0 ? 0 : r;
        c = c < 0 ? 0 : c;
        r = r >= row ? row - 1 : r;
        c = c >= col ? col - 1 : c;
        items[r][c] = flag;
        this.repaint();
    }
    public void toggleLife(int x, int y){
        int r = y / (UNIT + 1);
        int c = x / (UNIT + 1);
        r = r < 0 ? 0 : r;
        c = c < 0 ? 0 : c;
        r = r >= row ? row - 1 : r;
        c = c >= col ? col - 1 : c;
        if (items[r][c] == 0){
            items[r][c] = 1;
        }else {
            items[r][c] = 0;
        }
        this.repaint();
    }

    //游戏画面绘制
    @Override
    public void paint(Graphics g){
        //清楚画板
        g.setColor(Color.black);
        g.fillRect(0, 0, fullWidth, fullHeight);
        //绘制每一个生命
        for(int r = 0; r < row; r++){
            for(int c = 0; c < col; c++){
                //只有生命存活时才绘制出来
                g.setColor(Color.green);
                if (items[r][c] > 0){
                    //让出一素1像素的间隙
                    g.fillRect(c * UNIT + 1 * (c + 1),r * UNIT + 1 * ( r + 1), UNIT, UNIT);
                }
            }
        }
    }
    //游戏更新
    @Override
    public void run(){
        while (true){

            //生命的视图重绘
            this.repaint();

            //刷新速度控制
            try{
                Thread.sleep(250);
            }catch (Exception e){

            }
            //生命的数据更新
            this.updateLife();
        }
    }
    //依据生命游戏规则更新
    public void updateLife(){
        //如果未开启更新则不往下执行
        if (!isUpdate){
            return;
        }
        //第二代细胞
        int[][] next = new int[row][col];
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                //统计当前细胞四周的邻居数量
                int sum = 0;
                //上、下、左、右
                sum += calc(r - 1, c);
                sum += calc(r + 1, c);
                sum += calc(r, c - 1);
                sum += calc(r, c + 1);

                //左上、右上、左下、右下
                sum += calc(r - 1, c - 1);
                sum += calc(r - 1, c + 1);
                sum += calc(r + 1, c - 1);
                sum += calc(r + 1, c + 1);

                //System.out.println("r=" + r + ",c=" + c + ",邻居数量=" + sum);

                //孤单死亡：如果细胞的邻居小于一个，则该细胞在下一次状态将死亡
                if(sum <= 1){
                    next[r][c] = 0;
                }
                //拥挤死亡：如果细胞的邻居在四个以上，则该细胞在下一次状态将死亡
                else if (sum >= 4){
                    next[r][c] = 0;
                }
                //稳定：如果细胞的邻居为两个或三个，则下次状态为稳定存活
                else if (items[r][c] == 1 && (sum == 2 || sum == 3)){
                        next[r][c] = 1;
                }
                //复活：如果某位置原无细胞存活，而该位置的邻居为三个，则该位置复活一细胞。
                else if (sum == 3){
                    next[r][c] = 1;
                }
            }
        }
        this.items = next;
    }
    private int calc(int r, int c){
        if (r < 0 || r >= row || c < 0 || c >= col){
            return 0;
        }
        return items[r][c];
    }
    public void start(){this.isUpdate = true;}

    public void stop(){this.isUpdate = false;}

    public void clear(){
        this.items = new int[row][col];
        this.repaint();
    }
}
