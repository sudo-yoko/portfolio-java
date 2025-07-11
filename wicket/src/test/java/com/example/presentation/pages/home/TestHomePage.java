package com.example.presentation.pages.home;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.presentation.MyApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
	private WicketTester tester;

	@BeforeEach
	public void setUp() {
		tester = new WicketTester(new MyApplication());
	}

	@Test
	public void homepageRendersSuccessfully() {
		// start and render the test page
		tester.startPage(HomePage.class);

		// assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}
}
