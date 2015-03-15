package com.starshipsim.panels;

import com.starshipsim.interfaces.Swappable;
import com.starshipsim.shipmodules.ShipModule;

public class ModuleSwapper {

	private Swappable<ShipModule> source, dest;
	
	public Swappable<ShipModule> getSource() {
		return source;
	}
	public void setSource(Swappable<ShipModule> source) {
		this.source = source;
	}
	public Swappable<ShipModule> getDest() {
		return dest;
	}
	public void setDest(Swappable<ShipModule> dest) {
		this.dest = dest;
	}
	
	public void activate() {
		if(source != null && dest != null) {
			ShipModule module1 = source.getObject();
			ShipModule module2 = dest.getObject();
			
			source.setObject(module2);
			dest.setObject(module1);;
			
			this.source = null;
			this.dest = null;
		}
	}
	
}
