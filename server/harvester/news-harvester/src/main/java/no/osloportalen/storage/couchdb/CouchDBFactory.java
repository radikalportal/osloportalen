package no.osloportalen.storage.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.lightcouch.CouchDbClient;

import no.osloportalen.storage.BasicFactory;

public class CouchDBFactory extends BasicFactory {
	private List<CouchDbClient> clients;

	{
		clients = new ArrayList<CouchDbClient>();
	}

	public CouchDbClient get() {
		CouchDbClient client = new CouchDbClient();
		if (clients == null) {
			clients = new ArrayList<CouchDbClient>();
		}
		if ( clients.isEmpty() ) {
			clients.add(client);
		}
		return clients.get(0);
	}

	@Override
	public boolean isPoolable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	 
}
