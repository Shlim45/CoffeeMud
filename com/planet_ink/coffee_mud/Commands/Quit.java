package com.planet_ink.coffee_mud.Commands;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

/* 
   Copyright 2000-2005 Bo Zimmerman

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
public class Quit extends StdCommand
{
	public Quit(){}

	private String[] access={"QUIT","QUI","Q"};
	public String[] getAccessWords(){return access;}

	public static void dispossess(MOB mob)
	{
		if(mob.soulMate()==null)
		{
			mob.tell("Huh?");
			return;
		}
        mob.dispossess(true);
	}

	public boolean execute(MOB mob, Vector commands)
		throws java.io.IOException
	{
		if(mob.soulMate()!=null)
			dispossess(mob);
		else
		if(!mob.isMonster())
		{
            if((mob.session()!=null)
            &&(mob.session().getLastPKFight()>0)
            &&((System.currentTimeMillis()-mob.session().getLastPKFight())<(5*60*1000)))
            {
                mob.tell("You must wait a few more minutes before you are allowed to quit.");
                return false;
            }
            
			try
			{
				mob.session().cmdExit(mob,commands);
			}
			catch(Exception e)
			{
				if(mob.session()!=null)
					mob.session().setKillFlag(true);
			}
		}
		return false;
	}
	public int ticksToExecute(){return 0;}
	public boolean canBeOrdered(){return false;}

	public int compareTo(Object o){ return CMClass.classID(this).compareToIgnoreCase(CMClass.classID(o));}
}
