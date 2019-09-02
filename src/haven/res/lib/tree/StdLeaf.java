package haven.res.lib.tree;

import haven.*;
import haven.res.lib.leaves.*;
import java.util.*;

public class StdLeaf extends FallingLeaves.Leaf {
	public final Material m;

	public StdLeaf(FallingLeaves fx, Coord3f c, Material m) {
		fx.super(c);
		this.m = m;
	}

	public Material mat() {return(m);}
}