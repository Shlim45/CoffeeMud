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
public class Song_Flight extends Song
{
	public String ID() { return "Song_Flight"; }
	public String name(){ return "Flight";}
	public int abstractQuality(){ return Ability.QUALITY_INDIFFERENT;}
	protected boolean skipStandardSongInvoke(){return true;}

	public boolean tick(Tickable ticking, int tickID)
	{
		if(!super.tick(ticking,tickID))
			return false;

		MOB mob=(MOB)affected;
		if(mob==null) return false;
		if(mob==invoker) return true;
		if(mob.amFollowing()!=invoker)
			return false;
		return true;
	}

	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto, int asLevel)
	{
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		if((!auto)&&(!CMLib.flags().canSpeak(mob)))
		{
			mob.tell("You can't sing!");
			return false;
		}

		boolean success=profficiencyCheck(mob,0,auto);
		unsing(mob,mob,true);
		if(success)
		{
			String str=auto?"^SThe "+songOf()+" begins to play!^?":"^S<S-NAME> begin(s) to sing the "+songOf()+".^?";
			if((!auto)&&(mob.fetchEffect(this.ID())!=null))
				str="^S<S-NAME> start(s) the "+songOf()+" over again.^?";

			CMMsg msg=CMClass.getMsg(mob,null,this,verbalCastCode(mob,null,auto),str);
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				invoker=mob;
				
				HashSet h=properTargets(mob,givenTarget,auto);
				if(h==null) return false;

				// malicious songs must not affect the invoker!
				if(!h.contains(mob)) h.add(mob);

				for(Iterator f=h.iterator();f.hasNext();)
				{
					MOB follower=(MOB)f.next();
					// malicious songs must not affect the invoker!
					int affectType=CMMsg.MSG_CAST_VERBAL_SPELL;
					if((castingQuality(mob,follower)==Ability.QUALITY_MALICIOUS)&&(follower!=mob))
						affectType=CMMsg.MSG_CAST_ATTACK_VERBAL_SPELL;
					if(auto) affectType=affectType|CMMsg.MASK_GENERAL;

					if((CMLib.flags().canBeHeardBy(invoker,follower)&&(follower.fetchEffect(this.ID())==null)))
					{
						CMMsg msg2=CMClass.getMsg(mob,follower,this,affectType,null);
						if(mob.location().okMessage(mob,msg2))
						{
							mob.location().send(mob,msg2);
							if(msg2.value()<=0)
							{
								int directionCode=-1;
								String direction="";
								for(int d=0;d<7;d++)
								{
									Exit thisExit=mob.location().getExitInDir(d);
									if(thisExit!=null)
									{
										if(thisExit.isOpen())
										{
											direction=Directions.getDirectionName(d);
											break;
										}
									}
								}
								directionCode=Directions.getDirectionCode(direction);
								if(directionCode<0)
								{
									mob.tell("Flee where?!");
									return false;
								}
								CMLib.tracking().move(follower,directionCode,true,false);
							}
						}
					}
				}
			}
		}
		else
			mob.location().show(mob,null,CMMsg.MSG_NOISE,"<S-NAME> hit(s) a foul note.");

		return success;
	}
}
