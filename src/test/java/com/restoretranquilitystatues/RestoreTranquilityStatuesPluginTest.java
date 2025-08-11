package com.restoretranquilitystatues;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RestoreTranquilityStatuesPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RestoreTranquilityStatuesPlugin.class);
		RuneLite.main(args);
	}
}