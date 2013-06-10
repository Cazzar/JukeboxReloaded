package cazzar.mods.jukeboxreloaded.item;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.lib.util.LogHelper;
import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

public class ItemCustomRecord extends ItemRecord {
	
	
	
	private static HashMap<String, ItemCustomRecord>	records	= new HashMap<String, ItemCustomRecord>();
	
	String												recordInfo;
	String[]											details;
	
	public ItemCustomRecord(int ID, String recordName,
			String recordInfo, String... details) {
		super(ID, recordName);
		
		this.recordInfo = recordInfo;
		this.details = details;
		//registerSong(recordFile);
		records.put(recordName, this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordTitle() {
		return recordInfo;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer,
			List list, boolean par4) {
		list.add(getRecordTitle());
		for (String s : details) {
			list.add(s);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("cazzar:record_" + recordName);
	}
	
	public static Map<String, ItemCustomRecord> getRecords() {
		return records;
	}
}
