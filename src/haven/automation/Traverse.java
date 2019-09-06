package haven.automation;

import haven.*;

import static haven.OCache.posres;

import java.util.ArrayList;
import java.util.Arrays;

public class Traverse implements Runnable {
    private GameUI gui;
    private ArrayList<String> buildings = new ArrayList<String>(Arrays.asList(
        "gfx/terobjs/arch/logcabin",
        "gfx/terobjs/arch/timberhouse",
        "gfx/terobjs/arch/stonestead",
        "gfx/terobjs/arch/stonemansion",
        "gfx/terobjs/arch/greathall",
        "gfx/terobjs/arch/stonetower",
        "gfx/terobjs/arch/windmill"
    ));
    
    public Traverse(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        Gob targetGob = getGob(gui, buildings, 40*11);
        if (targetGob != null) {
            try {
                gui.map.wdgmsg("click", targetGob.sc, targetGob.rc.floor(posres), 3, 0, 0, (int) targetGob.id, targetGob.rc.floor(posres), 0, ( buildings.contains(targetGob.getres().name) ) ? 16 : -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    private Gob getGob(GameUI gui, ArrayList<String> gobNameAL, double maxrange)  //search for nearest unvisited Gob in range
    {
        Coord2d plc = gui.map.player().rc;
        Gob result = null;
        if( maxrange == 0 ) maxrange = 1024.0;
        synchronized (gui.map.glob.oc) {
            for (Gob gob : gui.map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    
                    if (res == null) 
                        continue;

                    if (res.name.endsWith("-door")) { //inner door check
                        result = gob;
                        break;                         //that's interior room, no buildings or mine ladders are expected here
                    }

                    boolean skipGob = true;
                    if ( res.name.endsWith("ladder") || gobNameAL.contains(res.name) )
                        skipGob = false;
                        
                    if (skipGob) continue;

                    if ((result == null || gob.rc.dist(plc) < result.rc.dist(plc)) && gob.rc.dist(plc) < maxrange) 
                        result = gob;
                } 
                catch (Loading l) {
                    l.printStackTrace();
                }
            }
        }
        return result;
    }
}
