package com.wingnest.bundles.sample.hello.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.streamBundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.wingnest.bundles.sample.hello.service_spec.Hello;


@ExamReactorStrategy(PerClass.class)
@RunWith(PaxExam.class)
public class TestOsgiServices {

  @Inject
  protected BundleContext bundleContext;
  
  @Inject
  protected Hello hello;

	@Configuration
	public Option[] config() {
		return new Option[] {

				mavenBundle()
						.groupId("com.wingnest.bundles.sample.hello")
						.artifactId(
								"com.wingnest.bundles.sample.hello.service_spec")
						.versionAsInProject(),

				streamBundle(bundle()
						.set(Constants.BUNDLE_SYMBOLICNAME, "com.wingnest.bundles.sample.hello.service")	// この設定は必須ではない
						.add(com.wingnest.bundles.sample.hello.service.Activator.HelloImpl.class) // Windows環境だと下 add()との順番を入れ替えると Activatorが見つけられないという例外がでる。
						.add(com.wingnest.bundles.sample.hello.service.Activator.class)
						.set(Constants.BUNDLE_ACTIVATOR, com.wingnest.bundles.sample.hello.service.Activator.class.getName())
						.set(Constants.IMPORT_PACKAGE, "org.osgi.framework, com.wingnest.bundles.sample.hello.service_spec")
						.build()),

				junitBundles()

		};
	}

  @Test
  public void test() throws InterruptedException {
	  assertNotNull(bundleContext);
	  assertNotNull(hello);
	  assertEquals("hello", hello.hello());
  }
  
}