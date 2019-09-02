package haven.res.lib.globfx;

import haven.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.ref.*;

public interface Effect extends Rendered {
	public boolean tick(float dt);
}