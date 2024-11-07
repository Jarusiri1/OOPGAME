package objactgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import util.Resource;

public class Chaser {
    private float x; // ตำแหน่ง X
    private float y; // ตำแหน่ง Y
    private float speed; // ความเร็ว
    private MainCharacter target; // ตัวละครที่ถูกไล่ตาม
    private BufferedImage image; // รูปภาพของ Chaser

    public Chaser(MainCharacter target) {
        this.target = target;
        this.x = 150; // เริ่มต้นที่ตำแหน่ง X (ขวาของหน้าจอ)
        this.y = 200; // เริ่มต้นที่ตำแหน่ง Y
        this.speed = 2; // ความเร็วในการวิ่ง
        image = Resource.getResourceImage("datagame/littleghost.png"); // โหลดภาพ Chaser
        image = resizeImage(image, 100, 100); // ปรับขนาดภาพ Chaser ให้มีขนาด 50x50 พิกเซล
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    public void update() {
        // ไล่ตามตัวละครหลัก
        if (x > target.getX()) {
            x -= speed; // ลดค่า X เพื่อลดตำแหน่งไปทางซ้าย
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null); // วาด Chaser
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
