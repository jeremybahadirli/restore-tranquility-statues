package com.restoretranquilitystatues;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Restore Tranquility Statues"
)
public class RestoreTranquilityStatuesPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	private static final int STATUE_RESTORED = 0;
	private static final int STATUE_UNRESTORED = 2;
	private static final int GARDEN_QUEST_COMPLETED = 60;

	@Override
	protected void startUp() throws Exception
	{
		setStatues(STATUE_RESTORED);
	}

	@Override
	protected void shutDown() throws Exception
	{
		setStatues(STATUE_UNRESTORED);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		if (varbitChanged.getVarbitId() == VarbitID.GARDEN_SARADOMIN_STATUE_VARBIT)
		{
			setStatues(STATUE_RESTORED);
		}
	}

	private void setStatues(int status)
	{
		clientThread.invokeLater(() -> {
			if (client.getGameState() != GameState.LOGGED_IN)
			{
				return;
			}
			if (client.getVarbitValue(VarbitID.GARDEN_QUEST) != GARDEN_QUEST_COMPLETED)
			{
				return;
			}

			client.setVarbit(VarbitID.GARDEN_SARADOMIN_STATUE_VARBIT, status);
			client.setVarbit(VarbitID.GARDEN_KING_STATUE_VARBIT, status);
		});
	}
}
