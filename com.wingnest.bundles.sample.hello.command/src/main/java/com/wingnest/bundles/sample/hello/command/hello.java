
package com.wingnest.bundles.sample.hello.command;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.karaf.shell.commands.Command;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.wingnest.bundles.sample.hello.service.Hello;

@Command(scope = "mycommand", name = "hello", description = "hello command")
public class hello extends OsgiCommandSupport {

    protected Object doExecute() throws Exception {
    	BundleContext context = this.getBundleContext();
    	ServiceReference<Hello> serviceReference = context.getServiceReference(com.wingnest.bundles.sample.hello.service.Hello.class);
    	if (serviceReference != null ) {
    		Hello service = context.getService(serviceReference);
    		System.out.println(service.hello());
    	}
        return null;
    }
}
