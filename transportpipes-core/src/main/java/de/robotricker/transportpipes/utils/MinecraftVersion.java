package de.robotricker.transportpipes.utils;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MinecraftVersion {

    private static final Pattern VERSION = Pattern.compile("^\\d+(?:\\.\\d+){0,2}");
    private static final int[] CURRENT = parse(current());

    private MinecraftVersion() {}

    public static String current() {
        String raw = Bukkit.getBukkitVersion().split("-")[0];
        Matcher matcher = VERSION.matcher(raw);
        return matcher.find() ? matcher.group() : raw;
    }

    public static boolean isAtLeast(int major, int minor, int patch) {
        return compare(CURRENT, new int[]{major, minor, patch}) >= 0;
    }

    public static boolean isAtLeast(String version, int major, int minor, int patch) {
        return compare(parse(version), new int[]{major, minor, patch}) >= 0;
    }

    private static int[] parse(String version) {
        String[] parts = version.split("\\.");
        int[] result = new int[3];
        for (int i = 0; i < 3 && i < parts.length; i++) {
            try {
                result[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                result[i] = 0;
            }
        }
        return result;
    }

    private static int compare(int[] a, int[] b) {
        for (int i = 0; i < 3; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }
        return 0;
    }
}
