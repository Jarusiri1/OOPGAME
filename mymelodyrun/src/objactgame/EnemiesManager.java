package objactgame;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import userinterface.GameScreen;
import util.Resource;

public class EnemiesManager {
    private List<Enemy> enemies;
    
    private Random random;
    
    private BufferedImage imageRocks1, imageRocks2;
    private MainCharacter mainCharacter;
    private GameScreen gameScreen;
    
    private int minDistanceBetweenRocks = 300;  // ระยะห่างขั้นต่ำระหว่างหิน
    private int maxDistanceBetweenRocks = 500;  // ระยะห่างสูงสุดระหว่างหิน
    private int maxRocksOnScreen = 3;  // จำนวนหินสูงสุดที่ปรากฏบนหน้าจอ

    public EnemiesManager(MainCharacter mainCharacter , GameScreen gameScreen) {
    	this.gameScreen = gameScreen;
    	this.mainCharacter = mainCharacter;
        enemies = new ArrayList<>();
        imageRocks1 = Resource.getResourceImage("datagame/png/rock.png");
        imageRocks2 = Resource.getResourceImage("datagame/png/mushroom.png");
        random = new Random();
        
        // เพิ่มหินเริ่มต้นที่มีระยะห่างแบบสุ่ม
        int initialX = 1000;
        for (int i = 0; i < maxRocksOnScreen; i++) {
            int distance = random.nextInt(maxDistanceBetweenRocks - minDistanceBetweenRocks) + minDistanceBetweenRocks;
            enemies.add(getRandomRocks(initialX));  
            initialX += distance;  // เพิ่มระยะห่างแบบสุ่ม
        }
    }
    
    public void update() {
        List<Enemy> enemiesToRemove = new ArrayList<>();  // ลิสต์เก็บหินที่ออกนอกจอ
        

            for (Enemy e : enemies) {
                e.update();
                if (e.isOver() && !e.isScoreGot()) {
                    gameScreen.plusScore(20);
                    e.setIsScoreGot(true);
                }
                
                // ตรวจสอบว่ามีการชนกับ MainCharacter หรือไม่
                if (mainCharacter.getBound().intersects(e.getBound()) && !e.isScoreGot()) {
                    System.out.println("Collision detected!");
                    mainCharacter.decreaseLife();
                    e.setIsScoreGot(true);

                    // ถ้าหัวใจหมด ตัวละครจะตาย
                    if (mainCharacter.getLife() == 0) {
                        mainCharacter.setAlive(false);
                        System.out.println("Character is dead.");
                    }
                }
                

                if (e.getX() + e.getWidth() < 0) {
                    enemiesToRemove.add(e);
                }
            }

            enemies.removeAll(enemiesToRemove);
   
        // ลบหินที่ออกนอกจอแล้ว
        enemies.removeAll(enemiesToRemove);

        // เพิ่มหินใหม่เมื่อจำนวนหินน้อยกว่า maxRocksOnScreen
        if (enemies.size() < maxRocksOnScreen) {
            Enemy lastEnemy = enemies.get(enemies.size() - 1);  // หินก้อนสุดท้าย
            
            // ตรวจสอบว่าหินสุดท้ายอยู่ห่างพอสำหรับการเพิ่มหินใหม่
            if (lastEnemy.getX() < 600 - minDistanceBetweenRocks) {
                int distance = random.nextInt(maxDistanceBetweenRocks - minDistanceBetweenRocks) + minDistanceBetweenRocks;
                enemies.add(getRandomRocks(600 + distance));  // เพิ่มหินใหม่ที่ตำแหน่งสุ่ม
            }
        }
    }
    
    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }
    public List<Enemy> getEnemies() {
        return enemies; // Assuming 'enemies' is the list of enemies managed by this class.
    }
    
    public boolean isCollision() {
        for (Enemy e : enemies) {
            if (mainCharacter.getBound().intersects(e.getBound()) && !e.isScoreGot()) {
                // ลดจำนวนหัวใจ
                mainCharacter.decreaseLife();
                e.setIsScoreGot(true); // ป้องกันการลดหัวใจซ้ำ
                System.out.println("Collision detected! Lives remaining: " + mainCharacter.getLife());
                if (mainCharacter.getLife() == 0) {
                    mainCharacter.setAlive(false); // ตัวละครตายเมื่อหัวใจหมด
                }
                return true;
            }
        }
        return false;
    }

    
    public void reset() {
    	enemies.clear();
    	enemies.add(getRandomRocks(maxDistanceBetweenRocks));
    }
    
    private Rocks getRandomRocks(int startX) {
        Rocks rocks = new Rocks(mainCharacter);
        rocks.setX(startX);  // กำหนดตำแหน่ง X ของหินที่สร้างใหม่

        if (random.nextBoolean()) {
            rocks.setImage(imageRocks1);
        } else {
            rocks.setImage(imageRocks2);
        }
        
        return rocks;
    }
    
    public boolean isCollision1() {
        for (Enemy e : enemies) {
            if (mainCharacter.getBound().intersects(e.getBound()) && !e.isScoreGot()) {
                // ลดจำนวนหัวใจ
                mainCharacter.decreaseLife();
                e.setIsScoreGot(true); // ป้องกันการลดหัวใจซ้ำ
                
                System.out.println("Collision detected! Lives remaining: " + mainCharacter.getLife());
                
                if (mainCharacter.getLife() == 0) {
                    mainCharacter.setAlive(false); // ตัวละครตายเมื่อหัวใจหมด
                    System.out.println("Character is dead.");
                }
                return true;
            }
        }
        return false;
    }


    
   
}





