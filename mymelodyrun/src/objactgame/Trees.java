package objactgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Resource;

public class Trees {
    private BufferedImage treeImage;
    private List<TreeObject> trees;  // เก็บลิสต์ของต้นไม้ที่เคลื่อนไหว
    private int treeWidth = 150;  // ความกว้างของต้นไม้
    private int treeHeight = 150;  // ความสูงของต้นไม้
    private int treeSpeed = 3;  // ความเร็วของต้นไม้
    private Random random;

    // กำหนดตำแหน่ง Y ให้มีความสูงคงที่
    private final int fixedPosY = 80; // ระยะ Y ที่ต้องการ

    public Trees() {
        treeImage = Resource.getResourceImage("datagame/backgroud/Tree.png");
        trees = new ArrayList<>();
        random = new Random();
        
        // เพิ่มต้นไม้หลายต้นให้เรียงติดกันโดยไม่มีช่องว่าง
        int initialX = 0;
        for (int i = 0; i < 10; i++) {  // เพิ่มต้นไม้ 10 ต้น
            TreeObject tree = new TreeObject();
            tree.posX = initialX;
            // ใช้ตำแหน่ง Y คงที่
            tree.posY = fixedPosY; // กำหนดตำแหน่ง Y เป็นค่าคงที่
            trees.add(tree);
            
            // ต้นไม้ถัดไปจะอยู่ถัดจากต้นไม้ก่อนหน้าเท่ากับ treeWidth
            initialX += treeWidth; // อัพเดตตำแหน่ง X ของต้นไม้ถัดไป
        }
    }

    // อัปเดตการเคลื่อนไหวของต้นไม้
    public void update() {
        for (TreeObject tree : trees) {
            tree.posX -= treeSpeed;  // เลื่อนต้นไม้ไปทางซ้าย

            // ถ้าต้นไม้ออกจากจอทางซ้าย ให้ย้ายไปขวาสุดเพื่อทำให้วนซ้ำไปเรื่อยๆ
            if (tree.posX + treeWidth < 0) {
                TreeObject lastTree = trees.get(trees.size() - 1);  // ต้นไม้ที่อยู่ขวาสุด
                tree.posX = lastTree.posX + treeWidth;  // วางต้นไม้ให้อยู่ต่อท้าย
                // กำหนดตำแหน่ง Y ใหม่เป็นค่าคงที่
                tree.posY = fixedPosY; // กำหนดตำแหน่ง Y เป็นค่าคงที่
            }
        }
    }

    // วาดต้นไม้ทั้งหมด
    public void draw(Graphics g) {
        for (TreeObject tree : trees) {
            g.drawImage(treeImage, tree.posX, tree.posY, treeWidth, treeHeight, null);
        }
    }

    // คลาสสำหรับเก็บข้อมูลต้นไม้แต่ละต้น
    private class TreeObject {
        int posX;  // ตำแหน่ง X ของต้นไม้
        int posY;  // ตำแหน่ง Y ของต้นไม้
    }
}


