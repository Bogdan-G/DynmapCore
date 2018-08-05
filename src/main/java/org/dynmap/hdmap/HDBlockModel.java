package org.dynmap.hdmap;

import org.dynmap.renderer.DynmapBlockState;

public abstract class HDBlockModel {
    private String blockset;
    /**
     * Block definition - positions correspond to Bukkit coordinates (+X is south, +Y is up, +Z is west)
     * @param blockname - block name
     * @param databits - bitmap of block data bits matching this model (bit N is set if data=N would match)
     * @param blockset - ID of block definition set
     */
    protected HDBlockModel(String blockname, int databits, String blockset) {
        this.blockset = blockset;
        DynmapBlockState bblk = DynmapBlockState.getBaseStateByName(blockname);
        if (bblk.isNotAir()) {
            for (int i = 0; i < bblk.getStateCount(); i++) {
                if((databits & (1<<i)) != 0) {
                    DynmapBlockState bs = bblk.getState(i);
                    HDBlockModel prev = HDBlockModels.models_by_id_data.put(bs.globalStateIndex, this);
                    if((prev != null) && (prev != this)) {
                        prev.removed(bs);
                    }
                }
            }
        }
    }
    public String getBlockSet() {
        return blockset;
    }
    
    public abstract int getTextureCount();
    
    public void removed(DynmapBlockState blk) {
    }
}
