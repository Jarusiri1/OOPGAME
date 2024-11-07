package objactgame;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
    public abstract void update();  // เมธอด update ที่ศัตรูทุกตัวต้องมี
    public abstract void draw(Graphics g);  // เมธอด draw ที่ศัตรูทุกตัวต้องมี
    public abstract int getX();  // ให้แต่ละศัตรูต้องมีเมธอด getX
    public abstract int getWidth();  // ให้แต่ละศัตรูต้องมีเมธอด getWidth
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen();
	public abstract boolean isOver();
	public abstract boolean isScoreGot();
	public abstract void setIsScoreGot(boolean isScoreGot);
	protected abstract boolean isColliding(MainCharacter mainCharacter);
	
}
