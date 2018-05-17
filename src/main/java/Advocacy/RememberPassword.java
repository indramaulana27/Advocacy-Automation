package Advocacy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Test;

public class RememberPassword {
	
	public static String USERNAME = "benkrokower";
	public static String ACCESSKEY = "gF6ExsBpXyATbNzpvTHx";
	public static String URL = "https://"+USERNAME+":"+ACCESSKEY+"@hub-cloud.browserstack.com/wd/hub";
	
	@Test (dataProvider = "BrowserData")
	public static void main(String[] args) throws Exception{
		
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.ALL);
		logs.enable(LogType.CLIENT, Level.ALL);
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.PERFORMANCE, Level.ALL);
		logs.enable(LogType.PROFILER, Level.ALL);
		logs.enable(LogType.SERVER, Level.ALL);

		DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
		desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
			
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browser", "chrome");
		caps.setCapability("os", "Windows");
		caps.setCapability("os_version", "10");
		caps.setCapability("resolution", "1024x768");
		caps.setCapability("browserstack.debug", "true");
		caps.setCapability("browserstack.networkLogs", "true");
		caps.setCapability("project", "advocacy");
		
		WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
		driver.get("https://manage.advocateslink.com");
		RememberPassword(driver);
	}
	
	static void SendEmail(WebDriver driver) throws Exception {
		final String username = "indram@strategies360.com";
		final String password = "Password1!";
		final InternetAddress[] Recipient = InternetAddress.parse("indra@upwardstech.com, indra.maulana.r@gmail.com");
		
		
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.office365.com");
		properties.put("mail.smtp.port", "587");
		properties.put("java.net.preferIPv4Stack", "true");
		Session session = Session.getDefaultInstance(properties, 
		 new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});	
		
		try{
			
			SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
			URL url = new URL("https://api.browserstack.com/automate/sessions/a6c1f4203351cd8a83f65a0f55d45d0262d606f6.json");
			System.out.println(url);
			//driver.switchTo().alert().sendKeys("ben@upwardstech.com");
			//driver.switchTo().alert().sendKeys("lubliner");
			//driver.switchTo().alert().accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			//driver.findElement(By.id(id));
			String str = "";
			while(null != (str = br.readLine())) {
				System.out.println(str);
			}
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, Recipient);
			message.setSubject("Automation Reporting (Login)");



			BodyPart messageBodyPart1 = new MimeBodyPart();  
			messageBodyPart1.setText("Result of Automation"); 
			BodyPart messageBodyPart2 = new MimeBodyPart(); 
			messageBodyPart2.setText(url.toString());
			

			//4) create new MimeBodyPart object and set DataHandler object to this object      
			//MimeBodyPart messageBodyPart2 = new MimeBodyPart();  

			//String filename = "File path if you want to attach in mail";//change accordingly  
			//DataSource source = new FileDataSource(filename);  
			//messageBodyPart2.setDataHandler(new DataHandler(source));  
			//messageBodyPart2.setFileName(filename);  


			//5) create Multipart object and add MimeBodyPart objects to this object      
			Multipart multipart = new MimeMultipart();  
			multipart.addBodyPart(messageBodyPart1);  
			multipart.addBodyPart(messageBodyPart2);  

			//6) set the multiplart object to the message object  
			message.setContent(multipart);  
			Transport.send(message);

			System.out.println("Mail Sent Successfully");
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}
	}
	
	static void RememberPassword(WebDriver driver) throws Exception {
		try{
			WebElement remember_password = driver.findElement(By.xpath("//*[@id=\"log_in\"]/div[5]/div[2]/a"));
			JavascriptExecutor click_remember = ((JavascriptExecutor)driver);
			click_remember.executeScript("arguments[0].click", remember_password);
			//
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys("indra+001@upwardstech.com");
			Thread.sleep(2000);
			//click button for login
			WebElement backto_login = driver.findElement(By.xpath("//*[@id=\"log_in\"]/div[4]/div[2]/button"));
			JavascriptExecutor back = ((JavascriptExecutor)driver);
			back.executeScript("arguments[0].click()", backto_login);
			Thread.sleep(5000);
			
//			driver.get("mail.google.com");
//			driver.findElement(By.id("indetifierId")).clear();
//			driver.findElement(By.id("identifierId")).sendKeys("indra@upwardstech.com");
//			WebElement next = driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content/span"));
//			JavascriptExecutor click_next = ((JavascriptExecutor)driver);
//			click_next.executeScript("arguments[0].click", next);
//			driver.findElement(By.name("password")).clear();
//			driver.findElement(By.name("password")).sendKeys("katacinta");
//			WebElement login = driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span"));
//			JavascriptExecutor click_login = ((JavascriptExecutor)driver);
//			click_login.executeScript("arguments[0].click", login);
//			List<WebElement> element = (List<WebElement>) driver.findElement(By.xpath("//*[@id=\"view_container\"]/form"));
//			if(element.isEmpty() == true) {
//				WebElement get_code = driver.findElement(By.xpath("//*[@id=\"view_container\"]/form/div[2]/div/div/div/ul/li[3]/div/div[2]/span[2]"));
//				JavascriptExecutor click_get_code = ((JavascriptExecutor)driver);
//				click_get_code.executeScript("arguments[0].click", get_code);
//				
//			}else {
//				
//			}
			
			
			//notes the result
			System.out.println(driver.getCurrentUrl());
			SendEmail(driver);
			driver.quit();
		}catch(Exception e){
		SendEmail(driver);
		driver.quit();
		}
	}
}
