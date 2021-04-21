package br.com.vini.tasks.prod;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

	@Test
	public void healthCheck() throws MalformedURLException {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.20.0.1:4444/wd/hub"), capabilities);
		try {
			driver.navigate().to("http://127.0.0.1:9999/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			String text = driver.findElement(By.id("version")).getText();
			System.out.println(text);
			assertTrue(text.contains("build"));
		} finally {
			driver.quit();
		}
	}
}
