package me.client.clickgui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import me.client.clickgui.component.Component;
import me.client.clickgui.component.Frame;
import me.client.module.Category;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class ClickGui extends GuiScreen {

	public static ArrayList<Frame> frames;
	public static int color = -1;
	
	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		final FontRenderer fontRenderer = this.fontRendererObj;
		GL11.glEnable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2884);
		GL11.glScaled(1.5, 1.5, 1.5);
		final int posX = 0;
		this.mc.fontRendererObj.drawStringWithShadow("G", 5.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4850.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("h", 11.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4750.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("o", 17.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4650.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("s", 23.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4550.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("t", 28.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4450.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("d", 34.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4350.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("a", 40.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4250.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("r", 46.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4150.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("k", 52.0f, 5.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4050.0f, 0.7f, 0.7f));
		GL11.glScaled(0.6666666666666666, 0.6666666666666666, 0.6666666666666666);
		this.mc.fontRendererObj.drawStringWithShadow("v", 90.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4250.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("1", 97.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4150.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow(".", 104.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4100.0f, 0.7f, 0.7f));
		this.mc.fontRendererObj.drawStringWithShadow("0", 107.0f, 8.0f, Color.HSBtoRGB(System.currentTimeMillis() % 15000L / 4000.0f, 0.7f, 0.7f));
		GL11.glEnable(2884);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
