package objactgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import util.Resource;

public class Rocks extends Enemy {

    private BufferedImage image;
    private BufferedImage scaledImage;
    private int posX, posY;
    private MainCharacter mainCharacter;
    private int rockWidth = 55;  // กำหนดความกว้างของก้อนหิน
    private int rockHeight = 40; // กำหนดความสูงของก้อนหิน
    private int speedX = 2;      // ความเร็วในการเลื่อนหินไปทางซ้าย
    private boolean isScoreGot = false;

    public Rocks(MainCharacter mainCharacter) {
    	this.mainCharacter = mainCharacter;
        image = Resource.getResourceImage("datagame/png/rock.png");
        posX = 500;
        posY = 250;
        scaledImage = resizeImage(image, rockWidth, rockHeight);  // ปรับขนาดภาพหิน
    }

    @Override
    public void update() {
    		posX -= speedX;  // ลดค่า posX เพื่อลดตำแหน่งไปทางซ้าย
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(scaledImage, posX, posY, null); // วาดหิน
        g.setColor(java.awt.Color.BLUE);  
        Rectangle hitbox = getBound();
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height); 
    }


    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getWidth() {
        return rockWidth;
    }

    public void setX(int x) {
        this.posX = x;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        scaledImage = resizeImage(image, rockWidth, rockHeight);
    }

    @Override
    public Rectangle getBound() {
        int offsetX = 5;  // ระยะขอบด้านซ้ายและขวาที่ลดลง
        int offsetY = 5;  // ระยะขอบด้านบนและล่างที่ลดลง
        int width = rockWidth - 2 * offsetX;  // ลดความกว้าง
        int height = rockHeight - 2 * offsetY;  // ลดความสูง

        return new Rectangle(posX + offsetX, posY + offsetY, width, height);  // คืนค่า hitbox ของหิน
    }


    // ฟังก์ชันสำหรับปรับขนาดภาพ
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // ปรับคุณภาพของการย่อภาพ
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }
    
    @Override
    public boolean isOutOfScreen() {
    	return (posX + image.getWidth() < 0);
    }
    @Override
    public boolean isOver() {
    	return(mainCharacter.getX() > posX);
    }
    
    @Override
    public boolean isScoreGot() {
    	return isScoreGot;
    }
    
    @Override
    public void setIsScoreGot(boolean isScoreGot) {
        this.isScoreGot = isScoreGot;
    }

	@Override
	protected boolean isColliding(MainCharacter mainCharacter) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
