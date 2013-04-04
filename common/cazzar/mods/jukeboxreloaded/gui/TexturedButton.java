package cazzar.mods.jukeboxreloaded.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class TexturedButton extends GuiButton {

	private String textureFile;
	private int xOffset, yOffset, yOffsetForDisabled, xOffsetForDisabled,
	xOffsetForHovered, yOffsetForHovered;

	public TexturedButton(int id, int xPosition, int yPosition, int width, int height,
			String textureFile, int xOffset, int yOffset, int xOffsetForDisabled,
			int yOffsetForDisabled, int xOffsetForHovered, int yOffsetForHovered) {

		super(id, xPosition, yPosition, width, height, "");
		
		this.textureFile = textureFile;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xOffsetForDisabled = xOffsetForDisabled;
		this.yOffsetForDisabled = yOffsetForDisabled;
		this.xOffsetForHovered = xOffsetForHovered;
		this.yOffsetForHovered = yOffsetForHovered;
	}

	@Override
	public void drawButton(Minecraft mc, int par2, int par3) {
		if (this.drawButton) {
			mc.renderEngine.bindTexture(this.textureFile);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			
			//int hoverStatus = this.getHoverState(this.field_82253_i);
		    //Args: x, y, xOffset, yOffset, Width, Height
			
			switch (this.getHoverState(this.field_82253_i))
			{
				case 0:
					//Disabled
					this.drawTexturedModalRect(this.xPosition, this.yPosition,
							this.xOffsetForDisabled, this.yOffsetForDisabled,
							this.width, this.height);
					break;
				case 1:
					//not hovering
					this.drawTexturedModalRect(this.xPosition, this.yPosition,
							this.xOffset, this.yOffset,
							this.width, this.height);
					break;
				case 2:
					//hovering
					this.drawTexturedModalRect(this.xPosition, this.yPosition,
							this.xOffsetForHovered, this.yOffsetForHovered,
							this.width, this.height);
					break;
			}
			
			//this.drawTexturedModalRect(this.xPosition, this.yPosition,
			//		this.xOffset, this.yOffset, this.width, this.height);
		}
	}
}
