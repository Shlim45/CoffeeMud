package com.planet_ink.coffee_mud.Abilities.Spells;
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
public class Spell_ReadMagic extends Spell
{
	public String ID() { return "Spell_ReadMagic"; }
	public String name(){return "Read Magic";}
	public String displayText(){return "(Ability to read magic)";}
	protected int canTargetCode(){return CAN_ITEMS;}
	public int classificationCode(){return Ability.ACODE_SPELL|Ability.DOMAIN_DIVINATION;}


	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto, int asLevel)
	{
		// first, using the commands vector, determine
		// the target of the spell.  If no target is specified,
		// the system will assume your combat target.
		Environmental target=getTarget(mob,null,givenTarget,commands,Item.WORNREQ_ANY);
		if(target==null) return false;

		// the invoke method for spells receives as
		// parameters the invoker, and the REMAINING
		// command line parameters, divided into words,
		// and added as String objects to a vector.
		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;


		boolean success=profficiencyCheck(mob,0,auto);
		if((success)&&(mob.fetchEffect(this.ID())==null))
		{
			Ability thisNewOne=(Ability)this.copyOf();
			mob.addEffect(thisNewOne);
			CMLib.commands().doStandardCommand(mob,"Read",CMParms.makeVector("READ",target));
			mob.delEffect(thisNewOne);
		}
		else
			return beneficialWordsFizzle(mob,target,"<S-NAME> incant(s) and gaze(s) over <T-NAMESELF>, but nothing more happens.");

		// return whether it worked
		return success;
	}
}
