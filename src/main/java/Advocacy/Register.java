package Advocacy;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


public class Register {
	
	public static String USERNAME = "benkrokower";
	public static String ACCESSKEY = "gF6ExsBpXyATbNzpvTHx";
	public static String URL = "https://"+USERNAME+":"+ACCESSKEY+"@hub-cloud.browserstack.com/wd/hub";
	
	
	@Test (dataProvider = "BrowserData")
	public static void main(String[] args) throws Exception{
			
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
		Register(driver);
	}
	
	
	static void SendEmail(WebDriver driver) throws Exception {
		final String username = "indram@strategies360.com";
		final String password = "Password1!";
		
		
		SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
		String url = ("https://benkrokower:gF6ExsBpXyATbNzpvTHx@api.browserstack.com/automate/sessions/"+sessionId+".json");
		//String json = ClientBuilder.newClient().target("https://benkrokower:gF6ExsBpXyATbNzpvTHx@api.browserstack.com/automate/sessions/"+sessionId+".json").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		
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
			
			
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("indram@strategies360.com"));
			message.setRecipients(Message.RecipientType.TO,
			    InternetAddress.parse("indra@upwardstech.com"));
			message.setSubject("Automation Reporting (Register)");



			BodyPart messageBodyPart1 = new MimeBodyPart();  
			messageBodyPart1.setText("Result of Automation"); 
			BodyPart messageBodyPart2 = new MimeBodyPart();  
			messageBodyPart2.setText(url);

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
	
	static void Register(WebDriver driver) throws Exception {
		try{
			//click register link
			WebElement register = driver.findElement(By.xpath("//*[@id=\"log_in\"]/div[5]/div[1]/a"));
			JavascriptExecutor click_register = ((JavascriptExecutor)driver);
			click_register.executeScript("arguments[0].click()", register);
			//insert first name, last name, and email
			driver.findElement(By.id("first_name")).clear();
			driver.findElement(By.id("first_name")).sendKeys("indra");
			driver.findElement(By.id("last_name")).clear();
			driver.findElement(By.id("last_name")).sendKeys("maulana");
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys("indra+102@upwardstech.com");
			//click send button
			WebElement send = driver.findElement(By.xpath("//*[@id=\"sign_up\"]/button"));
			JavascriptExecutor click_button = ((JavascriptExecutor)driver);
			click_button.executeScript("arguments[0].click()", send);
			Thread.sleep(10000);
			List<WebElement> element = driver.findElements(By.xpath("/html/body/ui-view/div/div/div/div[2]/div[1]/button"));
			if(element.isEmpty()==true){
				driver.findElement(By.id("email")).clear();
				driver.findElement(By.id("email")).sendKeys("indra+104@upwardstech.com");
				//click send button
				WebElement resend = driver.findElement(By.xpath("//*[@id=\"sign_up\"]/button"));
				JavascriptExecutor reclick_button = ((JavascriptExecutor)driver);
				reclick_button.executeScript("arguments[0].click()", resend);
				//notes the result
				System.out.println(driver.getCurrentUrl());
				Thread.sleep(1000);
				SendEmail(driver);
				driver.quit();
			}else {
				//notes the result
				System.out.println(driver.getCurrentUrl());
				Thread.sleep(1000);
				SendEmail(driver);
				driver.quit();
			}
		}catch(Exception e){
		SendEmail(driver);
		driver.quit();
		}
	}
	
	
	
}
