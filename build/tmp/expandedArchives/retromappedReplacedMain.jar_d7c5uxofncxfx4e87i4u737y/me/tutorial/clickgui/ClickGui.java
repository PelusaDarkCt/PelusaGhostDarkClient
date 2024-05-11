package me.tutorial.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import me.tutorial.clickgui.component.Component;
import me.tutorial.clickgui.component.Frame;
import me.tutorial.module.Category;
import net.minecraft.client.gui.GuiScreen;

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
	public void func_73866_w_() {
	}
	
	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
		this.func_146276_q_();
		for(Frame frame : frames) {
			frame.renderFrame(this.field_146289_q);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
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
	protected void func_73869_a(char typedChar, int keyCode) {
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
            this.field_146297_k.func_147108_a(null);
        }
	}

	
	@Override
    protected void func_146286_b(int mouseX, int mouseY, int state) {
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
	public boolean func_73868_f() {
		return true;
	}
}
