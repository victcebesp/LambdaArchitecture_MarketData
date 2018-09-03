package org.eifer.box;

import io.intino.konos.alexandria.Inl;
import io.intino.konos.datalake.ReflowConfiguration;
import io.intino.ness.inl.Message;
import io.intino.tara.magritte.Graph;
import io.intino.tara.magritte.stores.FileSystemStore;
import org.eifer.box.ness.NessOperations;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		MounterBox box = new MounterBox(args);
		Graph graph = new Graph(new FileSystemStore(box.configuration.store()));
		box.put(graph).open();

		Thread.sleep(5000);

		Message message = Inl.toMessage(new ReflowConfiguration()
				.blockSize(50000)
				.cleanStore(true)
				.tankList(Arrays.asList(new ReflowConfiguration.Tank().name("market.intradayreport"),
						new ReflowConfiguration.Tank().name("market.dayaheadreport"))));

		new NessOperations(box).customReflow(message.toString());

		Runtime.getRuntime().addShutdownHook(new Thread(box::close));

	}
}