package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    public GameWindow() {
        super("Mymelody Run");
        setSize(800, 370);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ปิดโปรแกรมเมื่อกดปิดหน้าต่าง

        // เรียกใช้ HomeScreen แทนที่จะเป็น GameScreen ทันที
        HomeScreen homeScreen = new HomeScreen(this);
        add(homeScreen);

        setLocationRelativeTo(null);  // จัดตำแหน่งหน้าต่างให้อยู่ตรงกลางจอ
    }

    public static void main(String[] args) {
        GameWindow gw = new GameWindow();
        gw.setVisible(true);  // แสดงหน้าต่างเกม
        
    }
}
