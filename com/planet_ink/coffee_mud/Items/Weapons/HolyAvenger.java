package com.planet_ink.coffee_mud.Items.Weapons;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;

public class HolyAvenger extends TwoHandedSword
{
	public String ID(){	return "HolyAvenger";}
	public HolyAvenger()
	{
		super();

		setName("the holy avenger");
		setDisplayText("a beautiful two-handed sword has been left here");
		setDescription("A two-handed sword crafted with a careful hand, and inscribed with several holy symbols.");
		secretIdentity="The Holy Avenger!  A good-only Paladin sword that casts dispel evil on its victims";
		baseEnvStats().setAbility(0);
		baseEnvStats().setLevel(20);
		baseEnvStats().setWeight(25);
		baseEnvStats().setAttackAdjustment(25);
		baseEnvStats().setDamage(18);
		baseEnvStats().setDisposition(baseEnvStats().disposition()|EnvStats.IS_GOOD|EnvStats.IS_BONUS);
		baseGoldValue=15500;
		recoverEnvStats();
		material=EnvResource.RESOURCE_MITHRIL;
		weaponType=TYPE_SLASHING;
		setRawLogicalAnd(true);
	}

	public Environmental newInstance()
	{
		return new HolyAvenger();
	}

	public boolean okAffect(Environmental myHost, Affect affect)
	{
		if(!super.okAffect(myHost,affect))
			return false;

		MOB mob=affect.source();
		if(mob.location()==null)
			return true;

		if(affect.amITarget(this))
		switch(affect.targetMinor())
		{
		case Affect.TYP_HOLD:
		case Affect.TYP_WEAR:
		case Affect.TYP_WIELD:
		case Affect.TYP_GET:
			if((!affect.source().charStats().getCurrentClass().ID().equals("Paladin"))
			||(affect.source().getAlignment()<650))
			{
				unWear();
				mob.location().show(mob,null,Affect.MSG_OK_ACTION,name()+" flashes and flies out of <S-HIS-HER> hands!");
				if(affect.source().isMine(this))
					ExternalPlay.drop(affect.source(),this,true,false);
				return false;
			}
			break;
		case Affect.TYP_DROP:
		case Affect.TYP_THROW:
			break;
		default:
			break;
		}
		return true;
	}

	public void affect(Environmental myHost, Affect affect)
	{
		super.affect(myHost,affect);
		if((affect.source().location()!=null)
		&&(Util.bset(affect.targetCode(),Affect.MASK_HURT))
		&&((affect.targetCode()-Affect.MASK_HURT)>0)
		&&(affect.tool()==this)
		&&(affect.target() instanceof MOB)
		&&(!((MOB)affect.target()).amDead())
		&&(((MOB)affect.target()).getAlignment()<350))
		{
			FullMsg msg=new FullMsg(affect.source(),(MOB)affect.target(),new HolyAvenger(),Affect.MSG_OK_ACTION,Affect.MSK_MALICIOUS_MOVE|Affect.TYP_UNDEAD,Affect.MSG_NOISYMOVEMENT,null);
			if(affect.source().location().okAffect(affect.source(),msg))
			{
				affect.source().location().send(affect.source(), msg);
				int damage=Dice.roll(1,15,0);
				if(msg.wasModified())
					damage=damage/2;
				affect.addTrailerMsg(new FullMsg(affect.source(),(MOB)affect.target(),Affect.MSG_OK_ACTION,name()+" dispels evil within <T-NAME> and "+CommonStrings.standardHitWord(Weapon.TYPE_BURSTING,damage)+" <T-HIM-HER>>!"));
				affect.addTrailerMsg(new FullMsg(affect.source(),(MOB)affect.target(),null,Affect.NO_EFFECT,Affect.MASK_HURT+damage,Affect.NO_EFFECT,null));
			}
		}
	}

}
