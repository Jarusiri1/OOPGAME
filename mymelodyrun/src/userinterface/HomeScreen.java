package userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HomeScreen extends JPanel {

    private JButton startButton;
    private JButton exitButton;
    private JFrame parentFrame;
    private JLabel titleLabel;
    private Font pixelFont;
    private BufferedImage backgroundImage;

    public HomeScreen(JFrame frame) {
        this.parentFrame = frame;
        File file = new File("datagame/png/full.png");
        if (file.exists()) {
            System.out.println("File found!");
        } else {
            System.out.println("File not found!");
        }

        // โหลดฟอนต์พิกเซล
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("datagame/font/Tiny5-Regular.ttf")).deriveFont(28f);
        } catch (FontFormatException | IOException e) {
            pixelFont = new Font("Monospaced", Font.BOLD, 28); // ฟอนต์สำรอง
            e.printStackTrace();
        }

        // โหลดภาพพื้นหลัง
        try {
            backgroundImage = ImageIO.read(new File("datagame/backgroud/back1.jpeg")); // ระบุเส้นทางของภาพพื้นหลัง
        } catch (IOException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดหากโหลดภาพไม่สำเร็จ
        }

        // ตั้งค่าการจัดเลย์เอาต์
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);  // ระยะห่างระหว่างองค์ประกอบ

        // สร้าง Title Label
        titleLabel = new JLabel("Welcome to the Game!");
        titleLabel.setFont(pixelFont);
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // สร้างปุ่ม Start
        startButton = new JButton("Start Game");
        styleButton(startButton);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(startButton, gbc);

        // สร้างปุ่ม Exit
        exitButton = new JButton("Exit");
        styleButton(exitButton);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(exitButton, gbc);

        // ActionListener สำหรับปุ่ม Start
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    parentFrame.getContentPane().removeAll();
                    GameScreen gameScreen = new GameScreen(); // ตรวจสอบว่าไฟล์นี้มีอยู่
                    parentFrame.add(gameScreen);
                    parentFrame.revalidate();
                    parentFrame.repaint();
                    gameScreen.requestFocusInWindow();
                    gameScreen.startGame();
                }).start();  // เริ่มเกมใน Thread ใหม่
            }
        });

        // ActionListener สำหรับปุ่ม Exit
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // ออกจากโปรแกรม
            }
        });
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // วาดภาพพื้นหลังถ้าภาพไม่เป็น null
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // วาดพื้นหลังแบบไล่เฉดสี (สามารถนำออกได้หากใช้ภาพพื้นหลัง)
        Color color1 = new Color(255, 182, 193); // สีชมพูอ่อน
        Color color2 = new Color(255, 105, 180); // สีชมพูเข้ม
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

 // เมธอดสำหรับจัดสไตล์ปุ่ม
    private void styleButton(JButton button) {
        button.setFont(pixelFont);
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 105, 180)); // สีชมพูเข้ม
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // เพิ่มการเปลี่ยนสีเมื่อเมาส์ชี้
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 182, 193)); // สีชมพูอ่อนเมื่อชี้เมาส์
                button.setForeground(Color.BLACK); // เปลี่ยนสีข้อความเมื่อชี้เมาส์
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // เปลี่ยนขอบเมื่อชี้เมาส์
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180)); // สีเดิมเมื่อไม่ชี้เมาส์
                button.setForeground(Color.WHITE); // คืนค่าการตั้งค่าสีข้อความเดิม
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // คืนค่าขอบเดิม
            }
        });
    }

    
}
