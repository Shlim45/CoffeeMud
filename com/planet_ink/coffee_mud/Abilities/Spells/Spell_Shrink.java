package com.planet_ink.coffee_mud.Abilities.Spells;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;


public class Spell_Shrink extends Spell
{

	public Spell_Shrink()
	{
		super();
		myID=this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.')+1);
		name="Shrink";
		displayText="(Shrunk)";
		miscText="";

		canAffectCode=Ability.CAN_MOBS|Ability.CAN_ITEMS;
		canTargetCode=Ability.CAN_MOBS|Ability.CAN_ITEMS;

		canBeUninvoked=true;
		isAutoinvoked=false;
		quality=Ability.OK_OTHERS;

		baseEnvStats().setLevel(6);

		baseEnvStats().setAbility(0);
		uses=Integer.MAX_VALUE;
		recoverEnvStats();
	}

	public Environmental newInstance()
	{
		return new Spell_Shrink();
	}
	public int classificationCode()
	{
		return Ability.SPELL|Ability.DOMAIN_TRANSMUTATION;
	}


	public void unInvoke()
	{
		// undo the affects of this spell
		if((canBeUninvoked)&&(affected!=null))
		{
			if(affected instanceof MOB)
			{
				MOB mob=(MOB)affected;
				mob.tell("You return to your normal size.");
			}
			else
			if(affected instanceof Item)
			{
				Item item=(Item)affected;
				if(item.owner()!=null)
				{
					if(item.owner() instanceof Room)
						((Room)item.owner()).showHappens(Affect.MSG_OK_VISUAL,item.name()+" returns to its proper size.");
					else
					if(item.owner() instanceof MOB)
						((MOB)item.owner()).tell(item.name()+" returns to its proper size.");
				}
			}
		}
		super.unInvoke();
	}

	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		super.affectCharStats(affected,affectableStats);
		int str=affectableStats.getStat(CharStats.STRENGTH);
		int dex=affectableStats.getStat(CharStats.DEXTERITY);
		affectableStats.setStat(CharStats.STRENGTH,(str/10)+1);
		affectableStats.setStat(CharStats.DEXTERITY,(dex*2)+1);
	}

	public void affectEnvStats(MOB affectedMOB, EnvStats affectedStats)
	{
		super.affectEnvStats(affectedMOB,affectedStats);
		int height=(int)Math.round(new Integer(affectedStats.height()).doubleValue()*0.10);
		if(height==0) height=1;
		affectedStats.setHeight(height);
	}

	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		Environmental E=getAnyTarget(mob,commands,givenTarget,Item.WORN_REQ_UNWORNONLY);
		if(E==null) return false;

		if(!super.invoke(mob,commands,givenTarget,auto))
			return false;

		boolean success=profficiencyCheck(0,auto);
		if((success)&&((E instanceof MOB)||(E instanceof Item)))
		{
			FullMsg msg=new FullMsg(mob,E,this,affectType,auto?"<S-NAME> feel(s) somewhat smaller.":"<S-NAME> casts(s) a small spell on <T-NAMESELF>.");
			if(mob.location().okAffect(msg))
			{
				mob.location().send(mob,msg);
				beneficialAffect(mob,E,0);
				if(E instanceof MOB)
					((MOB)E).charStats().getMyRace().confirmGear((MOB)E);
			}
		}
		else
			beneficialWordsFizzle(mob,E,"<S-NAME> attempt(s) to cast a small spell, but fail(s).");

		return success;
	}
}