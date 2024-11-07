package objactgame;

import java.awt.image.BufferedImage;
import java.util.List;

public class Level {
    private String backgroundPath;  // เก็บพาธของรูปพื้นหลัง
    private List<BufferedImage> enemies;  // รายการของศัตรูในด่าน

    // Constructor สำหรับสร้าง Level ใหม่
    public Level(String backgroundPath, List<BufferedImage> enemies) {
        this.backgroundPath = backgroundPath;
        this.enemies = enemies;
    }

    // Getter สำหรับพื้นหลัง
    public String getBackgroundPath() {
        return backgroundPath;
    }

    // Getter สำหรับรายการศัตรู
    public List<BufferedImage> getEnemies() {
        return enemies;
    }
}

