/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.blocks;

import net.cazzar.mods.jukeboxreloaded.blocks.tile.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.util.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockJukebox extends BlockContainer {
    private IIcon[] buffer = new IIcon[4];

    public BlockJukebox() {
        super(Material.wood);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        if (side == ForgeDirection.UP.ordinal()) return buffer[2];
        if (side == ForgeDirection.DOWN.ordinal()) return buffer[2];
        final TileJukebox te = (TileJukebox) world.getTileEntity(x, y, z);

        ForgeDirection front, s = ForgeDirection.UNKNOWN;
        front = te.getFacing();

        switch (front) {
            case NORTH:
            case SOUTH:
                s = ForgeDirection.WEST;
                break;
            case WEST:
            case EAST:
                s = ForgeDirection.NORTH;
        }

        if (side == s.ordinal() || side == s.getOpposite().ordinal()) return buffer[1];
        if (side == front.ordinal()) return buffer[3];
        else return buffer[0];
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, ent, stack);
        final int i = MathHelper.floor_double(ent.rotationYaw * 4F / 360F + 0.5D) & 3;
        final TileJukebox jukebox = (TileJukebox) world.getTileEntity(x, y, z);
        switch (i) {
            case 0:
                jukebox.setFacing(ForgeDirection.NORTH);
                break;
            case 1:
                jukebox.setFacing(ForgeDirection.EAST);
                break;
            case 2:
                jukebox.setFacing(ForgeDirection.SOUTH);
                break;
            case 3:
                jukebox.setFacing(ForgeDirection.WEST);
                break;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        buffer[0] = register.registerIcon(ModInfo.MODID + ":jukeboxbottom");
        buffer[1] = register.registerIcon(ModInfo.MODID + ":jukeboxside");
        buffer[2] = register.registerIcon(ModInfo.MODID + ":jukeboxtop");
        buffer[3] = register.registerIcon(ModInfo.MODID + ":jukeboxfront");
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileJukebox();
    }
}
