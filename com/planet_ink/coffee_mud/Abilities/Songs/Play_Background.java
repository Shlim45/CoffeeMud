package com.planet_ink.coffee_mud.Abilities.Songs;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;


import java.util.*;


/* 
   Copyright 2000-2006 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class Play_Background extends Play
{
	public String ID() { return "Play_Background"; }
	public String name(){ return "Background";}
	public int abstractQuality(){ return  Ability.QUALITY_BENEFICIAL_OTHERS;}
	public void affectCharStats(MOB mob, CharStats stats)
	{
		super.affectCharStats(mob,stats);
		if(invoker()!=null)
		{
			int cha=invoker().charStats().getStat(CharStats.STAT_CHARISMA)/2;
			int lvl=invokerLevel()/3;
			stats.setStat(CharStats.STAT_SAVE_ACID,stats.getStat(CharStats.STAT_SAVE_ACID)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_COLD,stats.getStat(CharStats.STAT_SAVE_COLD)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_DISEASE,stats.getStat(CharStats.STAT_SAVE_DISEASE)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_ELECTRIC,stats.getStat(CharStats.STAT_SAVE_ELECTRIC)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_FIRE,stats.getStat(CharStats.STAT_SAVE_FIRE)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_GAS,stats.getStat(CharStats.STAT_SAVE_GAS)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_GENERAL,stats.getStat(CharStats.STAT_SAVE_GENERAL)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_JUSTICE,stats.getStat(CharStats.STAT_SAVE_JUSTICE)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_MAGIC,stats.getStat(CharStats.STAT_SAVE_MAGIC)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_MIND,stats.getStat(CharStats.STAT_SAVE_MIND)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_PARALYSIS,stats.getStat(CharStats.STAT_SAVE_PARALYSIS)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_POISON,stats.getStat(CharStats.STAT_SAVE_POISON)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_TRAPS,stats.getStat(CharStats.STAT_SAVE_TRAPS)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_UNDEAD,stats.getStat(CharStats.STAT_SAVE_UNDEAD)+lvl+cha);
			stats.setStat(CharStats.STAT_SAVE_WATER,stats.getStat(CharStats.STAT_SAVE_WATER)+lvl+cha);
		}
	}
}

