package objactgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import util.Resource;

public class MainCharacter {
	public static final float GRAVITY = 0.07f;
    public static final float GROUNDY = 300;
	private float x = 0;
    private float y = 0;
    private float speedY = 0;
    private Rectangle rect;
    private boolean isAlive = true;
    
    private BufferedImage characterImage;
    private BufferedImage scaledImage;
    private int characterWidth = 100;  // กำหนดขนาดที่ต้องการ
    private int characterHeight = 140; // กำหนดขนาดที่ต้องการ
    
    private int life = 3;

    public MainCharacter() {
    	// โหลดภาพ
        characterImage = Resource.getResourceImage("datagame/mymelody1.png");
        rect = new Rectangle();
        scaledImage = resizeImage(characterImage, characterWidth, characterHeight); // ปรับขนาดภาพ
    }

    // เมธอดสำหรับปรับขนาดภาพ
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    public void update() {
        // กระโดดและแรงโน้มถ่วง
        if (y >= GROUNDY - scaledImage.getHeight()) {
            speedY = 0;
            y = GROUNDY - scaledImage.getHeight();
        } else {
            speedY += GRAVITY;
            y += speedY;
        }
        rect.x = (int) x;
        rect.y = (int) y;
        rect.width = scaledImage.getWidth();  // กำหนดความกว้างของตัวละคร
        rect.height = scaledImage.getHeight(); // กำหนดความสูงของตัวละคร
    }
    
    public Rectangle getBound() {
        int offsetX = 20;  // ระยะขอบด้านซ้ายและขวาที่ลดลง
        int offsetY = 10;  // ระยะขอบด้านบนและล่างที่ลดลง
        int width = scaledImage.getWidth() - 2 * offsetX;  // ลดความกว้าง
        int height = scaledImage.getHeight() - 2 * offsetY;  // ลดความสูง

        return new Rectangle((int) x + offsetX, (int) y + offsetY, width, height);  
    }

    
    public void draw(Graphics g) {
        g.drawImage(scaledImage, (int) x, (int) y, null); // วาดตัวละคร
        g.setColor(java.awt.Color.RED);  // ใช้สีแดงสำหรับhitbox
        //Rectangle hitbox = getBound();
        //g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height); 
    }

    
    public void jump() {
    	speedY = -4;  // กระโดดขึ้นเมื่อกดปุ่ม
        y += speedY;
    }
    
    public void decreaseLife() {
        if (life > 0) {
            life--;
            System.out.println("Life decreased! Current life: " + life);
            if (life == 0) {
                isAlive = false; // ถ้าหัวใจหมด ตัวละครตาย
                System.out.println("Character died.");
            }
        }
    }

    
    public void resetLife() {
        life = 3;
        isAlive = true;
    }

    // Getter และ Setter ต่าง ๆ
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    
    public void setAlive(boolean alive) {
    	isAlive = alive;
    }
    
    public boolean getAlive() {
    	return isAlive;
    }
    public void decreaseSpeed() {
        speedY *= 0.5; // ลดความเร็วลงครึ่งหนึ่ง
    }
    
    public int getLife() {
        return life;
    }
    
    
}



