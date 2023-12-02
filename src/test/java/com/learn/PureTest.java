package com.learn;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PureTest {
	@Test
	public void testWebScraping() {
		Pure scraper = new Pure();

		// Teste para "Atlético-MG"
		Integer resultForAtleticoMG = scraper.getNeedPointsToDrawLeader("Atlético-MG");
		assertEquals(Integer.valueOf(3), resultForAtleticoMG);

		// Teste para "Botafogo"
		Integer resultForBotafogo = scraper.getNeedPointsToDrawLeader("Botafogo");
		assertEquals(Integer.valueOf(3), resultForBotafogo);

		// Teste para "Vitória"
		Integer resultForVitoria = scraper.getNeedPointsToDrawLeader("Vitória");
		assertEquals(Integer.valueOf(-1), resultForVitoria);

		// Teste para "Palmeiras"
		Integer resultForPalmeiras = scraper.getNeedPointsToDrawLeader("Palmeiras");
		assertEquals(Integer.valueOf(0), resultForPalmeiras);

		// Teste para "Coritiba"
		Integer resultForCoritiba = scraper.getNeedPointsToDrawLeader("Coritiba");
		assertEquals(Integer.valueOf(36), resultForCoritiba);
	}
}
