package haven.automation;


import static haven.OCache.posres;

import haven.CheckListboxItem;
import haven.Config;
import haven.GameUI;
import haven.Gob;
import haven.Loading;
import haven.Resource;
import haven.Utils;
import haven.sloth.gob.Type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class PickForageable implements Runnable {
    private GameUI gui;
    public static final HashSet<String> gates = new HashSet(Arrays.asList(
        "brickbiggate",
        "brickwallgate",
        "drystonewallbiggate",
        "drystonewallgate",
        "palisadebiggate",
        "palisadegate",
        "polebiggate",
        "polegate"
    ));
    public static final HashSet<String> excludes = new HashSet(Arrays.asList(
        "boostspeed",
        "bram",
        "cart",
        "dugout",
        "fishingnet",
        "knarr",
        "snekkja",
        "lobsterpot",
        "mare",
        "rowboat",
        "stallion",
        "wagon",
        "wball",
        "wheelbarrow"
    ));
    public PickForageable(GameUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        Gob herb = null;
        synchronized (gui.map.glob.oc) {
            if (gui.map.player() == null)
                return;//player is null, possibly taking a road, don't bother trying to do all of the below.
            for (Gob gob : gui.map.glob.oc) {
                Resource res = null;
                boolean gate = false;
                boolean cart = false;
                boolean ignore = true;
                try {
                    res = gob.getres();
                } catch (Loading l) {
                }
                if (res != null) {
                    CheckListboxItem itm = Config.icons.get(res.basename());
                    if ( !excludes.contains( res.basename() ) )
                        ignore = false;
                    Boolean hidden = Boolean.FALSE;
                    if(!Config.disablegatekeybind)
                        gate = gates.contains(res.basename());
                    if(!Config.disablecartkeybind)
                        cart = res.basename().equals("cart");
                    if (itm == null)
                        hidden = null;
                    else if (itm.selected)
                        hidden = Boolean.TRUE;

                    /*don't ignore open/close visitor gates
                    try {
                        if (gate) {
                            for(Gob.Overlay ol : gob.ols){
                                String resname = (this.gui.map.glob.sess.getres(Utils.uint16d(ol.sdt.rbuf, 0)).get()).basename();
                                if (resname.equals("visflag")) {
                                    gate = false;
                                }
                            }
                        }
                    } catch (Exception fucknulls) {
                        fucknulls.printStackTrace();
                    }
                    */

                    if (hidden == null && res.name.startsWith("gfx/terobjs/herbs") || (hidden == Boolean.FALSE && !ignore)  || gate || cart) {
                        double distFromPlayer = gob.rc.dist(gui.map.player().rc);
                        if (distFromPlayer <= 40 * 11 && (herb == null || distFromPlayer < herb.rc.dist(gui.map.player().rc)))
                            herb = gob;
                    }
                }
            }
        }
        if (herb == null)
            return;
        gui.map.wdgmsg("click", herb.sc, herb.rc.floor(posres), 3, 0, 0, (int) herb.id, herb.rc.floor(posres), 0, -1);

        if(herb.getres() != null) {
            CheckListboxItem itm = Config.autoclusters.get(herb.getres().name);
            if(itm != null && itm.selected)
                gui.map.startMusselsPicker(herb);
        }
    }
}
