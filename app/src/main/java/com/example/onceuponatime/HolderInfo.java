package com.example.onceuponatime;

public class HolderInfo {
    String name;
    String need;
    String icon;
    boolean used;

    public HolderInfo(String _name, String _need, String _icon) {
        name = _name;
        need = _need;
        icon = _icon;
        used = false;
    }

    public String getName() { return name; }

    public String getNeed() { return need; }

    public String getIcon() { return icon; }

    public boolean getUsed() { return used; }

    public void setUsed(boolean _used) { used = _used; }
}
