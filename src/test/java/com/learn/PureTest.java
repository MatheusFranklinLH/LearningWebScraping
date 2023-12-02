package com.learn;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PureTest {
	@Test
	public void testWebScraping() {
		Pure scraper = new Pure();

		// Teste para "Atlético-MG"
		Integer resultForAtleticoMG = scraper.getNeedScoreToDrawLeader("Atlético-MG");
		assertEquals(Integer.valueOf(3), resultForAtleticoMG);

		// Teste para "Botafogo"
		Integer resultForBotafogo = scraper.getNeedScoreToDrawLeader("Botafogo");
		assertEquals(Integer.valueOf(3), resultForBotafogo);

		// Teste para "Vitória"
		Integer resultForVitoria = scraper.getNeedScoreToDrawLeader("Vitória");
		assertEquals(Integer.valueOf(-1), resultForVitoria);

		// Teste para "Palmeiras"
		Integer resultForPalmeiras = scraper.getNeedScoreToDrawLeader("Palmeiras");
		assertEquals(Integer.valueOf(0), resultForPalmeiras);

		// Teste para "Coritiba"
		Integer resultForCoritiba = scraper.getNeedScoreToDrawLeader("Coritiba");
		assertEquals(Integer.valueOf(36), resultForCoritiba);
	}
}
