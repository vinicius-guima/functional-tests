package br.com.vini.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	public WebDriver acessarApp() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();Web
		ChromeOptions capabilities = new ChromeOptions();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.20.0.1:4444/wd/hub"),capabilities);
		driver.navigate().to("http://172.20.0.1:8888/tasks/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {

		WebDriver driver = acessarApp();
		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("teste via selenium");
			driver.findElement(By.id("dueDate")).sendKeys("13/06/2029");

			driver.findElement(By.id("saveButton")).click();

			String text = driver.findElement(By.id("message")).getText();

			assertEquals("Success!", text);
		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefaComfalhaPorData() throws MalformedURLException {
		WebDriver driver = acessarApp();
		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("teste via selenium falha");
			driver.findElement(By.id("dueDate")).sendKeys("13/06/2010");

			driver.findElement(By.id("saveButton")).click();

			String text = driver.findElement(By.id("message")).getText();

			assertEquals("Due date must not be in past", text);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarApp();
		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("dueDate")).sendKeys("13/06/2010");

			driver.findElement(By.id("saveButton")).click();

			String text = driver.findElement(By.id("message")).getText();

			assertEquals("Fill the task description", text);
		} finally {
			driver.quit();
		}
	}
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarApp();
		try {
			driver.findElement(By.id("addTodo")).click();

			driver.findElement(By.id("task")).sendKeys("teste via selenium falha");

			driver.findElement(By.id("saveButton")).click();

			String text = driver.findElement(By.id("message")).getText();

			assertEquals("Fill the due date", text);
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarApp();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("inserção e remoção");
			driver.findElement(By.id("dueDate")).sendKeys("15/02/2030");
			driver.findElement(By.id("saveButton")).click();
			String text = driver.findElement(By.id("message")).getText();
			assertEquals("Success!", text);
			
			driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
			text = driver.findElement(By.id("message")).getText();
			assertEquals("Success!", text);
		} finally {
			driver.quit();
		}
	}

}
