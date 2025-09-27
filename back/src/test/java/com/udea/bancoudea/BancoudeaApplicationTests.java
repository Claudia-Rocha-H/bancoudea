package com.udea.bancoudea;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Pruebas de integración para verificar que la aplicación arranca correctamente
 */
@SpringBootTest
@ActiveProfiles("test")
class BancoudeaApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("🧪 Probando: Carga del contexto de Spring Boot");
		// Verifica que el contexto de Spring se carga sin errores
		System.out.println("✅ Aplicación arranca correctamente - Contexto cargado sin errores");
	}

}
