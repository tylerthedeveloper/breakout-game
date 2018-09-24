package com.infrastructure;

import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IComposite {
	public void draw(Graphics g);
	 void save(ObjectOutputStream op);
	 void load(ObjectInputStream ip);
}
