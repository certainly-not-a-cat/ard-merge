package haven.res.lib.globfx;

import haven.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.ref.*;

public abstract class GlobEffect implements Effect {
    public int hashCode() {
	return(this.getClass().hashCode());
    }

    public boolean equals(Object o) {
	return(this.getClass() == o.getClass());
    }
}
