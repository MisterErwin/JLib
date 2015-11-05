package com.j0ach1mmall3.jlib.methods;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since Unknown
 */
public final class ReflectionAPI {
    /**
     * Returns the current NMS version
     * @return The NMS version
     * @deprecated Replaced by {@link ReflectionAPI#getNmsVersion()}
     */
    @Deprecated
	public static String getVersion(){
        return getNmsVersion();
	}

    /**
     * Returns if Spigot is used
     * @return If Spigot is used
     */
	public static boolean useSpigot(){
		String path = "org.spigotmc.Metrics";
		try{
			Class.forName(path);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		    return false;
		}
	}

    /**
     * Returns the NMS class by name
     * @param name The name of the class
     * @return The class
     */
	public static Class<?> getNmsClass(String name){
		String className = "net.minecraft.server." + getNmsVersion() + "." + name;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e){
            e.printStackTrace();
        }
		return clazz;
	}

    /**
     * Returns the OBC class by name
     * @param name The name of the class
     * @return The class
     */
    public static Class<?> getObcClass(String name){
		String className = "org.bukkit.craftbukkit." + getNmsVersion() + "." + name;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return clazz;
	}

    /**
     * Returns the Handle of an Entity
     * @param entity The Entity
     * @return The Handle
     */
    public static Object getHandle(Entity entity){
		HashMap<Class<? extends Entity>, Method> handles = new HashMap<>();
		try {
			if (handles.get(entity.getClass()) != null)
				return handles.get(entity.getClass()).invoke(entity);
			else {
				Method getHandle = entity.getClass().getMethod("getHandle");
				handles.put(entity.getClass(), getHandle);
				return getHandle.invoke(entity);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Returns the Handle of a World
     * @param world The World
     * @return The Handle
     */
	public static Object getHandle(World world){
		Class<?> craftWorldClass = getObcClass("CraftWorld");
		try {
			return craftWorldClass.getMethod("getHandle").invoke(world);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * Sends a Packet to a Player
     * @param player The player to send the Packet to
     * @param packet The Packet to send
     */
	public static void sendPacket(Player player, Object packet) {
		Method sendPacket = null;
		try {
            Method m = getNmsClass("PlayerConnection").getDeclaredMethod("sendPacket", getNmsClass("Packet"));
            if(m != null) m.invoke(getNmsClass("EntityPlayer").getField("playerConnection").get(getHandle(player)), packet);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException e){
			e.printStackTrace();
		}
	}

    /**
     * Returns if the Version is bigger than or equal to a number at a certain depth
     * Examples:
     * - Bukkit version=1.8.3, depth=1, number=3 returns false
     * - Bukkit version=1.7.2, depth=2, number=8 returns true
     * @param depth The depth (position) of the number we should compare
     * @param number The number we should compare the version to
     * @return If the Version is bigger than or equal to a number at a certain depth
     */
    public static boolean verBiggerThan(int depth, int number) {
        return Parsing.parseInt(Bukkit.getBukkitVersion().split("\\-")[0].split("\\.")[depth]) >= number;
    }

    /**
     * Returns the Bukkit Version
     * @return The Bukkit Version
     */
    public static String getBukkitVersion() {
        return Bukkit.getBukkitVersion().split("\\-")[0];
    }

    /**
     * Returns the current NMS version
     * @return The NMS version
     */
    public static String getNmsVersion() {
        String[] array = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        if (array.length == 4) return array[3];
        return null;
    }
}