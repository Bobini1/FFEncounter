package Main.Logic.Characters;

import Main.Engine.Actor;
import Main.Logic.Components.AttackDealer;
import Main.Logic.Components.AttackTaker;

public interface GameCharacter extends Actor, AttackDealer, AttackTaker {

}
