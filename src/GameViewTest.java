/*import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

    @org.junit.jupiter.api.Test
    public  void testrun() {
    }

    @org.junit.jupiter.api.Test
    public void testupdateLife() {
    }
}*/
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class GameViewTest {

    private GameView gameView;

    @BeforeEach
    void setUp() {
        gameView = new GameView();
    }
    @AfterEach
    void tearDown() {
        gameView = null;
    }


    @Test
    void testSetLifeAndToggleLife() {
        // 设置生命
        gameView.setLife(10, 10, 1);
        assertEquals(1, gameView.items[0][0]); // 假设初始化后数组为0，设置后应为1

        // 切换生命状态
        gameView.toggleLife(10, 10);
        assertEquals(0, gameView.items[0][0]); // 切换后应变为0
    }

    @Test
    void testClear() {
        // 设置一些生命
        gameView.setLife(15, 15, 1);
        gameView.setLife(20, 20, 1);

        // 清除所有生命
        gameView.clear();

        // 检查所有生命是否都被清除
        for (int i = 0; i < gameView.row; i++) {
            for (int j = 0; j < gameView.col; j++) {
                assertEquals(0, gameView.items[i][j]);
            }
        }
    }

    @Test
    void testUpdateLife() {
        // 设置一些生命用于测试更新逻辑
        gameView.items[10][10] = 1;
        gameView.items[11][10] = 1;
        gameView.items[12][10] = 1;

        // 模拟生命游戏规则的更新
        gameView.updateLife();

        // 检查更新的结果是否符合预期
        // 这里需要根据生命游戏的规则来判断预期的结果
        // 例如，如果一个细胞周围有3个细胞，它应该复活
        assertTrue(gameView.items[11][10] == 1); // 中间的细胞应该存活
    }
    @Test
    void testUpdateLife_LonelyDeath() {
        // 设置初始状态，只有一个存活的细胞
        gameView.items[gameView.row / 2][gameView.col / 2] = 1;
        // 执行更新
        gameView.isUpdate = true;
        gameView.updateLife();
        // 验证孤独死亡规则
        assertEquals(0, gameView.items[gameView.row / 2][gameView.col / 2]);
    }

    @Test
    void testUpdateLife_CrowdedDeath() {
        // 设置初始状态，周围有多个存活的细胞
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    gameView.items[(gameView.row / 2) + i][(gameView.col / 2) + j] = 1;
                }
            }
        }
        // 执行更新
        gameView.isUpdate = true;
        gameView.updateLife();
        // 验证拥挤死亡规则
        assertEquals(0, gameView.items[gameView.row / 2][gameView.col / 2]);
    }

    @Test
    void testUpdateLife_Stability() {
        // 设置初始状态，有两个存活的邻居
        gameView.items[gameView.row / 2][gameView.col / 2] = 1;
        gameView.items[gameView.row / 2 - 1][gameView.col / 2] = 1;
        gameView.items[gameView.row / 2 + 1][gameView.col / 2] = 1;
        // 执行更新
        gameView.isUpdate = true;
        gameView.updateLife();
        // 验证稳定规则
        assertEquals(1, gameView.items[gameView.row / 2][gameView.col / 2]);
    }

    @Test
    void testUpdateLife_Resurrection() {
        // 设置初始状态，有三个存活的邻居
        gameView.items[gameView.row / 2][gameView.col / 2] = 0;
        gameView.items[gameView.row / 2 - 1][gameView.col / 2 - 1] = 1;
        gameView.items[gameView.row / 2 - 1][gameView.col / 2 + 1] = 1;
        gameView.items[gameView.row / 2 + 1][gameView.col / 2 - 1] = 1;
        // 执行更新
        gameView.isUpdate = true;
        gameView.updateLife();
        // 验证复活规则
        assertEquals(1, gameView.items[gameView.row / 2][gameView.col / 2]);
    }

    @Test
    void testPaint() {
        // 由于 paint 方法是与图形界面相关的，通常不会直接测试
        // 但是可以检查一些基本的行为，例如是否设置了正确的首选大小
        assertEquals(new Dimension(gameView.fullWidth, gameView.fullHeight), gameView.getPreferredSize());
    }
}

