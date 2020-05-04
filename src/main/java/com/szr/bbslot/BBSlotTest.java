package com.szr.bbslot;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BBSlotTest {

	public static void test() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.bigbasket.com/basket/?ver=1");
//		driver.get("https://www.bigbasket.com");
		System.out.println("Please login using OTP and then wait for a while.");

		long millis = 60000;
		Thread.sleep(millis);
		
		driver.findElement(By.xpath("//a[@qa = 'areaDD']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//a[@qa = 'address']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[contains(text(),'CONTINUE')]")).click();
		
		boolean value = true;
		while (value) {
			
			driver.get("https://www.bigbasket.com/basket/?ver=1");
			Thread.sleep(10000);
			System.out.println("Trying to find a slot!");

			try {
				driver.findElement(By.xpath("//button[@id = 'checkout']")).click();
				Thread.sleep(10000);
				String src = driver.getPageSource();
				if (src.contains("checkout") && !src.contains("Unfortunately, we do not have")) {
					System.out.println("Found the slots!");
					notifyMe();
				} else {
					System.out.println("Trying for slots");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			Thread.sleep(120000);
		}
	}

	public static void notifyMe() {
		System.out.println("OS: " + System.getProperty("os.name"));
		if (SystemTray.isSupported()) {
			TrayIconDemo td = new TrayIconDemo();
			try {
				td.displayTray();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("System tray not supported!");
		}

	}

	public static void main(String[] args) throws InterruptedException {
		BBSlotTest.test();
	}

}
