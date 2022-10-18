package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest extends Calculator {

	  @Test
	  @DisplayName("ともに自然数")
	  public void naturals() {
	    
	    int result = Calculator.absAdd(3, 5);
	    
	    assertThat(result, is(8));
	    
	  }
	  
		@Test
		@DisplayName("aが負の値")
		public void aMinus() {
			
			int result = Calculator.absAdd(-3, 5);
			assertThat(result, is(8));
		}
		
		@Test
		@DisplayName("bが負の値")
		public void bMinus() {
			int result = Calculator.absAdd(3, -5);
			assertThat(result, is(8));
		}

}
