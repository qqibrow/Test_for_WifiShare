package com.example.android.nsdchat.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;
import com.example.android.nsdchat.*;
public class ProtocolPackageTest {

	@Test
	public void testEqual() {
		ProtocolPackage p = new ProtocolPackage(PackageType.GET_META, "test");
		ProtocolPackage same = new ProtocolPackage(PackageType.GET_META, "test");
		assertEquals("Create same package and they should be equal.", p, same);
		
		ProtocolPackage p_has_file = new ProtocolPackage(PackageType.GET_META, "test", "foo.txt", 100);
		ProtocolPackage same_has_file = new ProtocolPackage(PackageType.GET_META, "test", "foo.txt", 100);
		assertEquals("Create same package and they should be equal.", p_has_file, same_has_file);
		
		assertThat(p, not(equalTo(p_has_file)));
		
	}
//	 Create an protocolpackage and output it to a stream, and then get it from the stream in the other side. It should ge the same with the orignal package.
	@Test
	public void testSendPackage() {
		PackageType type = PackageType.GET_META;
		String sender = "test";
		
		ProtocolPackage p = new ProtocolPackage(type, sender);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		p.sendPackage(bos);
		
		byte[] sink = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(sink);
		ProtocolPackage same_package = ProtocolPackage.receivePackage(bis);
		
		assertEquals("p has same type.", same_package.getType(), type);
		assertEquals("p has same sender.", same_package.getSender(), sender);
		assertEquals("Package got from inputstream should equals to original package.", p, same_package);	
	}

	@Test
	public void testCopyPackage() {
		ProtocolPackage p_has_file = new ProtocolPackage(PackageType.GET_META, "test", "foo.txt", 100);
		ProtocolPackage same = new  ProtocolPackage();
		same.copyPackage(p_has_file);
		assertEquals("Copy get the same result.", p_has_file, same);
	}

}
