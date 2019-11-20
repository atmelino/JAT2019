package chapter09.game;

import chapter09.engine.GameEngine;
import chapter09.engine.IGameLogic;

public class Main {

	public static void main(String[] args) {
		try {
			boolean bunny = true;
			bunny = false;
			bunny = true;

			boolean vSync = true;
			IGameLogic gameLogic = new DummyGame(bunny);
			GameEngine gameEng = new GameEngine("GAME", 600, 480, vSync, gameLogic, bunny);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}
}