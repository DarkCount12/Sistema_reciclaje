package Frontend.Components;

import org.jxmapviewer.viewer.TileFactoryInfo;

public class OSMTileFactoryInfo extends TileFactoryInfo {

    public OSMTileFactoryInfo() {
        super(
            1, // min zoom
            19, // max zoom
            19, // total zoom levels
            256, // tile size
            true, // x axis wrapping
            true, // y axis wrapping
            "https://tile.openstreetmap.org",
            "OpenStreetMap",
            "png",
            "© OpenStreetMap contributors"
        );
    }

   @Override
public String getTileUrl(int x, int y, int zoom) {
    int tileZoom = this.getTotalMapZoom() - zoom;
    return this.baseURL + "/" + tileZoom + "/" + x + "/" + y + ".png";
}

}
