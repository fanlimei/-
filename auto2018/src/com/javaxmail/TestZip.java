package com.javaxmail;

import org.testng.annotations.Test;

public class TestZip {

	@Test
	 public void nihao(){  
	ZipCompressor zip=new ZipCompressor("F:\\Three\\lhzgithub\\auto2018\\test-output\\html.zip");
	zip.compressExe("F:\\Three\\lhzgithub\\auto2018\\test-output\\html");
	}
}