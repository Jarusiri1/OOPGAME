package objactgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static userinterface.GameScreen.GROUNDY;

import util.Resource;

public class Land {
    private BufferedImage imageLand1, imageLand2, imageLand3;
    private int landWidth = 300;  // ความกว้างของพื้นดินแต่ละบล็อก
    private int landHeight = 50; // ความสูงของพื้นดินแต่ละบล็อก
    private int blockSpacing = 0; // ความห่างระหว่างบล็อกพื้นดินแต่ละบล็อก
    
    private List<ImageLand> listImage; // ลิสต์ของบล็อกพื้นดิน
    private Random random;
    
    private int landSpeed = 5;  // ความเร็วในการเลื่อนของพื้นดิน

    public Land() {
        imageLand1 = Resource.getResourceImage("datagame/Land/Land1.png");
        imageLand2 = Resource.getResourceImage("datagame/Land/Land2.png");
        imageLand3 = Resource.getResourceImage("datagame/Land/Land3.png");

        listImage = new ArrayList<>();
        random = new Random();
        
        // เพิ่มบล็อกพื้นดินหลายอันและให้ติดกัน โดยเพิ่ม blockSpacing
        int initialX = 0;
        for (int i = 0; i < 500; i++) {  // สร้างพื้นดิน 100 บล็อก
            ImageLand imageLand = new ImageLand();
            imageLand.posX = initialX;  // กำหนดตำแหน่ง X
            imageLand.image = getRandomLandImage();  // เลือกภาพพื้นดินแบบสุ่ม
            listImage.add(imageLand);  // เพิ่มบล็อกพื้นดินในลิสต์
            
            // ตำแหน่ง X ของบล็อกถัดไปคือ X ของบล็อกปัจจุบัน + ความกว้างของพื้นดิน + ความห่าง
            initialX += landWidth + blockSpacing;
        }
    }
    
    public void setSpeed(int speed) {
        this.landSpeed = speed;  // ปรับความเร็วของพื้นดิน
    }

    public void update() {
        for (ImageLand imageLand : listImage) {
            imageLand.posX -= landSpeed;
            if (imageLand.posX + imageLand.image.getWidth() < 0) {
                imageLand.posX = listImage.get(listImage.size() - 1).posX 
                    + imageLand.image.getWidth();
            }
        }
    }
    
    // ฟังก์ชันสุ่มภาพพื้นดิน
    private BufferedImage getRandomLandImage() {
        int rand = random.nextInt(3);
        if (rand == 0) {
            return imageLand1;
        } else if (rand == 1) {
            return imageLand2;
        } else {
            return imageLand3;
        }
    }



    public void draw(Graphics g) {
        for (ImageLand imageLand : listImage) {
            // วาดภาพพื้นดินที่ตำแหน่งที่กำหนด
            g.drawImage(imageLand.image, imageLand.posX, (int) GROUNDY - 10, landWidth, landHeight, null);
        }
    }

    // คลาสสำหรับจัดการตำแหน่งและภาพของแต่ละบล็อกพื้นดิน
    public class ImageLand {
        int posX;  // ตำแหน่ง X
        BufferedImage image;  // ภาพของบล็อกพื้นดิน
    }
}







