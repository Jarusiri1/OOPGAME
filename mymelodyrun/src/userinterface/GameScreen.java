package userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.FontFormatException;

import objactgame.Chaser;
import objactgame.EnemiesManager;
import objactgame.Enemy;
import objactgame.Land;
import objactgame.MainCharacter;
import objactgame.Trees;
import util.Resource;

import java.io.File;
import java.io.IOException;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    public static final int GAME_FIRST_STATE = 0;
    public static final int GAME_PLAY_STATE = 1;
    public static final int GAME_OVER_STATE = 2;
    public static final int GAME_PAUSED_STATE = 3;
    public static final int GAME_WIN_STATE = 4;  // เพิ่มสถานะชนะ
    public static final float GROUNDY = 300;

    private MainCharacter mainCharacter;
    private Land land;
    private Trees trees;
    private EnemiesManager enemiesManager;
    private Thread thread;
    private int score;
    private int gameState = GAME_FIRST_STATE;
    private Chaser chaser; // ตัวแปรสำหรับ Chaser

    private int timeLeft = 60;  // เวลาที่เหลือ (วินาที)
 // เปลี่ยนตัวแปรสำหรับจับเวลาให้นับขึ้นแทนการนับถอยหลัง
    private int timeElapsed = 0;  // ตัวแปรสำหรับเวลาที่ผ่านไป (วินาที)
    private Timer timer;  // Timer สำหรับจับเวลา
    private Font pixelFont;  // ฟอนต์สำหรับ pixel

    private JButton backButton;
    private JButton nextLevelButton;
    
    private BufferedImage heartImage;
 // เพิ่มปุ่ม homeButton ในส่วนของตัวแปรสมาชิก
    private JButton homeButton;

    public GameScreen() {
        setDoubleBuffered(true);
        thread = new Thread(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        heartImage = Resource.getResourceImage("datagame/png/full.png"); // โหลดภาพหัวใจ
        if (heartImage == null) {
            System.out.println("Failed to load heart image.");
        }

        mainCharacter = new MainCharacter();
        mainCharacter.setX(250);
        mainCharacter.setY(70);

        land = new Land();
        trees = new Trees();
        enemiesManager = new EnemiesManager(mainCharacter, this);
        chaser = new Chaser(mainCharacter); // สร้าง Chaser โดยอิงจาก MainCharacter

     // ตกแต่งปุ่ม "Back to Home"
        backButton = new JButton("Back to Home");
        styleButton(backButton); // ใช้เมธอด styleButton เพื่อตกแต่งปุ่ม
        backButton.setBounds(300, 250, 200, 50);
        backButton.setVisible(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToHomeScreen();
            }
        });
        this.setLayout(null);
        this.add(backButton);
        
        // ตกแต่งปุ่ม "Home"
        homeButton = new JButton("Home");
        styleButton(homeButton); // ใช้เมธอด styleButton เพื่อตกแต่งปุ่ม
        homeButton.setBounds(300, 350, 200, 50); // ตั้งค่าตำแหน่งและขนาดของปุ่ม
        homeButton.setVisible(false);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToHomeScreen();
            }
        });
        this.add(homeButton);

     // ในส่วนของตัวจับเวลา (Timer) ให้เปลี่ยนการเพิ่มค่าเวลาแทนการลดค่า
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == GAME_PLAY_STATE) {
                    timeElapsed++;  // เพิ่มเวลาที่ผ่านไปทีละ 1 วินาที
                }
            }
        });

        try {
            // โหลดฟอนต์จากไฟล์
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, 
                        new File("datagame/font/Tiny5-Regular.ttf")).deriveFont(25f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();  // แสดงข้อผิดพลาดหากไม่สามารถโหลดฟอนต์ได้
            pixelFont = new Font("Monospaced", Font.PLAIN, 25);  // ฟอนต์สำรอง
        }
    }

 // เมธอดสำหรับตกแต่งปุ่ม
    private void styleButton(JButton button) {
        button.setFont(pixelFont);
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 182, 193)); // สีพื้นหลังชมพูอ่อน
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // ขอบปุ่มสีขาว
        button.setOpaque(true); // ทำให้พื้นหลังปุ่มแสดงผล
        button.setContentAreaFilled(true);

        // เพิ่มการเปลี่ยนสีเมื่อชี้เมาส์
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180)); // สีชมพูเข้มเมื่อชี้เมาส์
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 182, 193)); // สีเดิมเมื่อไม่ชี้เมาส์
            }
        });
    }
    public void startGame() {
        if (!thread.isAlive()) {
            thread.start();
        }
        timeLeft = 30;  // รีเซ็ตเวลา
        timer.start();  // เริ่มจับเวลา
    }

    private void goToHomeScreen() {
        JFrame parent = (JFrame) this.getTopLevelAncestor();
        parent.getContentPane().removeAll();
        parent.add(new HomeScreen(parent));
        parent.revalidate();
        parent.repaint();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        final double nsPerFrame = 1000000000.0 / 120;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerFrame;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        switch(gameState) {
            case GAME_PLAY_STATE:
                mainCharacter.update();  // อัปเดตตัวละครหลัก
                land.update(); // อัปเดตพื้นดินที่เลื่อน
                enemiesManager.update();
                enemiesManager.isCollision(); // ตรวจสอบการชน
                chaser.update(); // อัปเดตตัวไล่ล่า
                homeButton.setVisible(false); // แสดงปุ่มเมื่ออยู่ในสถานะ PAUSED
                backButton.setVisible(false);
                
                if (!mainCharacter.getAlive()) {  // ตรวจสอบหากตัวละครตาย
                    gameState = GAME_OVER_STATE;
                    backButton.setVisible(true);
                    timer.stop();  // หยุดตัวจับเวลาเมื่อเกมจบ
                }
                break;
            case GAME_PAUSED_STATE:
                homeButton.setVisible(true); // แสดงปุ่มเมื่ออยู่ในสถานะ PAUSED
                backButton.setVisible(true);
                break;
            
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 204, 229));  // พื้นหลังสีชมพู
        g2.fillRect(0, 0, getWidth(), getHeight());  // วาดพื้นหลัง
        
        g2.setFont(pixelFont);
        g2.setColor(Color.BLACK);
        Font largePixelFont = pixelFont.deriveFont(50f); 
        Font smallPixelFont = pixelFont.deriveFont(25f); 
        
        for (int i = 0; i < mainCharacter.getLife(); i++) {
            if (heartImage != null) {
                g2.drawImage(heartImage, 20 + i * 40, 50, 30 , 30, null);
            } else {
                System.out.println("Heart image is null.");
            }
        }

        switch (gameState) {
            case GAME_FIRST_STATE:
                mainCharacter.draw(g);
                break;
             // ในส่วนของ paintComponent() ให้แสดงเวลาที่ผ่านไป
            case GAME_PLAY_STATE:
                trees.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                chaser.draw(g); // วาดตัวไล่ล่า
                g2.drawString("SCORE: " + score, 620, 30);
                g2.drawString("TIME: " + timeElapsed, 50, 30);  // แสดงเวลาที่ผ่านไป
                break;
            case GAME_PAUSED_STATE:
                g2.setFont(largePixelFont);
                g2.drawString("PAUSED", 300, 150);
                g2.setFont(pixelFont);
                g2.drawString("Press 'P' to continue", 280, 200);
                break;
            case GAME_OVER_STATE:
                trees.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g2.setFont(largePixelFont);
                g2.drawString("GAME OVER", 280, 150);
                
                // แสดงคะแนนที่ได้เมื่อเกมจบ
                g2.setFont(smallPixelFont);
                g2.setColor(Color.GREEN);
                g2.drawString("FINAL SCORE: " + score, 320, 180);  // ตำแหน่งในการแสดงคะแนน (สามารถปรับตำแหน่งได้ตามต้องการ)
                break;
            case GAME_WIN_STATE:  // สถานะชนะ
                trees.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g2.setFont(largePixelFont);
                g2.setColor(Color.BLUE);
                g2.drawString("YOU WIN!", 300, 150);  // แสดงข้อความ "WIN" เมื่อชนะ
                // แสดงคะแนนที่ชนะ
                g2.setFont(smallPixelFont);
                g2.setColor(Color.WHITE);
                g2.drawString("FINAL SCORE: " + score, 30, 180);  // แสดงคะแนนสุดท้าย
                nextLevelButton.setVisible(true);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_FIRST_STATE) {
                    gameState = GAME_PLAY_STATE;
                    startGame();
                } else if (gameState == GAME_PLAY_STATE) {
                    mainCharacter.jump();
                } else if (gameState == GAME_OVER_STATE) {
                    resetGame();
                    gameState = GAME_PLAY_STATE;
                    startGame();
                }
                break;
            case KeyEvent.VK_P:
                if (gameState == GAME_PLAY_STATE) {
                    gameState = GAME_PAUSED_STATE;
                    timer.stop();  // หยุดเวลาเมื่อหยุดเกม
                } else if (gameState == GAME_PAUSED_STATE) {
                    gameState = GAME_PLAY_STATE;
                    timer.start();  // เริ่มเวลาใหม่
                }
                break;
        }
    }

    public void resetGame() {
    	mainCharacter.resetLife(); // รีเซ็ตจำนวนหัวใจ
        mainCharacter.setAlive(true);
        mainCharacter.setX(250);
        mainCharacter.setY(70);
        
        
        score = 0;
        timeLeft = 30;  // รีเซ็ตเวลา
        backButton.setVisible(false);
    }

    public void plusScore(int score) {
        this.score += score;
    }
}