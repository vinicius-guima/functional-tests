package br.com.vini.tasks.functional;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarApp() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8888/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() {

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
	public void naoDeveSalvarTarefaComfalhaPorData() {
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
	public void naoDeveSalvarTarefaSemDescricao() {
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
	public void naoDeveSalvarTarefaSemData() {
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

}
